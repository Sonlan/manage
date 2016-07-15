package org.biac.manage.service;

import org.biac.manage.entity.ProductItem;

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
}
