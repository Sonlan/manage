package org.biacbiac.manage.service;

import org.biacbiac.manage.entity.ActivityInfo;

import java.util.List;

/**
 * Created by Song on 2016/7/14.
 */
public interface ActivityInfoService {
    /**
     * 新增一条活动信息记录
     * @param activityInfo
     * @return
     */
    int add(ActivityInfo activityInfo);

    /**
     * 删除一条活动信息记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 修改一条活动信息
     * @param activityInfo
     * @return
     */
    int update(ActivityInfo activityInfo);

    /**
     * 根据活动主题名或活动id查询活动信息
     * @param activity_id 活动id
     * @param theme 活动主题名
     * @param pageNum 待查询的页码
     * @return
     */
    List<ActivityInfo> query(String activity_id,String theme,String pageNum);

    /**
     * 获得记录的总数
     * @param activity_id
     * @param theme
     * @return
     */
    int queryForSize(String activity_id,String theme);
}
