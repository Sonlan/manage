package org.biac.manage.service.impl;

import org.biac.manage.dao.SaleRecordMapper;
import org.biac.manage.service.SaleRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
