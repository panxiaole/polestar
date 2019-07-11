package com.github.panxiaole.polestar.biz.sys.controller;

import com.github.panxiaole.polestar.biz.sys.model.Dict;
import com.github.panxiaole.polestar.biz.sys.service.DictService;
import com.github.panxiaole.polestar.datasource.base.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理控制类
 *
 * @author panxiaole
 * @date 2019-05-08
 */
@Slf4j
@Api(tags = "字典管理")
@RestController
@RequestMapping("/api/dicts")
@CacheConfig(cacheNames = "dict")
public class DictController extends BaseController<Dict, DictService> {

	@Autowired
	private DictService dictService;

}
