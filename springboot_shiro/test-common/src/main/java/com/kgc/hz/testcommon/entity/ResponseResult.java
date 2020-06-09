package com.kgc.hz.testcommon.entity;

import java.io.Serializable;

public class ResponseResult implements Serializable {

    private boolean result;
    private Integer flag;
    private Object data;

    public ResponseResult() {

    }

    public ResponseResult(boolean result, Integer flag, Object data) {
        this.result = result;
        this.flag = flag;
        this.data = data;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
