package com.github.panxiaole.polestar.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.panxiaole.polestar.biz.sys.mapper.UserMapper;
import com.github.panxiaole.polestar.biz.sys.model.User;
import com.github.panxiaole.polestar.biz.sys.service.UserService;
import com.github.panxiaole.polestar.datasource.base.BaseServiceImpl;
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
		return super.getOne(new QueryWrapper<User>().eq("USERNAME", username));
	}

	@Override
	public User findByMobile(String mobile) {
		return super.getOne(new QueryWrapper<User>().eq("MOBILE", mobile));
	}

}
