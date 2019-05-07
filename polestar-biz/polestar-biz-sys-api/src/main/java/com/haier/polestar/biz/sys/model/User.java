package com.haier.polestar.biz.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.haier.polestar.datasource.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class User extends BaseModel{

}
