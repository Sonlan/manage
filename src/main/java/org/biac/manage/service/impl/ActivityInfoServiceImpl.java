package org.biac.manage.service.impl;

import org.biac.manage.dao.ActivityInfoMapper;
import org.biac.manage.entity.ActivityInfo;
import org.biac.manage.service.ActivityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/7/14.
 */
@Service
public class ActivityInfoServiceImpl implements ActivityInfoService{
    @Autowired
    private ActivityInfoMapper activityInfoDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据活动主题名或活动id查询活动信息
     *
     * @param activity_id 活动id
     * @param theme       活动主题名
     * @param page     待查询的页码
     * @return
     */
    public List<ActivityInfo> query(String activity_id, String theme, String page) {
        try{
            int pageNum = Integer.parseInt(page);

            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("activity_id",activity_id);
            map.put("theme",theme);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return activityInfoDao.querySelective(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"查询活动信息出错");
            return null;
        }
    }

    /**
     * 获得记录的总数
     *
     * @param activity_id
     * @param theme
     * @return
     */
    public int queryForSize(String activity_id, String theme) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("activity_id",activity_id);
            map.put("theme",theme);
            return activityInfoDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"查询活动数目信息出错");
            return 0;
        }
    }

    /**
     * 新增一条经销点信息记录
     *
     * @param activityInfo
     * @return
     */
    public int add(ActivityInfo activityInfo) {
        try{
            activityInfoDao.insertSelective(activityInfo);
            return  0;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"插入活动信息异常");
            return 1;
        }
    }

    /**
     * 删除一条经销点信息记录
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            activityInfoDao.delete(id);
            return  0;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"删除活动信息异常");
            return 1;
        }
    }

    /**
     * 修改一条经销点信息
     *
     * @param activityInfo
     * @return
     */
    public int update(ActivityInfo activityInfo) {
        try{
            activityInfoDao.updateByIdSelective(activityInfo);
            return  0;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"修改活动信息异常");
            return 1;
        }
    }
}
