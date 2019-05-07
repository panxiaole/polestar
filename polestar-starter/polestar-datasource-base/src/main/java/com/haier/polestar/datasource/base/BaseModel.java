package com.haier.polestar.datasource.base;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
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
	@TableField("VERSION")
	protected int version;

	/**
	 * 数据有效性
	 */
	@TableLogic
	@TableField("DELETED")
	protected Boolean deleted;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
	protected Date createdTime;

	/**
	 * 最后修改时间
	 */
	@TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
	protected Date updateTime;


}
