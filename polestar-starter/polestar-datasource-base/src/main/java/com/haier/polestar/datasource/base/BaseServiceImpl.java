package com.haier.polestar.datasource.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haier.polestar.common.exception.IdempotencyException;
import com.haier.polestar.common.exception.LockException;
import com.haier.polestar.common.lock.DistributedLock;
import com.haier.polestar.common.response.Result;
import com.haier.polestar.common.response.ResultCode;
import com.haier.polestar.common.response.ResultGenerator;
import com.haier.polestar.common.util.ReflectUtil;
import com.haier.polestar.datasource.annotation.QueryCondition;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * service实现父类
 *
 * @author panxiaole
 * @date 2019-04-20
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	/**
	 * 新增记录
	 *
	 * @param model model
	 * @return result
	 */
	@Override
	public Result<T> add(T model) {
		return super.save(model) ? ResultGenerator.succeed() : ResultGenerator.failed("操作失败");
	}

	/**
	 * 修改记录
	 *
	 * @param model model
	 * @return result
	 */
	@Override
	public Result<T> update(T model) {
		return super.updateById(model) ? ResultGenerator.succeed() : ResultGenerator.failed(ResultCode.OPTIMISTIC_LOCKER_EXCEPTION);
	}

	/**
	 * 删除记录
	 *
	 * @param id id
	 * @return result
	 */
	@Override
	public Result<T> delete(Serializable id) {
		return super.removeById(id) ? ResultGenerator.succeed() : ResultGenerator.failed(ResultCode.OPTIMISTIC_LOCKER_EXCEPTION);
	}

	/**
	 * 分页
	 *
	 * @param page  分页对象
	 * @param model 查询条件
	 * @return page
	 */
	@Override
	public IPage<T> selectPage(Page<T> page, T model) {
		QueryWrapper<T> queryWrapper = this.buildQueryWrapper(model);
		return super.baseMapper.selectPage(page, queryWrapper);
	}

	/**
	 * 构建QueryWrapper
	 *
	 * @param model model
	 * @return QueryWrapper
	 */
	protected QueryWrapper<T> buildQueryWrapper(T model) {
		List<Field> fields = ReflectUtil.getAllFields(model.getClass());
		QueryWrapper<T> wrapper = new QueryWrapper<>();
		for (Field field : fields) {
			TableField tableField = field.getAnnotation(TableField.class);
			if (tableField == null) {
				continue;
			}
			try {
				field.setAccessible(true);
				if (field.get(model) == null) {
					continue;
				}
				if (field.getType().equals(Date.class) || field.getType().equals(Timestamp.class)) {
					//时间类型根据范围查询
					QueryCondition queryCondition = field.getAnnotation(QueryCondition.class);
					if (queryCondition == null) {
						continue;
					}
					String fieldName = queryCondition.field();
					switch (queryCondition.condition()) {
						case LT:
							wrapper.lt(fieldName, field.get(model));
							break;
						case LE:
							wrapper.le(fieldName, field.get(model));
							break;
						case GT:
							wrapper.gt(fieldName, field.get(model));
							break;
						case GE:
							wrapper.ge(fieldName, field.get(model));
							break;
						default:
					}
				} else {
					//非时间类型默认用等于
					wrapper.eq(tableField.value(), field.get(model));
				}
			} catch (IllegalAccessException e) {
				log.error("通过反射获取字段值错误", e);
			}
		}
		return wrapper;
	}

	/**
	 * 幂等性新增记录
	 * 例子如下：
	 * String username = sysUser.getUsername();
	 * boolean result = super.saveIdempotency(sysUser, lock
	 * , LOCK_KEY_USERNAME+username
	 * , new QueryWrapper<SysUser>().eq("username", username));
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @param msg          对象已存在提示信息
	 * @return
	 */
	@Override
	public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
		if (lock == null) {
			throw new LockException("DistributedLock is null");
		}
		if (StringUtils.isEmpty(lockKey)) {
			throw new LockException("lockKey is null");
		}
		try {
			//加锁
			boolean isLock = lock.lock(lockKey);
			if (isLock) {
				//判断记录是否已存在
				int count = super.count(countWrapper);
				if (count == 0) {
					return super.save(entity);
				} else {
					if (StringUtils.isEmpty(msg)) {
						msg = "已存在";
					}
					throw new IdempotencyException(msg);
				}
			} else {
				throw new LockException("锁等待超时");
			}
		} finally {
			lock.releaseLock(lockKey);
		}
	}

	/**
	 * 幂等性新增记录
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @return
	 */
	@Override
	public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
		return saveIdempotency(entity, lock, lockKey, countWrapper, null);
	}

	/**
	 * 幂等性新增或更新记录
	 * 例子如下：
	 * String username = sysUser.getUsername();
	 * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
	 * , LOCK_KEY_USERNAME+username
	 * , new QueryWrapper<SysUser>().eq("username", username));
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @param msg          对象已存在提示信息
	 * @return
	 */
	@Override
	public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
		if (null != entity) {
			Class<?> cls = entity.getClass();
			TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
			if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
				Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
				if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
					if (StringUtils.isEmpty(msg)) {
						msg = "已存在";
					}
					return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
				} else {
					return updateById(entity);
				}
			} else {
				throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
			}
		}
		return false;
	}

	/**
	 * 幂等性新增或更新记录
	 * 例子如下：
	 * String username = sysUser.getUsername();
	 * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
	 * , LOCK_KEY_USERNAME+username
	 * , new QueryWrapper<SysUser>().eq("username", username));
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @return
	 */
	@Override
	public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
		return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper, null);
	}
}
