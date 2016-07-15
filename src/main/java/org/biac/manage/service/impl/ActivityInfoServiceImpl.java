package org.biac.manage.service.impl;

import org.biac.manage.dao.ActivityInfoMapper;
import org.biac.manage.entity.ActivityInfo;
import org.biac.manage.service.ActivityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Song on 2016/7/14.
 */
@Service
public class ActivityInfoServiceImpl implements ActivityInfoService{
    @Autowired
    private ActivityInfoMapper activityInfoDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 新增一条经销点信息记录
     *
     * @param activityInfo
     * @return
     */
    public int add(ActivityInfo activityInfo) {
        return 0;
    }

    /**
     * 删除一条经销点信息记录
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        return 0;
    }

    /**
     * 修改一条经销点信息
     *
     * @param activityInfo
     * @return
     */
    public int update(ActivityInfo activityInfo) {
        return 0;
    }
}
