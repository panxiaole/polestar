package com.haier.polestar.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haier.polestar.biz.sys.mapper.UserMapper;
import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.biz.sys.service.UserService;
import com.haier.polestar.datasource.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户管理接口实现
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

	@Override
	public User findByUsername(String username) {
		return super.getOne(new QueryWrapper<User>().eq("username", username));
	}

	@Override
	public User findByMobile(String mobile) {
		return super.getOne(new QueryWrapper<User>().eq("mobile", mobile));
	}

}
