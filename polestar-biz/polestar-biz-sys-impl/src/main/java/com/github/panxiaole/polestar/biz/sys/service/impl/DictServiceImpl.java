package com.github.panxiaole.polestar.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.panxiaole.polestar.biz.sys.mapper.DictMapper;
import com.github.panxiaole.polestar.biz.sys.model.Dict;
import com.github.panxiaole.polestar.biz.sys.service.DictService;
import com.github.panxiaole.polestar.datasource.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 字典管理接口实现
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements DictService {

	@Resource
	private DictMapper dictMapper;

	@Override
	@Cacheable(cacheNames = "dict::id", key = "#a0")
	public Dict getById(Long id) {
		return super.getById(id);
	}


	@Override
	@Cacheable(cacheNames = "dict::type", key = "#a0")
	public List<Dict> getByType(String type) {
		return super.list(new QueryWrapper<Dict>().eq("TYPE", type));
	}
}
