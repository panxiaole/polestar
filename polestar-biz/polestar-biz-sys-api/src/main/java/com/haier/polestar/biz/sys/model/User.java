package com.haier.polestar.biz.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haier.polestar.datasource.base.BaseModel;
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
@TableName("sys_user")
public class User extends BaseModel {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
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
	@TableField("NICKNAME")
	@NotEmpty(message = "昵称不能为空")
	private String nickname;

	/**
	 * 手机号码
	 */
	@ApiModelProperty("手机号码")
	@TableField("MOBILE")
	@NotEmpty(message = "手机号码不能为空")
	private String mobile;

	/**
	 * 邮箱
	 */
	@ApiModelProperty("邮箱地址")
	@TableField("EMAIL")
	@Email
	@NotEmpty(message = "邮箱不能为空")
	private String email;

	/**
	 * 是否被锁定
	 */
	@ApiModelProperty("被锁定")
	@TableField("LOCKED")
	private Boolean locked;

	/**
	 * 账户过期时间
	 */
	@ApiModelProperty("账户过期时间")
	@TableField("ACCOUNT_EXPIRE_DATE")
	private Date accountExpireDate;

	/**
	 * 凭证过期时间
	 */
	@ApiModelProperty("凭证过期时间")
	@TableField("CREDENTIALS_EXPIRE_DATE")
	private Date credentialsExpireDate;

	/**
	 * 最后登录时间
	 */
	@ApiModelProperty("最后登录时间")
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

}
