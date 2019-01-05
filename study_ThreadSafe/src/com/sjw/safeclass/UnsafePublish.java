package com.sjw.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 * 不安全的发布
 **/
public class UnsafePublish {
    private List<String> list = new ArrayList<>();

    /*不安全的发布，将内部的线程不安全的list发布出去了*/
    public synchronized List getList() {
        return list;
    }

    public synchronized void setList(List<String> list) {
        this.list = list;
    }
}
