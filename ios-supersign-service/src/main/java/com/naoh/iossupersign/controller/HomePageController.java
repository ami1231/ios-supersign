package com.naoh.iossupersign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Controller
public class HomePageController {
    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/changePwd")
    public String changePwd() {
        return "changePwd";
    }

}
