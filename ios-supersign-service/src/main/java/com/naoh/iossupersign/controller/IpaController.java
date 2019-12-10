package com.naoh.iossupersign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
@RequestMapping("ipa")
public class IpaController {

    @RequestMapping("/index")
    public String index(){
        return "supersignature/ipa/index";
    }
}
