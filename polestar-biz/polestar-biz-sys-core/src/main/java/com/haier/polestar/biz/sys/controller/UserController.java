package com.haier.polestar.biz.sys.controller;

import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.biz.sys.service.UserService;
import com.haier.polestar.datasource.base.BaseController;
import com.haier.polestar.redis.annotation.CacheExpire;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@Api("用户管理API")
@RestController
@RequestMapping("/api/users")
@CacheConfig(cacheNames = "users")
public class UserController extends BaseController<User, UserService> {

	@Autowired
	private UserService userService;

	/**
	 * 根据用户名查找用户
	 *
	 * @param username 用户名
	 * @return user
	 */
	@ApiOperation(value = "根据用户名查询用户信息")
	@GetMapping("/username/{username}")
	@Cacheable(cacheNames = "users::username", key = "#username")
	@CacheExpire(30)
	public User findByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}

	/**
	 * 根据手机查找用户
	 *
	 * @param mobile 手机
	 * @return user
	 */
	@ApiOperation(value = "根据手机号查询用户信息")
	@GetMapping("/mobile/{mobile}")
	@Cacheable(cacheNames = "users::mobile", key = "#mobile")
	@CacheExpire(30)
	public User findByMobile(@PathVariable String mobile) {
		return userService.findByMobile(mobile);
	}

}
