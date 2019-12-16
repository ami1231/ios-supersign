package com.naoh.iossupersign.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IpaPackagePO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "包名")
    private String name;

    @ApiModelProperty(value = "图标位置")
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

    @ApiModelProperty(value = "下载地址")
    private String link;

    @ApiModelProperty(value = "总下载量")
    private long count;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "user下載資源位置")
    private String ipaDownloadId;

    private String downloadUrl;

    private String mobileFileName;

}
