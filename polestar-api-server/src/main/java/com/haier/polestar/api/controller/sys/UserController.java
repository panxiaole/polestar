package com.haier.polestar.api.controller.sys;

import com.haier.polestar.biz.sys.client.UserClient;
import com.haier.polestar.biz.sys.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panxiaole
 * @date 2019-05-09
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserClient userClient;

	@GetMapping("/{id}")
	public User find(@PathVariable Long id) {
		return userClient.find(id);
	}

}
