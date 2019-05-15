package com.haier.polestar.biz.sys.controller;

import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.biz.sys.service.UserService;
import com.haier.polestar.datasource.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<User, UserService> {

	@Autowired
	private UserService userService;

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

}
