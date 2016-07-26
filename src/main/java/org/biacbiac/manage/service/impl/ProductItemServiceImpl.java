package org.biacbiac.manage.service.impl;

import org.biacbiac.manage.dao.ProductItemMapper;
import org.biacbiac.manage.entity.ProductItem;
import org.biacbiac.manage.service.ProductItemService;
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

    /**
     * 按产品名称分页查询
     *
     * @param name
     * @param page
     * @return
     */
    public List<ProductItem> query(String name, String page) {
        try{
            int pageNum = Integer.parseInt(page);
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("name",name);
            map.put("page_start",pageNum*10);
            map.put("page_end",pageNum*10+10);
            return productItemDao.query(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"查询产品信息异常");
            return null;
        }
    }

    /**
     * 按产品名称查询符合条件的记录数目
     *
     * @param name
     * @return
     */
    public int queryForSize(String name) {
        try{
            Map<Object,Object> map = new HashMap<Object, Object>();
            map.put("name",name);
            return productItemDao.queryForSize(map).size();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass().toString()+"查询产品数目信息异常");
            return 0;
        }
    }
}
