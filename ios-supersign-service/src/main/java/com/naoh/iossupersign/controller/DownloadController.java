package com.naoh.iossupersign.controller;

import com.naoh.iossupersign.service.plist.PlistBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peter.Hong
 * @date 2019/12/14
 */
@Api("app下載")
@RestController
@RequestMapping("apps")
public class DownloadController {


    @Autowired
    private PlistBService plistBService;

    @ApiOperation(value = "download/{ipaDownloadId}/{udid}", notes = "下載Plist")
    @GetMapping("download/{ipaDownloadId}/{udid}/manifest.plist")
    public ResponseEntity<byte[]> downloadAppPlist(@PathVariable String ipaDownloadId, @PathVariable String udid){
        String plist = plistBService.getPlist(ipaDownloadId, udid);
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(plist.getBytes().length);
        respHeaders.setContentType(MediaType.TEXT_XML);
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "manifest.plist");
        return new ResponseEntity<byte[]>(plist.getBytes(), respHeaders, HttpStatus.OK);
    }

}
