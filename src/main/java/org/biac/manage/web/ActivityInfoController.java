package org.biac.manage.web;

import org.biac.manage.entity.ActivityInfo;
import org.biac.manage.service.ActivityInfoService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 活动信息业务管理
 * Created by Song on 2016/7/15.
 */
@Controller
public class ActivityInfoController {
    @Autowired
    private ActivityInfoService activityInfoService;
    /**
     * 经销点信息删除
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if(0==activityInfoService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }

    /**
     * 经销点信息编辑
     * @param activityInfo
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(ActivityInfo activityInfo, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==activityInfoService.update(activityInfo)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 新增经销点信息
     * @param activityInfo
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/add.do")
    public  void add(ActivityInfo activityInfo,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==activityInfoService.add(activityInfo)){
            response.getWriter().write(JsonUtil.statusResponse(0,"新增成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"新增失败",null));
    }

    /**
     * 根据经销点名称查询
     * @param name 产品名称
     * @param page 当前页面数
     * @param response
     * @throws IOException
     */
    public void query(@RequestParam String name,@RequestParam String page, HttpServletResponse response) throws IOException{

    }
}
