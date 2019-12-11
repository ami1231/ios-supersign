package com.naoh.iossupersign.controller;

import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Api("ipa管理")
@Controller
@RequestMapping("ipa")
public class IpaController {

    @Autowired
    private IpaPackageBSService ipaPackageBSService;

    @RequestMapping("/index")
    public String index(){
        return "supersignature/ipa/index";
    }


    @ApiOperation(value = "/uploadPackage", notes = "上传ipa", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @PostMapping(value = "/uploadPackage")
    public void uploadPackage(@RequestPart("file")MultipartFile file, String summary) {
        ipaPackageBSService.uploadIpa(file, summary);
    }
}
