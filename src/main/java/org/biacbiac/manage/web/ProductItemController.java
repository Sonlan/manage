package org.biacbiac.manage.web;

import org.biacbiac.manage.entity.ProductItem;
import org.biacbiac.manage.service.ProductItemService;
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
 * Created by Song on 2016/7/15.
 */
@Controller
@RequestMapping(value = "/productlist")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    /**
     * 产品信息删除
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
            if(0!=productItemService.delete(id)){
                erroMsg.add(id+":删除异常 ");
                errorCode = 1;
            }
        }
        response.getWriter().write(JsonUtil.statusResponse(errorCode,erroMsg.toString(),null));
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
     * @param page 当前页面数
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public void query(@RequestParam String page, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String name = request.getParameter("name");
        int length = productItemService.queryForSize(name);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"没有满足条件的产品数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,productItemService.query(name,page)));
    }
}
