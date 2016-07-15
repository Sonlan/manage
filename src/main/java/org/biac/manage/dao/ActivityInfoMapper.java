package org.biac.manage.dao;

import org.biac.manage.entity.ActivityInfo;

public interface ActivityInfoMapper {

    /**
     * 新增一条活动信息记录
     * @param activityInfo
     * @return
     */
    int insertSelective(ActivityInfo activityInfo);

    /**
     * 删除一条活动信息记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新一条活动信息记录
     * @param activityInfo
     * @return
     */
    int updateByIdSelective(ActivityInfo activityInfo);
}