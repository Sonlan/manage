package org.biac.manage.service;

import org.biac.manage.entity.StoreInfo;

/**
 * Created by Song on 2016/7/15.
 */
public interface StoreInfoService {
    /**
     * 新增一条经销点信息记录
     * @param storeInfo
     * @return
     */
    int add(StoreInfo storeInfo);

    /**
     * 删除一条经销点信息记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 修改一条经销点信息
     * @param storeInfo
     * @return
     */
    int update(StoreInfo storeInfo);
}

