package org.biac.manage.dao;

import org.biac.manage.entity.ProductItem;

public interface ProductItemMapper {
    /**
     * 新增一条产品类型记录
     * @param productItem
     * @return
     */
    int insertSelective(ProductItem productItem);

    /**
     * 删除一条产品记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新一条产品记录
     * @param productItem
     * @return
     */
    int updateByIdSelective(ProductItem productItem);
}