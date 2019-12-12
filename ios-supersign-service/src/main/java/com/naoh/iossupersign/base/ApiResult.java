package com.naoh.iossupersign.base;

import com.naoh.iossupersign.enums.ServiceError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.security.krb5.internal.PAForUserEnc;

/**
 * 
 * @author Billy
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T>{
	public static final String SUCCESS_CODE = "200";
	public static final String FAILD_CODE = "500";
	public static final String SUCCESS_MSG = "SUCCESS";
	public static final String ERROR_MSG = "ERROR";
	
	private T data;
	private String code = FAILD_CODE;
	private String msg;

	public ApiResult(ServiceError serviceError){
		this.msg = serviceError.msg;
	}

	public ApiResult(String msg){
		this.msg = msg;
	}
}
