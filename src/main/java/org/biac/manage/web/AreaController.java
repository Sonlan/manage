package org.biac.manage.web;

import org.biac.manage.entity.Area;
import org.biac.manage.service.AreaService;
import org.biac.manage.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Song on 2016/8/2.
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getall")
    public void area (HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        List<Area> list = areaService.queryByCode(null);
        response.getWriter().write(JsonUtil.statusResponse(0,"",list));
    }
}
