package com.naoh.iossupersign.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@ApiModel(value = "设备对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DevicePO {

  @ApiModelProperty(value = "id")
  private Long id;

  @ApiModelProperty(value = "设备udid")
  private String udid;

  @ApiModelProperty(value = "绑定的开发者账号")
  private Long appleId;

  @ApiModelProperty(value = "设备在开发者后台id")
  private String deviceId;

  private LocalDate createTime;
}