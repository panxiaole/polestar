package com.github.panxiaole.polestar.biz.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.panxiaole.polestar.datasource.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author panxiaole
 * @date 2019-07-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_DICT")
public class Dict extends BaseModel {

	@ApiModelProperty("类型")
	@TableField("TYPE")
	String type;

	@ApiModelProperty("值")
	@TableField("VALUE")
	String value;

}
