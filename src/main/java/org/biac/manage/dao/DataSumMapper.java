package org.biac.manage.dao;

import org.biac.manage.entity.RegionSaleRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/9/8.
 * 地域销售数据数据库操作接口
 */
public interface DataSumMapper {
    /**
     * 根据查询条件获取数据库数据
     * @param map (tableName:表名,month:月份,hour:小时）
     * @return 地区消费记录ReginSaleRecord
     */
    List<RegionSaleRecord> getRequestedData(Map<String,Object> map);
}
