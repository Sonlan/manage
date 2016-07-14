package org.biac.manage.dao;


import org.biac.manage.entity.User;

public interface UserMapper {

    int deleteById(long id);

    int insert(User record);

    int insertSelective(User record);

    User selectById(long id);

    User selectByOpenid(String openid);

    int updateByPrimaryKeySelective(User record);

    int updateByOpenid(User record);
}