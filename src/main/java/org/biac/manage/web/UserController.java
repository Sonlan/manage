package org.biac.manage.web;

import org.biac.manage.entity.User;
import org.biac.manage.service.UserService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Song on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户挂起
     * @throws IOException
     */
    public void suspend(@RequestParam String id, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==userService.suspend(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"挂起成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"挂起异常",null));
    }
    /**
     * 消费者用户恢复，与挂起对应
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/activate")
    public void activate(@RequestParam String id,HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==userService.activate(id)){
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
        if(0==userService.delete(id)){
            response.getWriter().write(JsonUtil.statusResponse(0,"删除成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"删除异常",null));
    }
    /**
     * 用户编辑
     * @param user
     * @param response
     */
    @RequestMapping(value = "/edit.do")
    public void edit(User user, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        if(0==userService.updateUser(user)){
            response.getWriter().write(JsonUtil.statusResponse(0,"修改成功",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,"修改失败",null));
    }

    @RequestMapping(value = "/query")
    public  void query(@RequestParam String page, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String nickname = request.getParameter("nickname");  //微信昵称
        int length = userService.queryForSize(nickname);
        if(0==length){
            response.getWriter().write(JsonUtil.statusResponse(0,"无符合查询条件的数据",null));
        }else response.getWriter().write(JsonUtil.statusResponse(0,length,userService.query(nickname,page)));
    }
}
