package com.haier.polestar.biz.sys.client;

import com.haier.polestar.biz.sys.fallback.UserFallbackFactory;
import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.datasource.base.BaseClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户管理 feign client
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@FeignClient(contextId = "userClient", name = "polestar-biz-sys", fallbackFactory = UserFallbackFactory.class, decode404 = true)
public interface UserClient extends BaseClient<User> {

}
