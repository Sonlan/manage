package org.biac.manage.dao;

import org.biac.manage.entity.ProductItem;

import java.util.List;
import java.util.Map;

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

    /**
     * 按条件分页查询
     * @param map
     * @return
     */
    List<ProductItem> query(Map<Object,Object> map);

    /**
     * 按条件查询
     * @param map
     * @return
     */
    List<ProductItem> queryForSize(Map<Object,Object> map);

}