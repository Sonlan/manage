package org.biacbiac.manage.web;

import org.biacbiac.manage.entity.ActivityInfo;
import org.biacbiac.manage.service.ActivityInfoService;
import org.biacbiac.manage.utils.JsonUtil;
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
 * 活动信息业务管理
 * Created by Song on 2016/7/15.
 */
@Controller
@RequestMapping(value = "/activityinfo")
public class ActivityInfoController {
    @Autowired
    private ActivityInfoService activityInfoService;
    /**
     * 活动信息删除
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String ids, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=activityInfoService.delete(id)){
                erroMsg.add(id+":活动信息删除异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 活动信息编辑
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
     * 新增活动信息
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
     * 根据活动名称以及活动id查询
     * @param page 当前页面数
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public void query(@RequestParam String page, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String activity_id = request.getParameter("activity_id");
        String theme = request.getParameter("theme");
        int length = activityInfoService.queryForSize(activity_id,theme);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合条件的活动记录",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,activityInfoService.query(activity_id,theme,page)));
    }
}
