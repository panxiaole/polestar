package com.haier.polestar.api.controller.sys;

import com.haier.polestar.biz.sys.client.UserClient;
import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.common.exception.GlobalExceptionResolver;
import com.haier.polestar.common.response.Result;
import com.haier.polestar.starter.log.annotation.SystemLog;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author panxiaole
 * @date 2019-05-09
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends GlobalExceptionResolver {

	@Autowired
	private UserClient userClient;

	@GetMapping("/{id}")
	public User find(@PathVariable Long id) {
		return userClient.find("users", id);
	}

	/**
	 * 新增记录
	 *
	 * @param user user
	 * @return result
	 */
	@SystemLog
	@PostMapping
	public Result add(@RequestBody User user) {
		try {
			return userClient.add("users", user);
		} catch (FeignException e) {
			Throwable throwable = e.getCause();
			log.error(throwable.getMessage());
		}
		return null;
	}

	/**
	 * 修改记录
	 *
	 * @param user user
	 * @return result
	 */
	@SystemLog
	@PutMapping
	public Result update(@RequestBody User user) {
		return userClient.update("users", user);
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
		return userClient.delete("users", id);
	}

}
