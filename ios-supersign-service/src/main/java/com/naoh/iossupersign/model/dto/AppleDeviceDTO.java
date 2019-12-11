package com.naoh.iossupersign.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppleDeviceDTO implements Serializable {

    private String type;

    private String id;

    private Map<String,?> attributes;

}
