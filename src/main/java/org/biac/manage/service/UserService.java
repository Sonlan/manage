package org.biac.manage.service;

import org.biac.manage.entity.User;

import java.util.List;


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

    /**
     * 根据用户微信昵称分页查询用户
     * @param nickname
     * @param page
     * @return
     */
    List<User> query(String nickname, String page);

    /**
     * 查询得到符合查询条件的消息总数
     * @param nickname
     * @return
     */
    int queryForSize(String nickname);

    int test();
}
