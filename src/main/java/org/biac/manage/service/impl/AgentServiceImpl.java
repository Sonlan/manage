package org.biac.manage.service.impl;

import org.biac.manage.dao.AgentMapper;
import org.biac.manage.entity.Agent;
import org.biac.manage.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Song on 2016/7/14.
 */
@Service
public class AgentServiceImpl implements AgentService{
    @Autowired
    private AgentMapper agentDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 经销商登录，成功返回经销商个人信息，失败返回null
     *
     * @param account
     * @param password
     * @return
     */
    public Agent login(String account, String password) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("account",account);
            map.put("password",password);
            return agentDao.login(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"经销商 登录异常");
            return  null;
        }
    }
}
