package org.biac.manage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台页面内部跳转
 * Created by Song on 2016/8/3.
 */
@Controller
@RequestMapping(value = "/html")
public class HtmlController {
    @RequestMapping(value = "/entAgencyManagement")
    public String entAgencyManagement(){
        return "views/entAgencyManagement";
    }
    @RequestMapping(value = "/entEntManagement")
    public String entEntManagement(){
        return "views/entEntManagement";
    }
    @RequestMapping(value = "/entProductInfo")
    public String entProductInfo(){
        return "views/entProductInfo";
    }
    @RequestMapping(value = "/entSalerManagement")
    public String entSalerManagement(){
        return "views/entSalerManagement";
    }
    @RequestMapping(value = "/entUserManagement")
    public String entUserManagement(){
        return "views/entUserManagement";
    }
}
