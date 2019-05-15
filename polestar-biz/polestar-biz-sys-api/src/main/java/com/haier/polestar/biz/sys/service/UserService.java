package com.haier.polestar.biz.sys.service;

import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.common.response.Result;
import com.haier.polestar.datasource.base.BaseService;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.Serializable;

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

	/**
	 * 新增用户
	 *
	 * @param user user
	 * @return result
	 */
	Result<User> add(@Valid User user);

	/**
	 * 修改用户
	 *
	 * @param user user
	 * @return result
	 */
	Result<User> update(@Valid User user);

	/**
	 * 删除用户
	 *
	 * @param id id
	 * @return result
	 */
	Result<User> delete(Serializable id);

}
