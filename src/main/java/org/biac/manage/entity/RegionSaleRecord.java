package org.biac.manage.entity;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Song on 2016/9/8.
 * 地域销售数据实体类
 */
public class RegionSaleRecord extends BasicSaleRecord{
    private String typeid;  //产品类型识别码
    private int csex;  //消费者性别-->1为男，2为女
    @DateTimeFormat(pattern = "hh:mm:ss")
    private String times;  //消费具体时间
    private String r1;  //保留字段
    private String r2;  //保留字段

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public int getCsex() {
        return csex;
    }

    public void setCsex(int csex) {
        this.csex = csex;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }
}
