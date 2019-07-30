package com.github.panxiaole.polestar.datasource.base;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.panxiaole.polestar.common.lock.DistributedLock;
import com.github.panxiaole.polestar.common.response.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

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
	 * 查询所有符合条件的记录
	 *
	 * @param model 查询条件
	 * @return page
	 */
	List<T> selectList(T model);

	/**
	 * 分页查询符合条件的记录
	 *
	 * @param page  分页对象
	 * @param model 查询条件
	 * @return page
	 */
	IPage<T> selectPage(Page<T> page, T model);

	/**
	 * 导出所有符合条件的记录
	 *
	 * @param exportParams 导出参数
	 * @param list         数据集合
	 * @return result
	 * @throws IOException
	 */
	Workbook export(ExportParams exportParams, List<T> list);

	/**
	 * 下载导出文件
	 * @param workbook      工作表
	 * @param response      响应
	 * @throws IOException
	 */
	void downloadExportFile(Workbook workbook, HttpServletResponse response) throws IOException;

	/**
	 * 存储导出文件
	 * @param workbook      工作表
	 * @return              存储结果
	 * @throws IOException
	 */
	Result<String> saveExportFile(Workbook workbook) throws IOException;

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
