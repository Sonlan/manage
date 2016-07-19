package org.biac.manage.web;

import org.biac.manage.entity.Agent;
import org.biac.manage.entity.Salesman;
import org.biac.manage.service.SalesmanService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
     * 推销员用户挂起
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
     * 推销员用户恢复，与挂起对应
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

    /**
     * 推销员用户分页查询
     * @param page
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public  void query(@RequestParam String page, HttpServletRequest request,HttpServletResponse response) throws IOException{
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
}
