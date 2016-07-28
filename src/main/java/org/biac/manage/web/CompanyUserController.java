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
import java.util.ArrayList;
import java.util.List;

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
    public void login(@RequestParam String account, @RequestParam String password, HttpServletResponse response, HttpServletRequest  request) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        try{
            CompanyUser companyUser = companyUserService.login(account,password);
            if(null != companyUser){
                if(0 != companyUser.getStatus()) response.getWriter().write(JsonUtil.statusResponse(0,"该用户账户已被冻结，请联系管理员",null));
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
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/suspend")
    public void suspend(@RequestParam String ids,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=companyUserService.suspend(id)){
                erroMsg.add(id+":挂起异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 经销商用户恢复，与挂起对应
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/activate")
    public void activate(@RequestParam String ids,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=companyUserService.activate(id)){
                erroMsg.add(id+":恢复异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 删除用户
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String ids,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=companyUserService.delete(id)){
                erroMsg.add(id+" :删除异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 用户编辑
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(@RequestParam String id,HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String password = request.getParameter("password");
        String authority = request.getParameter("authority");
        String status = request.getParameter("status");
        CompanyUser companyUser = new CompanyUser();
        companyUser.setPassword(password);
        if(null != authority)
            companyUser.setAuthority(Integer.parseInt(authority));
        if(null != status)
            companyUser.setStatus(Integer.parseInt(status));
        if(0==companyUserService.edit(companyUser)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 根据用户权限以及账户名查找用户
     * @param page
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public void query(@RequestParam String page,HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String account = request.getParameter("account");
        String authority = request.getParameter("authority");
        String status =request.getParameter("status");
        int length = companyUserService.queryForSize(account,authority,status);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,companyUserService.query(account,authority,page,status)));
    }
}
