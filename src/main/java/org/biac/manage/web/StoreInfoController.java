package org.biac.manage.web;

import org.biac.manage.entity.StoreInfo;
import org.biac.manage.service.StoreInfoService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 经销点信息业务管理
 * Created by Song on 2016/7/15.
 */
@Controller
@RequestMapping(value = "/store")
public class StoreInfoController {
    @Autowired
    private StoreInfoService storeInfoService;

    /**
     * 经销点信息删除
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if(0==storeInfoService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }

    /**
     * 经销点信息编辑
     * @param storeInfo
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(StoreInfo storeInfo, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==storeInfoService.update(storeInfo)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 新增经销点信息
     * @param storeInfo
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/add.do")
    public  void add(StoreInfo storeInfo,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==storeInfoService.add(storeInfo)){
            response.getWriter().write(JsonUtil.statusResponse(0,"新增成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"新增失败",null));
    }

    /**
     * 根据经销点名称查询
     * @param page 当前页面数
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public void query(@RequestParam String page, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String range = request.getParameter("range");
        String area_code = request.getParameter("area_code");
        int length = storeInfoService.queryForSize(name,range,area_code);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,storeInfoService.query(name,range,area_code,page)));
    }
}
