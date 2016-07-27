package org.biac.manage.web;

import org.biac.manage.entity.ProductItem;
import org.biac.manage.service.ProductItemService;
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
        response.setHeader("Access-Control-Allow-Origin", "*");
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
     * @param id
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(@RequestParam String id,HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String producer = request.getParameter("producer");
        String origin = request.getParameter("origin");
        String price = request.getParameter("price");
        String exp_date = request.getParameter("exp_date");
        String url = request.getParameter("url");
        String box_cnt = request.getParameter("box_cnt");

        ProductItem productItem = new ProductItem();
        productItem.setId(Long.parseLong(id));
        productItem.setName(name);
        productItem.setDescription(description);
        productItem.setProducer(producer);
        productItem.setOrigin(origin);
        productItem.setPrice(Float.parseFloat(price));
        productItem.setExpDate(Integer.parseInt(exp_date));
        productItem.setUrl(url);
        productItem.setBoxCnt(Integer.parseInt(box_cnt));

        if(0==productItemService.update(productItem)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    /**
     * 新增产品信息
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/add.do")
    public  void add(HttpServletRequest request,HttpServletResponse response) throws  IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String producer = request.getParameter("producer");
        String origin = request.getParameter("origin");
        String price = request.getParameter("price");
        String exp_date = request.getParameter("exp_date");
        String url = request.getParameter("url");
        String box_cnt = request.getParameter("box_cnt");

        ProductItem productItem = new ProductItem();
        productItem.setName(name);
        productItem.setDescription(description);
        productItem.setProducer(producer);
        productItem.setOrigin(origin);
        productItem.setPrice(Float.parseFloat(price));
        productItem.setExpDate(Integer.parseInt(exp_date));
        productItem.setUrl(url);
        productItem.setBoxCnt(Integer.parseInt(box_cnt));
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        String name = request.getParameter("name");
        int length = productItemService.queryForSize(name);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"没有满足条件的产品数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,productItemService.query(name,page)));
    }
}
