package org.biac.manage.web;

import org.biac.manage.entity.Salesman;
import org.biac.manage.service.SalesmanService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 推销员业务管理
 * Created by Song on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/salesman")
public class SalesmanController {
    @Autowired
    private SalesmanService salesmanService;

    /**
     * 经销商用户挂起
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/suspend")
    public void suspend(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==salesmanService.suspend(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"挂起成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"挂起异常",null));
    }

    /**
     * 经销商用户恢复，与挂起对应
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/activate")
    public void activate(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==salesmanService.activate(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"恢复成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"恢复异常",null));
    }

    /**
     * 删除用户
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==salesmanService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }

    /**
     * 用户编辑
     * @param salesman
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(Salesman salesman, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==salesmanService.edit(salesman)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }
}
