package org.biacbiac.manage.service;


import org.biacbiac.manage.entity.Agent;

import java.util.List;

/**
 * Created by Song on 2016/7/14.
 */
public interface AgentService {
    /**
     * 经销商登录，成功返回经销商个人信息，失败返回null
     * @param account
     * @param password
     * @return
     */
    Agent login(String account,String password);

    /**
     * 经销商用户挂起
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 经销商用户恢复
     * @param id
     * @return
     */
    int activate(String id);

    /**
     * 删除操作
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 用户信息编辑
     * @param agent
     * @return
     */
    int edit(Agent agent);

    /**
     * 经销商用户选择
     * @param name
     * @param range
     * @param area_code
     * @param page
     * @return
     */
    List<Agent> query(String name,String range,String area_code,String page);

    /**
     * 查询符合条件的所有消息记录
     * @param name
     * @param range
     * @param area_code
     * @return
     */
    int queryForSize(String name,String range,String area_code);

    /**
     * 经销商用户注册
     * @param account
     * @param password
     * @param storeId
     * @param tel
     * @param mail
     * @return
     */
    int regist(String account,String password,String storeId,String tel,String mail);

    /**
     * 经销商用户注册邮件激活
     * @param key
     * @param mail
     * @return
     */
    int regact(String key,String mail);
}
