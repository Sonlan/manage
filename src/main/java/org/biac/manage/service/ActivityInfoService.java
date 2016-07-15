package org.biac.manage.service;

import org.biac.manage.entity.ActivityInfo;

/**
 * Created by Song on 2016/7/14.
 */
public interface ActivityInfoService {
    /**
     * 新增一条经销点信息记录
     * @param activityInfo
     * @return
     */
    int add(ActivityInfo activityInfo);

    /**
     * 删除一条经销点信息记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 修改一条经销点信息
     * @param activityInfo
     * @return
     */
    int update(ActivityInfo activityInfo);
}
