package org.biac.manage.dao;

import org.biac.manage.entity.CompanyUser;

import java.util.List;
import java.util.Map;

public interface CompanyUserMapper {
    /**
     * 根据用户名查找用户，用户名不能重复
     * @param account
     * @return
     */
    CompanyUser selectByAccount(String account);

    /**
     * 用户登录，检查用户名，密码以及当前用户状态
     * @param map
     * @return
     */
    CompanyUser  login(Map<Object,Object> map);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    CompanyUser selectById(String id);

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
     * 根据企业用户账户名与用户权限分页查找用户
     * @param map
     * @return
     */
    List<CompanyUser> query(Map<Object,Object> map);

    /**+
     * 根据企业用户账户名与用户权限查找用户
     * @param map
     * @return
     */
    List<CompanyUser> queryForSize(Map<Object,Object> map);
}