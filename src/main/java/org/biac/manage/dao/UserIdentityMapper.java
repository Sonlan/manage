package org.biac.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.biac.manage.entity.UserIdentity;


public interface UserIdentityMapper {

    int deleteById(Long id);

    int insert(UserIdentity record);

    int insertSelective(UserIdentity record);

    UserIdentity selectById(Long id);

    UserIdentity selectByOpenid(@Param(value = "openid") String openid);

    int updateByIdSelective(UserIdentity record);

    int updateById(UserIdentity record);
}