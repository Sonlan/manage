package org.biac.manage.web;

import org.biac.manage.entity.Agent;
import org.biac.manage.service.AgentService;
import org.biac.manage.utils.JsonUtil;
import org.biac.manage.utils.MD5Util;
import org.biac.manage.utils.QRCode;
import org.biac.manage.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 经销商业务操作
 * Created by Song on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;
    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    public void login(@RequestParam String account, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        try{
            Agent agent = agentService.login(account,password);
            if(null != agent){
                if(0 == agent.getStatus()) response.getWriter().write(JsonUtil.statusResponse(0,"该用户账户已被冻结，请联系管理员",null));
                else {
                    request.getSession().setAttribute("_AGENT",agent);
                    request.getSession().setAttribute("_IDENTITY",0); //代表当前登录的是经销商
                    response.getWriter().write(JsonUtil.statusResponse(0,"登录成功",agent));
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
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=agentService.suspend(id)){
                erroMsg.add(id+":经销商用户挂起异常");
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
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=agentService.activate(id)){
                erroMsg.add(id+":经销商用户恢复异常");
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
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=agentService.delete(id)){
                erroMsg.add(id+":经销商用户删除异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 用户编辑
     * @param agent
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(Agent agent,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==agentService.edit(agent)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 根据经销点名称、经营范围以及地理区域选择经销商用户
     * @param page
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public void query(@RequestParam String page,HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String range = request.getParameter("range");
        String area_code = request.getParameter("area_code");
        int length = agentService.queryForSize(name,range,area_code);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,agentService.query(name,range,area_code,page)));
    }

    @RequestMapping(value = "register")
    public void register(@RequestParam String key,HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        key = MD5Util.encode(new Date().getTime()+"");
        long  timeLimit;
        try{
            timeLimit = 60000* Long.parseLong(SystemUtil.getProperty("timeLimit"));
        }catch (Exception e){
            timeLimit = 1200000;  //默认20分钟
        }
//        String s1 = MD5Util.encode(key);
        try{
            if(timeLimit <= new Date().getTime()-Long.parseLong(MD5Util.decode(key))){//超时
                response.getWriter().write(JsonUtil.statusResponse(1,"注册链接已失效",null));
                return;
            }else{//正常注册流程
                response.setContentType("image/jpg");
                byte [] image = QRCode.encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa76ca32f306d7277&redirect_uri=http://1u5186s163.51mypc.cn/sales/user/toIndex&response_type=code&scope=snsapi_userinfo&state=a13#wechat_redirect",250,250,"jpg");
                OutputStream out = response.getOutputStream();
                out.write(image);
                out.flush();
                out.close();
            }
        }catch (Exception e){
            response.getWriter().write(JsonUtil.statusResponse(1,"无效链接",null));
            return;
        }
    }
}
