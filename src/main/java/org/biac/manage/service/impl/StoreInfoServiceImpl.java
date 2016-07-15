package org.biac.manage.service.impl;

import org.biac.manage.dao.StoreInfoMapper;
import org.biac.manage.entity.StoreInfo;
import org.biac.manage.service.StoreInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Song on 2016/7/15.
 */
@Service
public class StoreInfoServiceImpl implements StoreInfoService{
    @Autowired
    private StoreInfoMapper storeInfoDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 新增一条经销点信息记录
     *
     * @param storeInfo
     * @return
     */
    public int add(StoreInfo storeInfo) {
        try{
            storeInfoDao.insertSelective(storeInfo);
            return  0;
        }catch (Exception e){
            logger.error((this.getClass().toString()+"经销点信息新增异常"));
            return  1;
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
            storeInfoDao.delete(id);
            return  0;
        }catch (Exception e){
            logger.error((this.getClass().toString()+"经销点信息删除异常"));
            return  1;
        }
    }

    /**
     * 修改一条经销点信息
     *
     * @param storeInfo
     * @return
     */
    public int update(StoreInfo storeInfo) {
        try{
            storeInfoDao.updateByIdSelective(storeInfo);
            return  0;
        }catch (Exception e){
            logger.error((this.getClass().toString()+"经销点信息修改异常"));
            return  1;
        }
    }
}
