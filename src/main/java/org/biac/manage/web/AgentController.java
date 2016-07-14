package org.biac.manage.web;

import org.biac.manage.entity.Agent;
import org.biac.manage.service.AgentService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 经销商业务操作
 * Created by Song on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;
    @RequestMapping(value = "/login")
    public void login(@RequestParam String account, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        try{
            Agent agent = agentService.login(account,password);
            if(null != agent){
                if(0 != agent.getStatus()) response.getWriter().write(JsonUtil.statusResponse(0,"该用户账户已被冻结，请联系管理员",null));
                else {
                    request.getSession().setAttribute("_AGENT",agent);
                    response.getWriter().write(JsonUtil.statusResponse(0,"登录成功",agent));
                }
            }else response.getWriter().write(JsonUtil.statusResponse(0,"不存在该用户",null));
        }catch (Exception e){
            response.getWriter().write(JsonUtil.statusResponse(1,"后台异常",null));
        }
    }
}
