package org.biac.manage.dao;

import org.biac.manage.entity.SaleRecord;

import java.util.List;
import java.util.Map;

public interface SaleRecordMapper {
    /**
     * 删除记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 按条件分页查询
     * @param map
     * @return
     */
    List<SaleRecord> querySelective(Map<Object,Object> map);

    /**
     * 获取记录总数
     * @param map
     * @return
     */
    List<SaleRecord> queryForSize(Map<Object,Object> map);
}