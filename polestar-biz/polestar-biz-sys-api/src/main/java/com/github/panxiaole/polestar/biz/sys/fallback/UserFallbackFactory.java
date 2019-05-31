package com.github.panxiaole.polestar.biz.sys.fallback;

import com.github.panxiaole.polestar.biz.sys.client.UserClient;
import com.github.panxiaole.polestar.biz.sys.model.User;
import com.github.panxiaole.polestar.common.response.Result;
import com.github.panxiaole.polestar.common.response.ResultCode;
import com.github.panxiaole.polestar.common.response.ResultGenerator;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户管理业务服务降级
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@Component
public class UserFallbackFactory implements FallbackFactory<UserClient> {

	@Override
	public UserClient create(Throwable throwable) {
		log.error("调用UserFeignClient失败,执行降级策略...", throwable);
		return new UserClient() {

			@Override
			public User find(String module, Long id) {
				return new User();
			}

			@Override
			public Result<User> add(String module, User model) {
				return ResultGenerator.failed(ResultCode.SAVE_FAILED);
			}

			@Override
			public Result<User> update(String module, User model) {
				return ResultGenerator.failed(ResultCode.UPDATE_FAILED);
			}

			@Override
			public Result<User> delete(String module, Long id) {
				return ResultGenerator.failed(ResultCode.DELETE_FAILED);
			}

		};
	}
}
