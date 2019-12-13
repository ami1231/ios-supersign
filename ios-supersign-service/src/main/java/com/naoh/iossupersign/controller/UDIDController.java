package com.naoh.iossupersign.controller;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.naoh.iossupersign.service.udid.UDIDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
@RequestMapping("mobileconfig")
public class UDIDController {

    @Autowired
    private ServletContext context;

    @Autowired
    private UDIDBSService udidbsService;

    private static final int BUFFER_SIZE = 4096;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadMobileConfig(HttpServletRequest req , HttpServletResponse res) throws IOException {
        String mobileConfig = udidbsService.getMobileConfig();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(mobileConfig.getBytes().length);
        respHeaders.setContentType(MediaType.asMediaType(MimeType.valueOf("application/x-apple-aspen-config")));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "udid.mobileconfig");
        return new ResponseEntity<byte[]>(mobileConfig.getBytes(), respHeaders, HttpStatus.OK);
    }

    @PostMapping("/getUDID")
    public void getUDID(HttpServletRequest request ,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            //获取HTTP请求的输入流
            InputStream is = request.getInputStream();
            //已HTTP请求输入流建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            //读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                sb.append(buffer);
            }
            String xml = sb.toString().substring(sb.toString().indexOf("<?xml"), sb.toString().indexOf("</plist>")+8);
            NSDictionary parse = (NSDictionary) PropertyListParser.parse(xml.getBytes());
            String udid = (String) parse.get("UDID").toJavaObject();
            Boolean isSuccess = udidbsService.bindUdidToAppleAccount(udid);

            //TODO 导出IPA下载画面

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}