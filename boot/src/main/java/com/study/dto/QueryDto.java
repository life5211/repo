package com.study.dto;

public class QueryDto<T> {
    private T t;
    private Integer size;

    public T getT() {
        return t;
    }

    public QueryDto<T> setT(T t) {
        this.t = t;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public QueryDto<T> setSize(Integer size) {
        this.size = size;
        return this;
    }

    public QueryDto(T t, Integer size) {
        this.t = t;
        this.size = size;
    }
}
