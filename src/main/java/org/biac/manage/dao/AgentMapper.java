package org.biac.manage.dao;

import org.biac.manage.entity.Agent;

import java.util.Map;

public interface AgentMapper {
    /**
     * 根据用户名查找用户，用户名不能重复
     * @param account
     * @return
     */
    Agent  selectByAccount(String account);

    /**
     * 用户登录，检查用户名，密码以及当前用户状态
     * @param map
     * @return
     */
    Agent  login(Map<Object,Object> map);
}