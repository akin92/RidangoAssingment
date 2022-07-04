package com.ridango.payment.model;

public class ResponseError {
    Integer error;
    String desc;

    public ResponseError(Integer error, String desc) {
        this.error = error;
        this.desc = desc;
    }

    public ResponseError() {
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
