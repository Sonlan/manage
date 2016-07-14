package org.biac.manage.dao;

import org.biac.manage.entity.ActivityInfo;

public interface ActivityInfoMapper {

    int deleteByPrimaryKey(long id);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    ActivityInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);
}