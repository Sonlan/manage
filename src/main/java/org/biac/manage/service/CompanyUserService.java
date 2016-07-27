package org.biac.manage.service;

import org.biac.manage.entity.CompanyUser;

import java.util.List;

/**
 * Created by Song on 2016/7/14.
 */
public interface CompanyUserService {
    /**
     * 企业用户登录，成功返回企业用户个人信息，失败返回null
     * @param account
     * @param password
     * @return
     */
    CompanyUser login(String account, String password);

    /**
     * 企业用户用户挂起
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 企业用户用户恢复
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
    int edit(CompanyUser agent);

    /**
     * 根据账户名与权限分页查找用户
     * @param account
     * @param authority
     * @param page
     * @return
     */
    List<CompanyUser> query(String account,String authority,String page,String status);

    /**
     * 返回符合查询条件的记录数目
     * @param account
     * @param authority
     * @return
     */
    int queryForSize(String account,String authority,String status);
}
