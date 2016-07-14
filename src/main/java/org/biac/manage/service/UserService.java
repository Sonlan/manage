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
}
