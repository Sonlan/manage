package org.biac.manage.web;

import org.biac.manage.service.SaleRecordService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 消费信息业务管理
 * Created by Song on 2016/7/15.
 */
@Controller
@RequestMapping(value = "/salerecord")
public class SaleRecordController {
    @Autowired
    private SaleRecordService saleRecordService;

    /**
     * 经销点信息删除
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delete.do")
    public void delete(@RequestParam String id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        if(0==saleRecordService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }

    /**
     * 分页查询
     * @param page
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/query")
    public  void query(@RequestParam String page, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String nickname = request.getParameter("nickname");  //微信昵称
        String code = request.getParameter("status");  //消费码
        int length = saleRecordService.queryForSize(nickname,code);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,saleRecordService.query(nickname,code,page)));
    }
}
