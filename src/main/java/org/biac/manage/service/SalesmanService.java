package org.biac.manage.service;

import org.biac.manage.entity.Salesman;

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
}
