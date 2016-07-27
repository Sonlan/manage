package org.biac.manage.web;

import net.sf.json.JSONObject;
import org.biac.manage.entity.Agent;
import org.biac.manage.entity.Salesman;
import org.biac.manage.entity.User;
import org.biac.manage.entity.UserIdentity;
import org.biac.manage.service.SalesmanService;
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
import java.util.List;

/**
 * 推销员业务管理
 * Created by Song on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/salesman")
public class SalesmanController {
    @Autowired
    private SalesmanService salesmanService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserIdentityService userIdentityService;

    /**
     * 推销员用户挂起
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
            if(0!=salesmanService.suspend(id)){
                erroMsg.add(id+":推销员用户挂起异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 推销员用户恢复，与挂起对应
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
            if(0!=salesmanService.activate(id)){
                erroMsg.add(id+":推销员用户恢复异常");
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
            if(0!=salesmanService.delete(id)){
                erroMsg.add(id+":删除异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 用户编辑
     * @param id
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(@RequestParam String id, HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String store_id = request.getParameter("store_id");
        String work_id = request.getParameter("work_id");
        String name = request.getParameter("name");
        String status = request.getParameter("status");

        Salesman salesman = new Salesman();
        salesman.setId(Long.parseLong(id));
        salesman.setStoreId(Integer.parseInt(store_id));
        salesman.setWorkId(work_id);
        salesman.setName(name);
        salesman.setStatus(Integer.parseInt(status));

        if(0==salesmanService.edit(salesman)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 推销员用户分页查询
     * @param page
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public  void query(@RequestParam String page, HttpServletRequest request,HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        Agent agent = (Agent) request.getSession().getAttribute("_AGENT");
        if(null != agent){
            long store_id = agent.getStoreId();
            String name = request.getParameter("name");  //姓名
            String work_id = request.getParameter("work_id");  //工号
            String status = request.getParameter("status");  //用户状态
            int length = salesmanService.queryForSize(store_id,name,work_id,status);
            if(0==length){
                response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
            }else response.getWriter().write(JsonUtil.statusResponse(0,length,salesmanService.query(store_id,name,work_id,status,page)));
        }else response.getWriter().write(JsonUtil.statusResponse(1,"无符合查询条件的数据",null));
    }
    /**
     * 生成微信扫码绑定二维码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/qrCode")
    public void qrCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType("image/jpg");
        Agent agent = null;
        if(0 == request.getSession().getAttribute("_IDENTITY")){
            agent   = (Agent) request.getSession().getAttribute("_AGENT");
        }
        if(null != agent) {
            System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa76ca32f306d7277&redirect_uri=" + SystemUtil.getProperty("domain") + "/agent/bind?key=" + MD5Util.encode(MD5Util.MD5(agent.getAccount())));
            byte[] image = QRCode.encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa76ca32f306d7277&redirect_uri=" + URLEncoder.encode(SystemUtil.getProperty("domain") + "/agent/bind?key=" + MD5Util.encode(MD5Util.MD5(agent.getAccount())), "utf-8") + "&response_type=code&scope=snsapi_userinfo&state=a13#wechat_redirect", 250, 250, "jpg");
            OutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
            out.close();
        }
    }
    /**
     * 推销员注册
     * @param key  当前agent用户account编码值
     * @param code 微信用户信息
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/register")
    public void register(@RequestParam String key,@RequestParam String code,HttpServletRequest request,HttpServletResponse response) throws IOException{
        Agent agent   = (Agent) request.getSession().getAttribute("_AGENT");
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
            identity.setIdentity(1);
            userIdentityService.insert(identity);
            userService.userInsert(user);

            switch (salesmanService.register(key,userObj.getString("nickname"),userObj.getString("openid"),agent)){
                case 0:{
                    response.getWriter().write(JsonUtil.statusResponse(0,"推销员注册成功",null));
                    break;
                }
                case 1:{
                    response.getWriter().write(JsonUtil.statusResponse(1,"无效注册接口",null));
                    break;
                }
                case 2:{
                    response.getWriter().write(JsonUtil.statusResponse(2,"后台异常，注册失败",null));
                    break;
                }
            }
            return;
        }catch (Exception e){
            response.getWriter().write(JsonUtil.statusResponse(3,"后台异常",null));
        }

    }
}
