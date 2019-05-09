package com.haier.polestar.biz.sys.client;

import com.haier.polestar.biz.sys.fallback.UserFallbackFactory;
import com.haier.polestar.biz.sys.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 feign client
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@RequestMapping("/api/users")
@FeignClient(contextId = "userClient", name = "polestar-biz-sys", fallbackFactory = UserFallbackFactory.class)
public interface UserClient {

	/**
	 * 根据id查找用户
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	User find(@PathVariable("id") Long id);

	/**
	 * 增加用户
	 *
	 * @param user
	 * @return
	 */
	@PostMapping
	void add(@RequestBody User user);
}
