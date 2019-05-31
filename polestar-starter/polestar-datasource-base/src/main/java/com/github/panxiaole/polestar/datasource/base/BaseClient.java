package com.github.panxiaole.polestar.datasource.base;

import com.github.panxiaole.polestar.common.response.Result;
import org.springframework.web.bind.annotation.*;

/**
 * feign client CRUD接口父类
 *
 * @author panxiaole
 * @date 2019-04-20
 */
public interface BaseClient<T> {

	/**
	 * 根据id查找记录
	 *
	 * @param module 模块
	 * @param id     主键
	 * @return T
	 */
	@GetMapping("/api/{module}/{id}")
	T find(@PathVariable("module") String module, @PathVariable("id") Long id);

	/**
	 * 新增记录
	 *
	 * @param module 模块
	 * @param model  model
	 * @return result
	 */
	@PostMapping("/api/{module}")
	Result add(@PathVariable("module") String module, @RequestBody T model);

	/**
	 * 修改记录
	 *
	 * @param module 模块
	 * @param model  model
	 * @return result
	 */
	@PutMapping("/api/{module}")
	Result update(@PathVariable("module") String module, @RequestBody T model);

	/**
	 * 删除记录
	 *
	 * @param module 模块
	 * @param id     id
	 * @return result
	 */
	@DeleteMapping("/api/{module}/{id}")
	Result delete(@PathVariable("module") String module, @PathVariable("id") Long id);



}
