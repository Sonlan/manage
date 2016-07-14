package org.biac.manage.web;

import org.biac.manage.entity.CompanyUser;
import org.biac.manage.service.CompanyUserService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 企业用户业务逻辑处理
 * Created by Song on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/companyuser")
public class CompanyUserController {
    @Autowired
    private CompanyUserService companyUserService;
    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    public void login(@RequestParam String account, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        try{
            CompanyUser companyUser = companyUserService.login(account,password);
            if(null != companyUser){
                if(0 == companyUser.getStatus()) response.getWriter().write(JsonUtil.statusResponse(0,"该用户账户已被冻结，请联系管理员",null));
                else {
                    request.getSession().setAttribute("_COMPANYUSER",companyUser);
                    request.getSession().setAttribute("_IDENTITY",1); //代表当前登录的是经销商
                    response.getWriter().write(JsonUtil.statusResponse(0,"登录成功",companyUser));
                }
            }else response.getWriter().write(JsonUtil.statusResponse(0,"不存在该用户",null));
        }catch (Exception e){
            response.getWriter().write(JsonUtil.statusResponse(1,"后台异常",null));
        }
    }

    /**
     * 经销商用户挂起
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/suspend")
    public void suspend(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==companyUserService.suspend(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"挂起成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"挂起异常",null));
    }

    /**
     * 经销商用户恢复，与挂起对应
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/activate")
    public void activate(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==companyUserService.activate(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"恢复成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"恢复异常",null));
    }

    /**
     * 删除用户
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==companyUserService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }

    /**
     * 用户编辑
     * @param companyUser
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(CompanyUser companyUser,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==companyUserService.edit(companyUser)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }
}
