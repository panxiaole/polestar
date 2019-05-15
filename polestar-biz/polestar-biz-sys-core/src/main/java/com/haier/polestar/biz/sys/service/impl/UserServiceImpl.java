package com.haier.polestar.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haier.polestar.biz.sys.mapper.UserMapper;
import com.haier.polestar.biz.sys.model.User;
import com.haier.polestar.biz.sys.service.UserService;
import com.haier.polestar.common.response.Result;
import com.haier.polestar.common.response.ResultCode;
import com.haier.polestar.common.response.ResultGenerator;
import com.haier.polestar.datasource.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

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

	@Override
	public Result<User> add(User user) {
		return super.save(user) ? ResultGenerator.succeed() : ResultGenerator.failed("操作失败");
	}

	@Override
	public Result<User> update(User user) {
		return super.updateById(user) ? ResultGenerator.succeed() : ResultGenerator.failed(ResultCode.OPTIMISTIC_LOCKER_EXCEPTION);
	}

	@Override
	public Result<User> delete(Serializable id) {
		return super.removeById(id) ? ResultGenerator.succeed() : ResultGenerator.failed(ResultCode.OPTIMISTIC_LOCKER_EXCEPTION);
	}
}
