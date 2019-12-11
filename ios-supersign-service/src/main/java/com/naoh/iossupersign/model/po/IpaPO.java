package com.naoh.iossupersign.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IpaPO implements Serializable {

    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "包名")
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "编译版本号")
    private String buildVersion;

    @ApiModelProperty(value = "最小支持版本")
    private String miniVersion;

    @ApiModelProperty(value = "安装包id")
    private String bundleIdentifier;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "获取UDID证书地址")
    private String mobileconfig;

    @ApiModelProperty(value = "下载地址")
    private String link;

    @ApiModelProperty(value = "总下载量")
    private long count;

}
