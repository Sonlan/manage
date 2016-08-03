package org.biac.manage.service.impl;

import org.biac.manage.dao.AreaMapper;
import org.biac.manage.dao.StoreInfoMapper;
import org.biac.manage.entity.StoreInfo;
import org.biac.manage.service.StoreInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/7/15.
 */
@Service
public class StoreInfoServiceImpl implements StoreInfoService{
    @Autowired
    private StoreInfoMapper storeInfoDao;
    @Autowired
    private AreaMapper areaDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 新增一条经销点信息记录
     *
     * @param storeInfo
     * @return
     */
    public int add(StoreInfo storeInfo){
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

    /**
     * 按条件分页查询经销点信息
     *
     * @param name      经销点名称
     * @param range     经营范围
     * @param area_code 地理位置编码
     * @param page
     * @return
     */
    public List<StoreInfo> query(String name, String range, String area_code, String page) {
        try{
            int pageNum = Integer.parseInt(page);
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("name",name);
            map.put("range",range);
            map.put("area_code",area_code);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return storeInfoDao.querySelective(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"经销点信息获取异常");
            return  null;
        }
    }

    /**
     * 获得满足查询条件的信息数
     *
     * @param name
     * @param range
     * @param area_code
     * @return
     */
    public int queryForSize(String name, String range, String area_code) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("name",name);
            map.put("range",range);
            map.put("area_code",area_code);
            return storeInfoDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"经销点信息数目获取异常");
            return  0;
        }
    }

    public List<StoreInfo> getall() {
        try{
            return storeInfoDao.getall();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int test() {
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setName("test1");
        storeInfoDao.insertSelective(storeInfo);
        throw new RuntimeException("test storeinfo");
    }
}
