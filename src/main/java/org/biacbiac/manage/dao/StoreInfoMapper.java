package org.biacbiac.manage.dao;


import org.biacbiac.manage.entity.StoreInfo;

import java.util.List;
import java.util.Map;

public interface StoreInfoMapper {
    /**
     * 新增一条经销点信息记录
     * @param storeInfo
     * @return
     */
    int insertSelective(StoreInfo storeInfo);

    /**
     * 删除一条经销点信息记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 更新一条经销点信息记录
     * @param storeInfo
     * @return
     */
    int updateByIdSelective(StoreInfo storeInfo);

    /**
     * 按条件分页查询
     * @param map
     * @return
     */
    List<StoreInfo> querySelective(Map<Object,Object> map);

    /**
     * 按条件查询
     * @param map
     * @return
     */
    List<StoreInfo> queryForSize(Map<Object,Object> map);
}