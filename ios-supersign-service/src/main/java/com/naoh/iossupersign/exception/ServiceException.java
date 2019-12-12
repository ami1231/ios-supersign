package com.naoh.iossupersign.exception;

import com.naoh.iossupersign.enums.ServiceError;
import lombok.Data;

/**
 * @author RayChen
 * @date 2019年1月21日
 */
@Data
public class ServiceException extends RuntimeException {

    private ServiceError serviceError;

    public ServiceException(ServiceError serviceError) {
        this.serviceError = serviceError;
    }

    public String getErrorMsg(){
        return serviceError.msg;
    }
}