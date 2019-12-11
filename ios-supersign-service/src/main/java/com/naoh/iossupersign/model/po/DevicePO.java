package com.naoh.iossupersign.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "设备对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DevicePO {

  @ApiModelProperty(value = "id")
  private long id;

  @ApiModelProperty(value = "设备udid")
  private String udid;

  @ApiModelProperty(value = "绑定的开发者账号")
  private long appleId;

  @ApiModelProperty(value = "安装过的安装包id")
  private String packageIds;

  @ApiModelProperty(value = "设备在开发者后台id")
  private String deviceId;

}