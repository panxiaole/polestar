package com.github.panxiaole.polestar.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author panxiaole
 * @date   2019-05-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("响应数据")
	private T data;

	@ApiModelProperty("是否成功")
	private Boolean success;

	@ApiModelProperty("响应编码")
	private Integer code;

	@ApiModelProperty("响应信息")
	private String message;

}
