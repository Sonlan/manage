package org.biacbiac.manage.entity;

import java.math.BigInteger;

public class UserIdentity {
    //记录id,主键，自增长
    private Long id;
    //微信openid
    private String openid;
    //0-消费者，1-卖家
    private int identity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public UserIdentity(String openid) {
        this.openid = openid;
    }

    public UserIdentity(BigInteger id, String openid, Integer identity) {
        this.id = id.longValue();
        this.openid = openid;
        this.identity = identity;
    }
}