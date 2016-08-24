package org.biac.manage.entity;

import org.springframework.format.annotation.DateTimeFormat;

public class StoreInfo {
    private long id;

    private String name;

    private String ranges;

    private String areaCode;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String createDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges == null ? null : ranges.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}