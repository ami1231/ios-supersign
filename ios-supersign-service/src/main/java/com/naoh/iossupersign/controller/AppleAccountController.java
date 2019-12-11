package com.naoh.iossupersign.controller;

import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
@RequestMapping("appleAccount")
public class AppleAccountController {

    @Autowired
    private AppleAccountBSService appleAccountBSService;

    @RequestMapping("/index")
    public String index(){

        appleAccountBSService.getAllAccount();

        return "supersignature/appleaccount/index";
    }

    @PostMapping("addAccount")
    public String addAppleAccount(AppleAccountPO appleAccountPO){

        //確認蘋果帳號是否存在
        if(appleAccountBSService.getAccountByAccount(appleAccountPO)==null){
            appleAccountBSService.addAppleAccount(appleAccountPO);
        }
        return null;
    }

    @GetMapping("queryByAccount")
    public String queryAccount(AppleAccountPO appleAccountPO){
        appleAccountBSService.getAccountByAccount(appleAccountPO);
        return null;
    }

}