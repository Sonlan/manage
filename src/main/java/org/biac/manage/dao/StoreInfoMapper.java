package org.biac.manage.dao;


import org.biac.manage.entity.StoreInfo;

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
}