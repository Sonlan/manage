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
     * 用户信息编辑
     *
     * @param agent
     * @return
     */
    public int edit(Agent agent) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("record",agent);
            agentDao.updateSelective(map);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"修改异常");
            return 1;
        }
    }

    /**
     * 经销商用户恢复
     *
     * @param id
     * @return
     */
    public int activate(String id) {
        try{
            agentDao.activate(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"恢复异常");
            return 1;
        }
    }

    /**
     * 删除操作
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            agentDao.delete(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"删除异常");
            return 1;
        }
    }

    /**
     * 经销商用户挂起
     *
     * @param id
     * @return
     */
    public int suspend(String id) {
        try{
            agentDao.suspend(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"挂起异常");
            return 1;
        }
    }

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
            logger.error(this.getClass().toString()+"经销商登录异常");
            return  null;
        }
    }
}