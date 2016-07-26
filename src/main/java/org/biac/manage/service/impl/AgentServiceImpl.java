package org.biac.manage.service.impl;

import org.biac.manage.dao.AgentMapper;
import org.biac.manage.entity.Agent;
import org.biac.manage.service.AgentService;
import org.biac.manage.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
     * 经销商用户选择
     *
     * @param name
     * @param range
     * @param area_code
     * @param page
     * @return
     */
    public List<Agent> query(String name, String range, String area_code, String page) {
        try{
            int pageNum = Integer.parseInt(page);
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("name",name);
            map.put("range",range);
            map.put("area_code",area_code);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return agentDao.querySelective(map);

        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"经销商用户信息获取异常");
            return  null;
        }
    }

    /**
     * 查询符合条件的所有消息记录
     *
     * @param name
     * @param range
     * @param area_code
     * @return
     */
    public int queryForSize(String name, String range, String area_code) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("name",name);
            map.put("range",range);
            map.put("area_code",area_code);
            return agentDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"经销商用户信息数目获取异常");
            return  0;
        }
    }

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

    /**
     * 经销商用户注册
     *
     * @param account
     * @param password
     * @param storeId
     * @param tel
     * @param mail
     * @return
     */
    public int regist(String account, String password, String storeId, String tel, String mail) {
        try{
            Agent agent = new Agent();
            agent.setAccount(account);
            agent.setPassword(password);
            agent.setStoreId(Integer.parseInt(storeId));
            agent.setTel(tel);
            agent.setMail(mail);
            agentDao.insertSelective(agent);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 经销商用户注册邮件激活
     *
     * @param key  对用户名进行加密形成的校验码
     * @param mail 用户邮箱
     * @return
     */
    public int regact(String key, String mail) {
        try{
            Agent agent = agentDao.selectByMail(mail);
            if(null == agent) return 1;
            else if(0!=agent.getStatus()) return 2;
            else if(!key.equals(MD5Util.encode(MD5Util.MD5(agent.getAccount())))) return 1;
            else{
                agentDao.activate(agent.getId()+"");
            }
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return 3;
        }
    }
}
