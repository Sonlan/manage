package org.biacbiac.manage.service.impl;

import org.biacbiac.manage.dao.SaleRecordMapper;
import org.biacbiac.manage.entity.SaleRecord;
import org.biacbiac.manage.service.SaleRecordService;
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
public class SaleRecordServiceImpl  implements SaleRecordService{
    @Autowired
    private SaleRecordMapper saleRecordDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消费记录删除
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            saleRecordDao.delete(id);
            return  0;
        }catch (Exception e){
            logger.error(this.getClass().toString()+"消费信息删除异常");
            return 1;
        }
    }

    /**
     * 分页查询
     *
     * @param nickname
     * @param code
     * @param page
     * @return
     */
    public List<SaleRecord> query(String nickname, String code, String page) {
        try{
            int pageNum = Integer.parseInt(page);
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("nickname",nickname);
            map.put("code",code);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return saleRecordDao.querySelective(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"消费记录信息获取异常");
            return  null;
        }
    }

    /**
     * 获取记录数
     *
     * @param nickname
     * @param code
     * @return
     */
    public int queryForSize(String nickname, String code) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("nickname",nickname);
            map.put("code",code);
            return saleRecordDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"消费记录信息数目获取异常");
            return  0;
        }
    }
}
