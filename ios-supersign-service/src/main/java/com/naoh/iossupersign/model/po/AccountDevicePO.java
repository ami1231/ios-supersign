package com.naoh.iossupersign.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peter.Hong
 * @date 2019/12/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDevicePO {


    @ApiModelProperty(value = "设备udid")
    private String udid;

    @ApiModelProperty(value = "设备在开发者后台id")
    private String deviceId;


    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "p8内容")
    private String p8;

    @ApiModelProperty(value = "iss")
    private String issuerId;

    @ApiModelProperty(value = "kid")
    private String kid;

    @ApiModelProperty(value = "p12文件地址")
    private String p12;

    @ApiModelProperty(value = "cerId")
    private String cerId;

    @ApiModelProperty(value = "开发者后台的通配证书id")
    private String bundleIds;
}
