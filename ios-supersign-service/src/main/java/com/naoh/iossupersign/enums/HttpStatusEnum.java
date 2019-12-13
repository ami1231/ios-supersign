package com.naoh.iossupersign.enums;

/**
 * @author Ami
 * @data 2019/10/1 下午 07:35
 */
public enum HttpStatusEnum {
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    RESOURCE_NOT_FOUND(404, "Resource Not Found"),
    CONFLICT(409, "Conflict"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    SERVER_ERROR(500, "Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    public Integer status;
    public String description;

    private HttpStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }
}
