package com.haier.polestar.biz.sys.service;

import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.datasource.base.BaseService;
import org.springframework.validation.annotation.Validated;

/**
 * 用户管理接口
 *
 * @author panxiaole
 * @date 2019-05-07
 */
@Validated
public interface UserService extends BaseService<User> {

	/**
	 * 查找唯一用户
	 *
	 * @param username 用户名
	 * @return user
	 */
	User findByUsername(String username);

	/**
	 * 查找唯一用户
	 *
	 * @param mobile 手机
	 * @return user
	 */
	User findByMobile(String mobile);

}
