package org.biac.manage.web;

import net.sf.json.JSONObject;
import org.biac.manage.entity.Agent;
import org.biac.manage.entity.User;
import org.biac.manage.entity.UserIdentity;
import org.biac.manage.service.AgentService;
import org.biac.manage.service.UserIdentityService;
import org.biac.manage.service.UserService;
import org.biac.manage.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
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
    @Autowired
    private UserService userService;
    @Autowired
    private UserIdentityService userIdentityService;
    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    public void login(@RequestParam String account, @RequestParam String password, HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        try{
            Agent agent = agentService.login(account,password);
            if(null != agent){
                if(0 != agent.getStatus()) response.getWriter().write(JsonUtil.statusResponse(0,"该用户账户已被冻结，请联系管理员",null));
                else {
                    request.getSession().setAttribute("_AGENT",agent);
                    request.getSession().setAttribute("_IDENTITY",0); //代表当前登录的是经销商
                    response.getWriter().write(JsonUtil.statusResponse(0,"登录成功",agent));
                }
            }else response.getWriter().write(JsonUtil.statusResponse(1,"不存在该用户",null));
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
        response.setHeader("Access-Control-Allow-Origin", "*");
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
        response.setHeader("Access-Control-Allow-Origin", "*");
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
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(@RequestParam String id, HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String storeId = request.getParameter("storeid");
        String password = request.getParameter("password");
        String tel = request.getParameter("tel");
        String mail = request.getParameter("mail");

        Agent agent = new Agent();
        agent.setId(Long.parseLong(id));
        agent.setStoreId(Integer.parseInt(storeId));
        agent.setPassword(password);
        agent.setTel(tel);
        agent.setMail(mail);
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String range = request.getParameter("range");
        String area_code = request.getParameter("area_code");
        String status = request.getParameter("status");
        int length = agentService.queryForSize(name,range,area_code,status);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,agentService.query(name,range,area_code,page,status)));
    }

    /**
     * 用户注册
     * @param key
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "register")
    public void register(@RequestParam String key,HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
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
            }else {//正常注册流程
                String account = request.getParameter("account");
                String password = request.getParameter("password");
                String storeId = request.getParameter("storeid");
                String tel = request.getParameter("tel");
                String mail = request.getParameter("mail");
                switch (agentService.regist(account, password, storeId, tel, mail)) {
                    case 0: {
                        MailUtil.sendEmail(mail, "BIAC经销系统账户激活", "亲爱的" + mail + "：\n" +
                                "恭喜您注册成功~！\n" +
                                "您的迅雷帐号为： " + account + "\n" +
                                "为了您的帐号安全，请点击如下链接完成安全邮箱激活，或把下面网页地址复制到浏览器的地址栏中打开:\n" +
                                "<a href='" + SystemUtil.getProperty("domain") + "/agent/regact?key=" + MD5Util.encode(MD5Util.MD5(account)) + "&mail=" + mail + "'>" + SystemUtil.getProperty("domain") + "/agent/regact?key=" + MD5Util.encode(MD5Util.MD5(account)) + "&mail=" + mail + "</a>");
                        response.getWriter().write(JsonUtil.statusResponse(0, "激活邮件已发送至您的邮箱，请查收", null));
                        break;
                    }
                    case 1: {
                        response.getWriter().write(JsonUtil.statusResponse(1, "用户已存在", null));
                        break;
                    }
                    case 2: {
                        response.getWriter().write(JsonUtil.statusResponse(2, "输入参数异常", null));
                        break;
                    }
                }
                return;
            }
        }catch (Exception e){
            response.getWriter().write(JsonUtil.statusResponse(1,"无效链接",null));
            return;
        }
    }

    /**
     * 邮箱激活
     * @param key
     * @param mail
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "regact")
    public String regact(@RequestParam String key,@RequestParam String mail,HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        switch (agentService.regact(key,mail)){
            case 0:{ //激活成功
                request.setAttribute("key",key);
                request.setAttribute("mail",mail);
                return "agent/qrCode";
            }
            case 1:{ //用户不存在或无效
                response.getWriter().write(JsonUtil.statusResponse(1,"无效链接",null));
                return "agent/invalid";
            }
            case 2:{
                response.getWriter().write(JsonUtil.statusResponse(1,"账户已激活",null));
                return "agent/repeat";
            }
            default:{
                response.getWriter().write(JsonUtil.statusResponse(1,"后台异常，请稍后重试",null));
                return "agent/error";
            }
        }
    }

    /**
     * 微信账户绑定
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/bind")
    public void bind(@RequestParam String code,@RequestParam String key,@RequestParam String mail,HttpServletResponse response) throws Exception {
        String APPID = SystemUtil.getProperty("APPID");
        String APPSECRET = SystemUtil.getProperty("APPSECRET");
        //获取accessToken
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        String ACCESS_TOKEN = "";
        String OPENID = "";
        response.setContentType("application/json;charset=utf-8");
        try {
            //获取有效时长为5分钟的access_token
            JSONObject demoJson = UrlGETUtil.GET(url);
            //刷新Access_token有效期
            JSONObject demojson2 = UrlGETUtil.GET("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APPID + "&grant_type=refresh_token" + "&refresh_token=" + demoJson.getString("refresh_token"));

            //获取用户信息
            JSONObject userObj = UrlGETUtil.GET("https://api.weixin.qq.com/sns/userinfo?access_token=" + demojson2.getString("access_token") + "&openid=" + demojson2.getString("openid") + "&lang=zh_CN");
            User user = new User(userObj.getString("openid"), userObj.getString("nickname"), userObj.getInt("sex"), userObj.getString("headimgurl"));

            UserIdentity identity =  new UserIdentity(userObj.getString("openid"));
            identity.setIdentity(2);
            userIdentityService.insert(identity);
            userService.userInsert(user);
            agentService.bind(key,mail,user);
            response.getWriter().write(JsonUtil.statusResponse(0,"账户绑定成功",null));
            return;
        }catch (Exception e){
            response.getWriter().write(JsonUtil.statusResponse(1,"后台异常",null));
        }
    }

    /**
     * 生成微信扫码绑定二维码
     * @param key
     * @param mail
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/qrCode")
    public void qrCode(@RequestParam String key,@RequestParam String mail,HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType("image/jpg");
        System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa76ca32f306d7277&redirect_uri="+SystemUtil.getProperty("domain")+"/agent/bind?key="+key+"&mail="+mail);
        byte [] image = QRCode.encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa76ca32f306d7277&redirect_uri="+ URLEncoder.encode(SystemUtil.getProperty("domain")+"/agent/bind?mail="+mail+"&key="+key, "utf-8")+"&response_type=code&scope=snsapi_userinfo&state=a13#wechat_redirect",250,250,"jpg");
        OutputStream out = response.getOutputStream();
        out.write(image);
        out.flush();
        out.close();
    }


}
