package org.biac.manage.service;


import org.biac.manage.entity.Agent;

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
}
