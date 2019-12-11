package com.naoh.iossupersign.model.dto;

import java.util.HashMap;

public class AppleReqBody extends HashMap<String,Object>{

    public AppleReqBody add(String key , Object value){
        this.put(key,value);
        return this;
    }

    public static AppleReqBody init(){
        return new AppleReqBody();
    }

    private AppleReqBody(){}

}