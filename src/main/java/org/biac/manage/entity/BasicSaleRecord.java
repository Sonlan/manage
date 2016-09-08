package org.biac.manage.entity;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Song on 2016/9/8.
 * 销售数据实体类抽象模板
 */
public abstract class BasicSaleRecord {
    protected long id;  //数据库记录id
    protected long code;  //产品识别码
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    protected String dates;  //销售日期
    protected String addr;  //销售地点

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
