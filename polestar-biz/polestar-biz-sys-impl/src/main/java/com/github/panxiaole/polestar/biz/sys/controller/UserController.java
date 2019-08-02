package com.github.panxiaole.polestar.biz.sys.controller;

import com.github.panxiaole.polestar.biz.sys.model.User;
import com.github.panxiaole.polestar.biz.sys.service.UserService;
import com.github.panxiaole.polestar.datasource.base.BaseController;
import com.github.panxiaole.polestar.redis.annotation.CacheExpire;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户管理
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/users")
@CacheConfig(cacheNames = "user")
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
	@Cacheable(cacheNames = "user::username", key = "#username")
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
	@Cacheable(cacheNames = "user::mobile", key = "#mobile")
	@CacheExpire(30)
	public User findByMobile(@PathVariable String mobile) {
		return userService.findByMobile(mobile);
	}

	@GetMapping("/upload")
	public ModelAndView upload(@NotNull ModelAndView model) {
		model.setViewName("upload");
		return model;
	}
}
