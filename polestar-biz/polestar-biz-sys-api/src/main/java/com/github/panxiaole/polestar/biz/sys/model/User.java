package com.github.panxiaole.polestar.biz.sys.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.panxiaole.polestar.biz.sys.service.DictService;
import com.github.panxiaole.polestar.datasource.base.BaseModel;
import com.github.panxiaole.polestar.redis.annotation.MapValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author panxiaole
 * @date 2019-05-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户")
@TableName("sys_user")
public class User extends BaseModel {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	@Excel(name = "用户名", orderNum = "1")
	@TableField("USERNAME")
	@NotEmpty(message = "用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@ApiModelProperty("密码")
	@TableField("PASSWORD")
	@NotEmpty(message = "密码不能为空")
	private String password;

	/**
	 * 昵称
	 */
	@ApiModelProperty("昵称")
	@Excel(name = "昵称", orderNum = "2")
	@TableField("NICKNAME")
	@NotEmpty(message = "昵称不能为空")
	private String nickname;

	/**
	 * 手机号码
	 */
	@ApiModelProperty("手机号码")
	@Excel(name = "手机号码", orderNum = "3")
	@TableField("MOBILE")
	@NotEmpty(message = "手机号码不能为空")
	private String mobile;

	/**
	 * 邮箱
	 */
	@ApiModelProperty("邮箱地址")
	@Excel(name = "邮箱地址", orderNum = "3")
	@TableField("EMAIL")
	@Email
	@NotEmpty(message = "邮箱不能为空")
	private String email;

	/**
	 * 是否被锁定
	 */
	@ApiModelProperty("被锁定")
	@Excel(name = "被锁定", replace = {"是_true", "否_false"}, orderNum = "4")
	@TableField("LOCKED")
	private Boolean locked;

	/**
	 * 账户过期时间
	 */
	@ApiModelProperty("账户过期时间")
	@Excel(name = "账户过期时间", orderNum = "5", format = "yyyy-MM-dd HH:mm:ss")
	@TableField("ACCOUNT_EXPIRE_DATE")
	private Date accountExpireDate;

	/**
	 * 凭证过期时间
	 */
	@ApiModelProperty("凭证过期时间")
	@Excel(name = "凭证过期时间", orderNum = "6", format = "yyyy-MM-dd HH:mm:ss")
	@TableField("CREDENTIALS_EXPIRE_DATE")
	private Date credentialsExpireDate;

	/**
	 * 最后登录时间
	 */
	@ApiModelProperty("最后登录时间")
	@Excel(name = "最后登录时间", orderNum = "7", format = "yyyy-MM-dd HH:mm:ss")
	@TableField("LAST_LOGIN_TIME")
	private Date lastLoginTime;

	/**
	 * 原始密码
	 */
	@ApiModelProperty("原始密码")
	@TableField(exist = false)
	private String oldPassword;

	/**
	 * 新密码
	 */
	@ApiModelProperty("新密码")
	@TableField(exist = false)
	private String newPassword;

	/**
	 * 用户类型
	 */
	@ApiModelProperty("用户类型")
	@Excel(name = "用户类型编码", orderNum = "9", isColumnHidden = true)
	@TableField("TYPE")
	private Long type;


	@MapValue(bean = DictService.class, method = "getById", source = "type", target = "value")
	@Excel(name = "用户类型", orderNum = "8")
	@TableField(exist = false)
	private String typeName;

}
