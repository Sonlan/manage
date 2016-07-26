package org.biacbiac.manage.dao;

import org.biacbiac.manage.entity.ActivityInfo;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据活动名称以及活动id查询活动信息详情
     * @param map
     * @return
     */
    List<ActivityInfo> querySelective(Map<Object,Object> map);

    /**
     * 查询符合条件的所有活动信息记录
     * @param map
     * @return
     */
    List<ActivityInfo> queryForSize(Map<Object,Object> map);

}