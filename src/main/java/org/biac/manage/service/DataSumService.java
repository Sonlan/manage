package org.biac.manage.service;


/**
 * Created by Song on 2016/9/8.
 * 数据统计相关接口
 */
public interface DataSumService {
    /**
     * 根据条件查询地域销售数据数据库，返回数据统计json字符串
     * @param province 省份
     * @param year 年份
     * @return json格式数据报表
     */
    String  getSaleDatas(String province,int year);
}
