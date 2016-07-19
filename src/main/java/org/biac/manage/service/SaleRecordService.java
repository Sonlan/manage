package org.biac.manage.service;

import org.biac.manage.entity.SaleRecord;

import java.util.List;

/**
 * Created by Song on 2016/7/15.
 */
public interface SaleRecordService {
    /**
     * 消费记录删除
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 分页查询
     * @param nickname
     * @param code
     * @param page
     * @return
     */
    List<SaleRecord> query(String nickname, String code,String page);

    /**
     * 获取记录数
     * @param nickname
     * @param code
     * @return
     */
    int queryForSize(String nickname, String code);
}
