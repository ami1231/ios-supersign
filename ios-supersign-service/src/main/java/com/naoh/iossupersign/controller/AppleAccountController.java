package com.naoh.iossupersign.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
@RequestMapping("appleAccount")
public class AppleAccountController {

    private static final Integer DEFAULT_PAGE = 1;

    private final AppleAccountBSService appleAccountBSService;

    public AppleAccountController(AppleAccountBSService appleAccountBSService) {
        this.appleAccountBSService = appleAccountBSService;
    }

    @RequestMapping("/index")
    public String index(Model model){
        return search(DEFAULT_PAGE,null, model);
    }

    @PostMapping("addAccount")
    public String addAppleAccount(AppleAccountPO appleAccountPO){

        //確認蘋果帳號是否存在
        if(appleAccountBSService.getAccountByAccount(appleAccountPO)==null){
            appleAccountBSService.addAppleAccount(appleAccountPO);
        }
        return null;
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜尋")
    public String search(@RequestParam(value = "page") Integer currentPage,
                         AppleAccountPO appleAccountPO ,
                                Model model) {
        Page<AppleAccountPO>  appleAccountPOS = appleAccountBSService.selectAppleAccountByCondition(currentPage,appleAccountPO);
        model.addAttribute("page", appleAccountPOS);
        model.addAttribute("accounts", appleAccountPOS.getRecords());
        return "supersignature/appleaccount/index";
    }

}