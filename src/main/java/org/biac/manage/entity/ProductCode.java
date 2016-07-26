package org.biac.manage.entity;

import org.springframework.format.annotation.DateTimeFormat;


public class ProductCode {
    private long id;

    private long key;

    private long productId;

    private int consumerFlag;

    private int agentFlag;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getConsumerFlag() {
        return consumerFlag;
    }

    public void setConsumerFlag(int consumerFlag) {
        this.consumerFlag = consumerFlag;
    }

    public int getAgentFlag() {
        return agentFlag;
    }

    public void setAgentFlag(int agentFlag) {
        this.agentFlag = agentFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}