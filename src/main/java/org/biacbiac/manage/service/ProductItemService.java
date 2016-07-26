package org.biacbiac.manage.service;

import org.biacbiac.manage.entity.ProductItem;

import java.util.List;

/**
 * Created by Song on 2016/7/15.
 */
public interface ProductItemService {
    /**
     * 新增一条产品记录
     * @param productItem
     * @return
     */
    int add(ProductItem productItem);

    /**
     * 删除一条产品记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 修改一条产品信息
     * @param productItem
     * @return
     */
    int update(ProductItem productItem);

    /**
     * 按产品名称分页查询
     * @param name
     * @param page
     * @return
     */
    List<ProductItem> query(String name,String page);

    /**
     * 按产品名称查询符合条件的记录数目
     * @param name
     * @return
     */
    int queryForSize(String name);
}
