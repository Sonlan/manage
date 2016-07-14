package org.biac.manage.service.impl;

import org.biac.manage.dao.SalesmanMapper;
import org.biac.manage.entity.Salesman;
import org.biac.manage.service.SalesmanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
}
