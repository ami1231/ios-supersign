package com.naoh.iossupersign.controller;

import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        return "supersignature/appleaccount/index";
    }

    @PostMapping("addAccount")
    public String addAppleAccount(AppleAccountPO appleAccountPO){

        //確認蘋果帳號是否存在
        if(appleAccountBSService.getAccountByAccount(appleAccountPO.getAccount())==null){

            appleAccountBSService.addAppleAccount(appleAccountPO);



        }else{

        }

        return null;

    }


}
