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
}
