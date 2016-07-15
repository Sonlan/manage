package org.biac.manage.service;

import org.biac.manage.entity.User;


/**
 * Created by Song on 2016/7/12.
 */
public interface UserService {
    /**
     * 插入一条User记录，成功返回0
     * @param user
     * @return
     */
    int userInsert(User user);

    /**
     * 更新一条User 记录，成功返回0
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 用户挂起
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 用户恢复激活
     * @param id
     * @return
     */
    int activate(String id);

    /**
     * 消费者信息删除
     * @param id
     * @return
     */
    int delete(String id);
}
