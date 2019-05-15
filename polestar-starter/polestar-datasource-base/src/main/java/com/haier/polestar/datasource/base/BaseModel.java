package com.haier.polestar.datasource.base;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
	@TableId(value = "ID", type= IdType.AUTO)
	protected Long id;

	/**
	 * 乐观锁版本号
	 */
	@Version
	@TableField(value = "VERSION", fill = FieldFill.INSERT)
	protected Integer version;

	/**
	 * 数据有效性
	 */
	@TableLogic
	@TableField(value = "DELETED", fill = FieldFill.INSERT)
	protected Boolean deleted;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
	protected Date createTime;

	/**
	 * 最后修改时间
	 */
	@TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
	protected Date updateTime;

}
