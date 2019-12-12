package com.naoh.iossupersign.enums;

/**
 * @author Ami
 * @data 2019/12/12 下午 07:34
 */
public enum ServiceError {
    GENERAL_ERROR(HttpStatusEnum.SERVER_ERROR.status, "0010001", "系统忙碌中,请稍后再试"),
    INVALID_PARAMETER(HttpStatusEnum.BAD_REQUEST.status, "0010002", "无效参数"),
    DATA_NOT_FOUND(HttpStatusEnum.RESOURCE_NOT_FOUND.status, "0010003", "找不到资料"),
    INSERT_DATA_FAILURE(HttpStatusEnum.SERVER_ERROR.status, "0010004", "新增资料失败"),
    UPDATE_DATA_FAILURE(HttpStatusEnum.SERVER_ERROR.status, "0010005", "更新资料失败"),
    DELETE_DATA_FAILURE(HttpStatusEnum.SERVER_ERROR.status, "0010006", "删除资料失败"),
    ;
    public Integer httpStatus;
    public String code;
    public String msg;

    private ServiceError(Integer httpStatus, String code, String msg) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    public Integer getHttpStatus() {
        return this.httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
