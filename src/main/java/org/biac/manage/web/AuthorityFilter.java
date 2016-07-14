package org.biac.manage.web;

import org.biac.manage.entity.Agent;
import org.biac.manage.entity.CompanyUser;
import org.biac.manage.utils.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截所有*。do请求,即相关信息删除和编辑操作
 * Created by Song on 2016/7/14.
 */
public class AuthorityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setContentType("application/json;charset=utf-8");
        try{
            int identity = (Integer)request.getSession().getAttribute("_IDENTITY");
            switch (identity) {
                case 0: {//经销商
                    resp.getWriter().write(JsonUtil.statusResponse(0,"您无此操作权限",null));
                    //  Agent agent = (Agent) request.getSession().getAttribute("_AGENT");
                    //  chain.doFilter(req, resp);
                    break;
                }
                case 1:{//企业用户
                    CompanyUser companyUser = (CompanyUser) request.getSession().getAttribute("_COMPANYUSER");
                    if(0!=companyUser.getAuthority()){
                        resp.getWriter().write(JsonUtil.statusResponse(0,"您无此操作权限",null));
                    }else chain.doFilter(req,resp);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            resp.getWriter().write(JsonUtil.statusResponse(0,"您无此操作权限",null));
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
