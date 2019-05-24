package com.haier.polestar.datasource.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haier.polestar.common.response.Result;
import com.haier.polestar.starter.log.annotation.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制类基类
 *
 * @author panxiaole
 * @date 2019-05-15
 */
public abstract class BaseController<T, S extends BaseService<T>> {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private S baseService;

	/**
	 * 根据id查询记录
	 *
	 * @param id 主键
	 * @return model
	 */
	@SystemLog
	@GetMapping("/{id}")
	public T find(@PathVariable Long id) {
		return baseService.getById(id);
	}

	/**
	 * 新增记录
	 *
	 * @param model model
	 * @return result
	 */
	@SystemLog
	@PostMapping
	public Result add(@RequestBody T model) {
		return baseService.add(model);
	}

	/**
	 * 修改记录
	 *
	 * @param model model
	 * @return result
	 */
	@SystemLog
	@PutMapping
	public Result update(@RequestBody T model) {
		return baseService.update(model);
	}

	/**
	 * 删除记录
	 *
	 * @param id id
	 * @return result
	 */
	@SystemLog
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long id) {
		return baseService.delete(id);
	}

	/**
	 * 分页
	 * @param page 分页对象
	 * @param model 查询条件
	 * @return page
	 */
	@SystemLog
	@GetMapping
	public IPage<T> selectPage(Page<T> page, T model) {
		return baseService.selectPage(page, model);
	}
}
