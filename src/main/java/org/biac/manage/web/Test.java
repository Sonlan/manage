package org.biac.manage.web;

import org.biac.manage.service.StoreInfoService;
import org.biac.manage.utils.JsonUtil;
import org.biac.manage.utils.MD5Util;
import org.biac.manage.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Song on 2016/7/19.
 */
@Controller
@RequestMapping(value = "/test")
public class Test {
    @Autowired
    private StoreInfoService storeInfoService;
    @RequestMapping(value = "/mail")
    public void mail(){
        MailUtil.sendEmail("1147649695@qq.com","激活测试邮件","<a href='www.baidu.com'>点击激活</a>");
    }

    @RequestMapping(value = "/tojson")
    public void tojson (HttpServletResponse response) throws IOException{
        String [] list = {"asd","0123","asdqwe"};
        response.getWriter().write(JsonUtil.statusResponse(0,"test",list));
    }

    @RequestMapping(value = "/toobj")
    public void toobj (@RequestParam String data) throws IOException{
        List<String> list = JsonUtil.toObject(data,List.class);
        for (String str:list) {
            System.out.println(str);
        }
    }

    @RequestMapping(value = "/toobj2")
    public void toobj2 (@RequestParam String data) throws IOException{
        String [] list = data.split(",");
        for (String str:list) {
            System.out.println(str);
        }
    }
    @RequestMapping(value = "/transac")
    public void transac () throws IOException{
        try{
            if(0!= test())  System.out.println("ERROR");
            else System.out.println("GET");
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    private int test(){
        storeInfoService.test();
        return  0;
    }

    @RequestMapping(value = "/encode")
    public void encode (@RequestParam String key) throws IOException{
        MD5Util.encode(key);
    }
}
