package org.biac.manage.service.impl;

import org.biac.manage.dao.ProductItemMapper;
import org.biac.manage.entity.ProductItem;
import org.biac.manage.service.ProductItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Song on 2016/7/15.
 */
@Service
public class ProductItemServiceImpl implements ProductItemService {
    @Autowired
    private ProductItemMapper productItemDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 新增一条产品记录
     *
     * @param productItem
     * @return
     */
    public int add(ProductItem productItem) {
        try{
            productItemDao.insertSelective(productItem);
            return  0;
        }catch (Exception e){
            logger.error((this.getClass().toString()+"产品信息新增异常"));
            return  1;
        }
    }

    /**
     * 删除一条产品记录
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        try{
            productItemDao.delete(id);
            return  0;
        }catch (Exception e){
            logger.error((this.getClass().toString()+"产品信息删除异常"));
            return  1;
        }
    }

    /**
     * 修改一条产品信息
     *
     * @param productItem
     * @return
     */
    public int update(ProductItem productItem) {
        try{
            productItemDao.updateByIdSelective(productItem);
            return  0;
        }catch (Exception e){
            logger.error((this.getClass().toString()+"产品信息修改异常"));
            return  1;
        }
    }
}
