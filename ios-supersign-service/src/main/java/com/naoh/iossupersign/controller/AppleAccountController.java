package com.naoh.iossupersign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.base.ApiResult;
import com.naoh.iossupersign.enums.ServiceError;
import com.naoh.iossupersign.exception.ServiceException;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
@RequestMapping("appleAccount")
@Slf4j
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
    @ResponseBody
    public ApiResult<String> addAppleAccount(AppleAccountPO appleAccountPO){
        ApiResult<String> result = new ApiResult<>();
        //確認蘋果帳號是否存在
        try{
            if(appleAccountBSService.getAccountByAccount(appleAccountPO)==null){
                appleAccountBSService.addAppleAccount(appleAccountPO);
                result.setCode(ApiResult.SUCCESS_CODE);
                result.setMsg(ApiResult.SUCCESS_MSG);
                log.info("add Apple Account Success");
            }else{
                result.setMsg("账号已存在");
            }
        }catch (ServiceException se){
            result.setMsg(se.getErrorMsg());
        }catch (Exception e){
            log.error("",e);
            result.setMsg(ServiceError.SERVER_ERROR.msg);
        }
        return result;
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