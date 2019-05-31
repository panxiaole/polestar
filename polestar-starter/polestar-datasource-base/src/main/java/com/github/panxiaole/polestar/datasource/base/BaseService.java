package com.github.panxiaole.polestar.datasource.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.panxiaole.polestar.common.lock.DistributedLock;
import com.github.panxiaole.polestar.common.response.Result;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * service接口父类
 *
 * @author panxiaole
 * @date 2019-04-20
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 新增记录
     *
     * @param model model
     * @return result
     */
    @Transactional(rollbackFor = Exception.class)
    Result<T> add(@Valid T model);

    /**
     * 修改记录
     *
     * @param model model
     * @return result
     */
    @Transactional(rollbackFor = Exception.class)
    Result<T> update(@Valid T model);

    /**
     * 删除记录
     *
     * @param id id
     * @return result
     */
    @Transactional(rollbackFor = Exception.class)
    Result<T> delete(Serializable id);

    /**
     * 分页
     * @param page 分页对象
     * @param model 查询条件
     * @return page
     */
    IPage<T> selectPage(Page<T> page, T model);

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg);

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper);
}
