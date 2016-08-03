package org.biac.manage.service;

import org.biac.manage.entity.StoreInfo;

import java.util.List;

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

    /**
     * 按条件分页查询经销点信息
     * @param name 经销点名称
     * @param range 经营范围
     * @param area_code 地理位置编码
     * @param page
     * @return
     */
    List<StoreInfo> query(String name,String range,String area_code,String page);

    /**
     * 获得满足查询条件的信息数
     * @param name
     * @param range
     * @param area_code
     * @return
     */
    int queryForSize(String name,String range,String area_code);

    List<StoreInfo> getall();

    int test();

}

