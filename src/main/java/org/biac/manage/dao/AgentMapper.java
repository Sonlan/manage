package org.biac.manage.dao;

import org.biac.manage.entity.Agent;

import java.util.List;
import java.util.Map;

public interface AgentMapper {
    /**
     * 根据用户名查找用户，用户名不能重复
     * @param account
     * @return
     */
    Agent  selectByAccount(String account);

    Agent  selectByMail(String mail);

    /**
     * 用户登录，检查用户名，密码以及当前用户状态
     * @param map
     * @return
     */
    Agent  login(Map<Object,Object> map);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    Agent selectById(String id);

    /**
     * 将用户status置为1，挂起
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 将用户status置为0，恢复
     * @param id
     * @return
     */
    int activate(String id);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 编辑用户信息
     * @param map
     * @return
     */
    int updateSelective(Map<Object,Object> map);

    /**
     * 按条件分页查询
     * @param map
     * @return
     */
    List<Agent> querySelective(Map<Object,Object> map);

    /**
     * 按条件查询
     * @param map
     * @return
     */
    List<Agent> queryForSize(Map<Object,Object> map);

    int insertSelective(Agent agent);
}