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
import java.util.ArrayList;
import java.util.List;

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
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String ids, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        int errorCode = 0;
        List<String> erroMsg = new ArrayList<String>();
        String [] list = ids.split(",");
        for (String id:list) {
            if(0!=storeInfoService.delete(id)){
                erroMsg.add(id+":经销点删除异常");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
    }

    /**
     * 经销点信息编辑
     * @param id
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(@RequestParam String id,HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String range = request.getParameter("range");
        String area_code = request.getParameter("areaCode");

        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setId(Long.parseLong(id));
        storeInfo.setName(name);
        storeInfo.setRanges(range);
        storeInfo.setAreaCode(area_code);

        if(0==storeInfoService.update(storeInfo)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 新增经销点信息
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/add.do")
    public  void add(HttpServletRequest request,HttpServletResponse response) throws  IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String range = request.getParameter("range");
        String area_code = request.getParameter("areaCode");

        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setName(name);
        storeInfo.setRanges(range);
        storeInfo.setAreaCode(area_code);

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
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String range = request.getParameter("range");
        String area_code = request.getParameter("areaCode");
        int length = storeInfoService.queryForSize(name,range,area_code);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据 ",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,storeInfoService.query(name,range,area_code,page)));
    }

    @RequestMapping(value = "/getall")
    public void getall(HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        List<StoreInfo> list = storeInfoService.getall();
        response.getWriter().write(JsonUtil.statusResponse(0,"",list));
    }
}
