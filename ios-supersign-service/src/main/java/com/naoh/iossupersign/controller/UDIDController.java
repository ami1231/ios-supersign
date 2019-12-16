package com.naoh.iossupersign.controller;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.naoh.iossupersign.config.DomainConfig;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageService;
import com.naoh.iossupersign.service.udid.UDIDBSService;
import com.naoh.iossupersign.utils.IosUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
@RequestMapping("udid")
public class UDIDController {

    private final ServletContext context;

    private final UDIDBSService udidbsService;

    private static final int BUFFER_SIZE = 4096;

    private final IpaPackageService ipaPackageService;

    private Map<String , IpaPackagePO> ipaPackageMap = new HashMap();

    private final DomainConfig domainConfig;

    public UDIDController(ServletContext context, UDIDBSService udidbsService, IpaPackageService ipaPackageService, DomainConfig domainConfig) {
        this.context = context;
        this.udidbsService = udidbsService;
        this.ipaPackageService = ipaPackageService;
        this.domainConfig = domainConfig;
    }


    @GetMapping("/download/view/{ipaId}")
    public String toMobileConfigView(@PathVariable String ipaId , Model model)  {
        IpaPackagePO ipaPackagePO = getIpa(ipaId);
        model.addAttribute("iconPath",domainConfig.getMobileConfigUrlPath()+ipaPackagePO.getIcon());
        model.addAttribute("appName",ipaPackagePO.getName());
        // FIXME: 2019/12/16 修改下載位置
        model.addAttribute("ipaUrl", "/mobileconfig/"+ipaPackagePO.getMobileFileName());
        model.addAttribute("embeddedUrl", domainConfig.getMobileConfigUrlPath()+"/mobileconfig/embedded.mobileprovision");
        return "appdownload/udidownload";
    }


    @GetMapping("/download/{ipaId}")
    public ResponseEntity<byte[]> downloadMobileConfig(@PathVariable String ipaId) throws IOException {
        String mobileConfig = udidbsService.getMobileConfig(getIpa(ipaId));
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(mobileConfig.getBytes().length);
        respHeaders.setContentType(MediaType.asMediaType(MimeType.valueOf("application/x-apple-aspen-config")));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "udid.mobileconfig");
        return new ResponseEntity<byte[]>(mobileConfig.getBytes(), respHeaders, HttpStatus.OK);
    }

    @PostMapping("/getUDID/{ipaId}")
    public void getUDID(@PathVariable String ipaId,HttpServletRequest request ,HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=UTF-8");
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
            if(isSuccess){
                System.out.println(IosUrlUtils.getRedirectIpaViewUrl(domainConfig.getMobileConfigUrlPath(),ipaId,udid));
//                response.sendRedirect(IosUrlUtils.getRedirectIpaViewUrl(udidDownloadUrl,ipaId,udid));
                response.setStatus(301);
                response.setHeader("Location",IosUrlUtils.getRedirectIpaViewUrl(domainConfig.getMobileConfigUrlPath(),ipaId,udid));
            }
         } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/app/ipa/{ipaId}")
    public String toIpaView(@PathVariable String ipaId,String udid , Model model){
        try {
            IpaPackagePO ipaPackagePO = getIpa(ipaId);
            model.addAttribute("iconPath",domainConfig.getMobileConfigUrlPath()+ipaPackagePO.getIcon());
            model.addAttribute("appName",ipaPackagePO.getName());
            model.addAttribute("ipaId",ipaId);
            model.addAttribute("udid",udid);
            model.addAttribute("plistPath",IosUrlUtils.getItemServiceUrl(domainConfig.getMobileConfigUrlPath(),ipaId,udid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "appdownload/ipadownload";
    }

    private IpaPackagePO getIpa(String ipaId){
        IpaPackagePO ipaPackagePO = null;
        if(ipaPackageMap.get(ipaId)==null){
            ipaPackagePO = ipaPackageService.selectByDownloadId(ipaId);
        }else{
            ipaPackagePO = ipaPackageMap.get(ipaId);
        }
        return ipaPackagePO;
    }
}