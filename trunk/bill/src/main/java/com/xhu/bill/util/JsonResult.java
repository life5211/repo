package com.xhu.bill.util;

import java.io.Serializable;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 21:54
 */
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = -5759384902264877013L;

    private int state;
    private String message;
    private T data;
    private Integer count;
    private Integer totalPage;

    public JsonResult(T data) {
        this.data = data;
    }

    public JsonResult(T data, Integer count, Integer totalPage) {
        this.data = data;
        this.count = count;
        this.totalPage = totalPage;
    }

    public JsonResult() {
        this.state = ConstantUtil.SYSTEM_ERROR;
        this.message = ConstantUtil.SYSTEM_ERROR_MESSAGE;
    }

    public JsonResult(int state, String message) {
        this.state = state;
        this.message = message;
    }
}
