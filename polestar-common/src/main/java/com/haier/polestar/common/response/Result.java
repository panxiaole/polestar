package com.haier.polestar.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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

	private T data;

	private Boolean success;

	private Integer code;

	private String message;

}
