package com.naoh.iossupersign.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppleApiResult<T> implements Serializable {

    private T data;

    private Map<String,Object> links;

    private Map<String,Object> meta;
}