package com.github.panxiaole.polestar.biz.sys.service;

import com.github.panxiaole.polestar.biz.sys.model.Dict;
import com.github.panxiaole.polestar.datasource.base.BaseService;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 字典管理接口
 *
 * @author panxiaole
 * @date 2019-05-07
 */
@Validated
public interface DictService extends BaseService<Dict> {

	/**
	 * 根据id查询字典
	 * @param id
	 * @return
	 */
	Dict getById(Long id);

	/**
	 * 根据类型查询字典
	 * @param type
	 * @return
	 */
	List<Dict> getByType(String type);

}
