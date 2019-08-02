package com.github.panxiaole.polestar.datasource.base;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.panxiaole.polestar.common.exception.IdempotencyException;
import com.github.panxiaole.polestar.common.exception.LockException;
import com.github.panxiaole.polestar.common.lock.DistributedLock;
import com.github.panxiaole.polestar.common.response.Result;
import com.github.panxiaole.polestar.common.response.ResultCode;
import com.github.panxiaole.polestar.common.response.ResultGenerator;
import com.github.panxiaole.polestar.common.utils.ReflectUtil;
import com.github.panxiaole.polestar.common.utils.StringUtil;
import com.github.panxiaole.polestar.datasource.annotation.QueryCondition;
import com.github.panxiaole.polestar.redis.annotation.NeedMapValue;
import io.swagger.annotations.ApiModel;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * service实现父类
 *
 * @author panxiaole
 * @date 2019-04-20
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	protected Class pojoClass = ReflectUtil.getClassGenericType(getClass(), 1);

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
	 * 查询全部符合条件的记录
	 *
	 * @param model 查询条件
	 * @return list
	 */
	@Override
	@NeedMapValue
	public List<T> selectList(T model) {
		QueryWrapper<T> queryWrapper = this.buildQueryWrapper(model);
		return super.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 分页查询符合条件的记录
	 *
	 * @param page  分页对象
	 * @param model 查询条件
	 * @return page
	 */
	@Override
	@NeedMapValue
	public IPage<T> selectPage(Page<T> page, T model) {
		QueryWrapper<T> queryWrapper = this.buildQueryWrapper(model);
		return super.baseMapper.selectPage(page, queryWrapper);
	}

	/**
	 * 构建工作表
	 *
	 * @param exportParams 导出参数
	 * @param list         数据集合
	 * @return Workbook
	 */
	@Override
	public Workbook buildWorkbook(ExportParams exportParams, List<T> list) {
		return ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
	}

	/**
	 * 下载导出文件
	 *
	 * @param list         数据列表
	 * @param exportParams 导出参数
	 * @param map          模型
	 * @param request      请求
	 * @param response     响应
	 */
	@Override
	public void downloadExportFile(List<T> list, ExportParams exportParams, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		map.put(NormalExcelConstants.DATA_LIST, list);
		map.put(NormalExcelConstants.CLASS, pojoClass);
		map.put(NormalExcelConstants.PARAMS, exportParams);
		map.put(NormalExcelConstants.FILE_NAME, this.generateExportFileName());
		PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
	}

	/**
	 * 存储导出文件
	 *
	 * @param exportParams 导出参数
	 * @param list         数据集合
	 * @return 存储结果
	 * @throws IOException
	 */
	@Override
	public Result<String> saveExportFile(ExportParams exportParams, List<T> list) throws IOException {
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
		String filePath = System.getProperty("user.home")
				+ File.separator + "export"
				+ File.separator + pojoClass.getSimpleName();
		String fileName = this.generateExportFileName();
		File directory = new File(filePath);
		if (!directory.exists() && !directory.mkdirs()) {
			return ResultGenerator.failed("导出失败,无法创建目录");
		}
		FileOutputStream fos = new FileOutputStream(filePath + File.separator + fileName);
		workbook.write(fos);
		fos.close();
		return ResultGenerator.succeed("导出成功", filePath + File.separator + fileName);
	}

	/**
	 * 下载导入模板
	 *
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void downloadImportTemplate(HttpServletResponse response) throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(String.format("/import/%s导入模板.xls", getClassDescription()));
		response.setHeader("contentType", "application/x-download");
		response.setHeader("Content-Disposition",
				String.format("attachment;filename*=utf-8'zh_cn'%s.xls", URLEncoder.encode(String.format("%s导入模板", getClassDescription()), "UTF-8")));
		IOUtils.copy(inputStream, response.getOutputStream());
	}

	/**
	 * 解析上传文件并返回数据集合
	 *
	 * @param file 上传文件
	 * @return list
	 * @throws IOException IOException
	 */
	@Override
	public List<T> analyzeImportTemplate(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String filePath = "/Users/panxiaole/upload";
		File dest = new File(filePath + File.separator + fileName);
		file.transferTo(dest);
		ImportParams importParams = new ImportParams();
		importParams.setTitleRows(0);
		importParams.setHeadRows(1);
		return ExcelImportUtil.importExcel(dest, ReflectUtil.getClassGenericType(getClass(), 1), importParams);
	}

	/**
	 * 模板导入
	 *
	 * @param file 上传文件
	 * @return 导入结果
	 */
	@Override
	public Result<String> templateImport(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return ResultGenerator.failed("上传失败，请选择文件");
		}
		List<T> list = this.analyzeImportTemplate(file);
		if (list.isEmpty()) {
			return ResultGenerator.failed("导入失败:未在模板中解析出有效数据");
		}
		this.fillCustomizedValue(list);
		return super.saveBatch(list) ? ResultGenerator.succeed(String.format("成功导入%s条数据", String.valueOf(list.size()))) : ResultGenerator.failed("导入失败");
	}

	/**
	 * 导入时填充对某些不需要填写的字段进行自定义填充或处理
	 * 例如导入用户时对密码进行加密
	 *
	 * @param list list
	 */
	@Override
	public void fillCustomizedValue(List<T> list) {
	}

	/**
	 * 获取类描述
	 *
	 * @return 类描述
	 */
	private String getClassDescription() {
		ApiModel annotation = (ApiModel) pojoClass.getAnnotation(ApiModel.class);
		return annotation != null ? annotation.value() : null;
	}

	/**
	 * 生成导出文件的文件名
	 *
	 * @return 文件名
	 */
	private String generateExportFileName() {
		return (getClassDescription() != null ? getClassDescription() : pojoClass.getSimpleName()) + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
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
				if (tableField.exist()) {
					//实体字段直接用equals构建查询条件
					wrapper.eq(tableField.value(), field.get(model));
					continue;
				}
				//非实体字段检查是否有QueryCondition注解,并根据注解中的参数组织查询条件
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
		if (StringUtil.isEmpty(lockKey)) {
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
					if (StringUtil.isEmpty(msg)) {
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
			if (null != tableInfo && StringUtil.isNotEmpty(tableInfo.getKeyProperty())) {
				Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
				if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
					if (StringUtil.isEmpty(msg)) {
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
