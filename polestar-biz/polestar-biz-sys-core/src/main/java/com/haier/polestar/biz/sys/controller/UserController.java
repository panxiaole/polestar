package com.haier.polestar.biz.sys.controller;

import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.biz.sys.service.UserService;
import com.haier.polestar.common.exception.GlobalExceptionResolver;
import com.haier.polestar.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@CacheConfig(cacheNames = "users")
@RestController
@RequestMapping("/api/users")
public class UserController extends GlobalExceptionResolver {

	@Autowired
	private UserService userService;

	/**
	 * 根据id查询用户
	 *
	 * @param id 主键
	 * @return user
	 */
	@GetMapping("/{id}")
	@Cacheable(value = "user", key = "#id")
	public User find(@PathVariable Long id) {
		return userService.getById(id);
	}

	/**
	 * 根据用户名查找用户
	 *
	 * @param username 用户名
	 * @return user
	 */
	@GetMapping("/username/{username}")
	public User findByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}

	/**
	 * 根据手机查找用户
	 *
	 * @param mobile 手机
	 * @return user
	 */
	@GetMapping("/mobile/{mobile}")
	public User findByMobile(@PathVariable String mobile) {
		return userService.findByMobile(mobile);
	}

	/**
	 * 新增用户
	 *
	 * @param user user
	 * @return result
	 */
	@PostMapping
	public Result add(@RequestBody User user) {
		return userService.add(user);
	}

	/**
	 * 修改用户
	 *
	 * @param user user
	 * @return result
	 */
	@PutMapping
	public Result update(@RequestBody User user) {
		return userService.update(user);
	}

	/**
	 * 删除
	 *
	 * @param id id
	 * @return result
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long id) {
		return userService.delete(id);
	}

}
