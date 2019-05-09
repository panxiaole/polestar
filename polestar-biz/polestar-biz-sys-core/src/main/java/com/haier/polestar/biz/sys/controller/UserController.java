package com.haier.polestar.biz.sys.controller;

import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.biz.sys.service.UserService;
import com.haier.polestar.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public User find(@PathVariable Long id) {
		return userService.getById(id);
	}

	/**
	 * 新增or更新
	 *
	 * @param user
	 * @return
	 */
	@CacheEvict(value = "user", key = "#sysUser.username")
	@PostMapping
	public Result saveOrUpdate(@RequestBody User user) {
		userService.save(user);
		return null;
	}


}
