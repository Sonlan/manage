package org.biac.manage.web;

import org.biac.manage.entity.ProductItem;
import org.biac.manage.service.ProductItemService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Song on 2016/7/15.
 */
@Controller
@RequestMapping(value = "/productlist")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    /**
     * 产品信息删除
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if(0==productItemService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }

    /**
     * 产品信息编辑编辑
     * @param productItem
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(ProductItem productItem, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==productItemService.update(productItem)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 新增产品信息
     * @param productItem
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/add.do")
    public  void add(ProductItem productItem,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==productItemService.add(productItem)){
            response.getWriter().write(JsonUtil.statusResponse(0,"新增成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"新增失败",null));
    }

    /**
     * 根据产品名称查询
     * @param name 产品名称
     * @param page 当前页面数
     * @param response
     * @throws IOException
     */
    public void query(@RequestParam String name,@RequestParam String page, HttpServletResponse response) throws IOException{

    }
}
