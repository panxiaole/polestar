package com.haier.polestar.biz.sys.fallback;

import com.haier.polestar.biz.sys.client.UserClient;
import com.haier.polestar.biz.sys.model.User;
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
		log.warn("调用UserFeignClient失败,执行降级策略...");
		return new UserClient() {

			@Override
			public User find(Long id) {
				return new User();
			}

			@Override
			public void add(User user) {
			}
		};
	}
}
