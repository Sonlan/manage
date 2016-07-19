package org.biac.manage.service.impl;

import org.biac.manage.dao.SalesmanMapper;
import org.biac.manage.entity.Salesman;
import org.biac.manage.service.SalesmanService;
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
public class SalesmanServiceImpl implements SalesmanService{
    @Autowired
    private SalesmanMapper salesmanDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 推销员用户挂起
     *
     * @param id
     * @return
     */
    public int suspend(String id) {
        try{
            salesmanDao.suspend(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"推销员信息挂起异常");
            return 1;
        }
    }

    /**
     * 推销员用户恢复
     *
     * @param id
     * @return
     */
    public int activate(String id) {
        try{
            salesmanDao.activate(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"推销员信息恢复异常");
            return 1;
        }
    }

    /**
     * 删除操作
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            salesmanDao.delete(id);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"推销员信息删除异常");
            return 1;
        }
    }

    /**
     * 用户信息编辑
     *
     * @param salesman
     * @return
     */
    public int edit(Salesman salesman) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("record",salesman);
            salesmanDao.updateSelective(map);
            return 0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"推销员信息修改异常");
            return 1;
        }
    }

    /**
     * 推销员用户分页查询
     *
     * @param name
     * @param work_id
     * @param status
     * @param page
     * @return
     */
    public List<Salesman> query(long store_id,String name, String work_id, String status, String page) {
        try{
            int pageNum = Integer.parseInt(page);
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("store_id",store_id);
            map.put("name",name);
            map.put("work_id",work_id);
            map.put("status",status);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return salesmanDao.querySelective(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"推销员信息获取异常");
            return  null;
        }
    }

    /**
     * 返回符合查询条件的信息总数
     *
     * @param name
     * @param work_id
     * @param status
     * @return
     */
    public int queryForSize(long store_id,String name, String work_id, String status) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("store_id",store_id);
            map.put("name",name);
            map.put("work_id",work_id);
            map.put("status",status);
            return salesmanDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"推销员信息数目获取异常");
            return  0;
        }
    }
}
