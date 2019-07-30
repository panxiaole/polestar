package com.github.panxiaole.polestar.datasource.base;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.panxiaole.polestar.datasource.annotation.QueryCondition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 实体类父类
 * 包含主键及主键生成策略、乐观锁、有效性、创建及修改信息
 *
 * @author panxiaole
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public abstract class BaseModel extends Model {

	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	@Excel(name = "ID")
	@TableId(value = "ID", type= IdType.ID_WORKER)
	protected Long id;

	/**
	 * 乐观锁版本号
	 */
	@ApiModelProperty("版本号")
	@Version
	@TableField(value = "VERSION", fill = FieldFill.INSERT)
	protected Integer version;

	/**
	 * 数据有效性
	 */
	@ApiModelProperty("是否被删除")
	@Excel(name = "是否被删除", replace = {"是_true", "否_false"}, orderNum = "100")
	@TableLogic
	@TableField(value = "DELETED", fill = FieldFill.INSERT)
	protected Boolean deleted;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@Excel(name = "创建时间", orderNum = "98", format = "yyyy-MM-dd HH:mm:ss")
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
	protected Date createTime;

	/**
	 * 最后修改时间
	 */
	@ApiModelProperty("最后修改时间")
	@Excel(name = "最后修改时间", orderNum = "99", format = "yyyy-MM-dd HH:mm:ss")
	@TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
	protected Date updateTime;

	/**
	 * 创建时间--开始
	 * 用于查询
	 */
	@ApiModelProperty("创建时间--开始")
	@TableField(exist = false)
	@QueryCondition(field = "CREATE_TIME", condition = QueryCondition.Contition.GE)
	protected Date createTimeBegin;

	/**
	 * 创建时间--结束
	 * 用于查询
	 */
	@ApiModelProperty("创建时间--结束")
	@TableField(exist = false)
	@QueryCondition(field = "CREATE_TIME", condition = QueryCondition.Contition.LT)
	protected Date createTimeEnd;

}
