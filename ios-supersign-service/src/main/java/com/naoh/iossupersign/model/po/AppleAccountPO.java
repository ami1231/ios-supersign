package com.naoh.iossupersign.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppleAccountPO {

  @ApiModelProperty(value = "id")
  private long id;

  @ApiModelProperty(value = "账号")
  private String account;

  @ApiModelProperty(value = "可用数量")
  private long count;

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

  @ApiModelProperty(value = "添加时间")
  private LocalDateTime createTime;

}
