package com.oneshoppoint.yates.util;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public class Pagination<T> {
    private List<T> tList;
    private Integer next;
    private Integer prev;

    public Pagination (List<T> tList,Integer next,Integer prev) {
        this.tList = tList;
        this.next = next;
        this.prev = prev;
    }

    public List<T> getList() {
        return tList;
    }

    public Integer getPrev() {
        return prev;
    }

    public Integer getNext() {
        return next;
    }
}
