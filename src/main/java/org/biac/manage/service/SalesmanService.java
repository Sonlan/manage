package org.biac.manage.service;

import org.biac.manage.entity.Agent;
import org.biac.manage.entity.Salesman;

import java.util.List;

/**
 * Created by Song on 2016/7/14.
 */
public interface SalesmanService {
    /**
     * 推销员用户挂起
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 推销员用户恢复
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
    int edit(Salesman agent);

    /**
     * 推销员用户分页查询
     * @param name
     * @param work_id
     * @param status
     * @param page
     * @return
     */
    List<Salesman> query(long store_id,String name, String work_id, String status, String page);

    /**
     * 返回符合查询条件的信息总数
     * @param name
     * @param work_id
     * @param status
     * @return
     */
    int queryForSize(long store_id,String name, String work_id, String status);

    /**
     * 推销员注册
     * @param key
     * @param nickname
     * @param openid
     * @return
     */
    int register(String key,String nickname,String openid,String account);
}
