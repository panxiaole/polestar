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
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
	 * 构建工作表
	 *
	 * @param exportParams 导出参数
	 * @param list         数据集合
	 * @return result
	 */
	Workbook buildWorkbook(ExportParams exportParams, List<T> list);

	/**
	 * 下载导出文件
	 *
	 * @param list         数据列表
	 * @param exportParams 导出参数
	 * @param map          模型
	 * @param request      请求
	 * @param response     响应
	 */
	void downloadExportFile(List<T> list, ExportParams exportParams, ModelMap map,
	                        HttpServletRequest request, HttpServletResponse response);

	/**
	 * 存储导出文件
	 *
	 * @param exportParams 导出参数
	 * @param list         数据集合
	 * @return 存储结果
	 * @throws IOException IOException
	 */
	Result<String> saveExportFile(ExportParams exportParams, List<T> list) throws IOException;

	/**
	 * 下载导入模板
	 * 需要将模板放入resources/import目录下
	 * 命名规则为@ApiModel中的value + 导入模板.xls
	 *
	 * @param response 响应
	 * @throws IOException IOException
	 */
	void downloadImportTemplate(HttpServletResponse response) throws IOException;

	/**
	 * 解析上传文件并返回数据集合
	 *
	 * @param file 上传文件
	 * @return list
	 * @throws IOException IOException
	 */
	List<T> analyzeImportTemplate(MultipartFile file) throws IOException;

	/**
	 * 模板导入
	 *
	 * @param file 上传文件
	 * @return 导入结果
	 * @throws IOException IOException
	 */
	@Transactional(rollbackFor = Exception.class)
	Result<String> templateImport(MultipartFile file) throws IOException;

	/**
	 * 导入时填充对某些不需要填写的字段进行自定义填充或处理
	 * 例如导入用户时对密码进行加密
	 *
	 * @param list list
	 */
	void fillCustomizedValue(List<T> list);


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
