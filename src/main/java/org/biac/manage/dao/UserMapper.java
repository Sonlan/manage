package org.biac.manage.dao;


import org.biac.manage.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int deleteById(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectById(long id);

    User selectByOpenid(String openid);

    int updateByPrimaryKeySelective(User record);

    int updateByOpenid(User record);

    /**
     * 将status置1，用户冻结
     * @param id
     * @return
     */
    int suspend(String id);

    /**
     * 将status置0，用户激活
     * @param id
     * @return
     */
    int activate(String id);

    /**
     * 按条件分页查询
     * @param map
     * @return
     */
    List<User> querySelective(Map<Object,Object> map);

    /**
     * 获取符合条件的所有信息
     * @param map
     * @return
     */
    List<User> queryForSize(Map<Object,Object> map);
}