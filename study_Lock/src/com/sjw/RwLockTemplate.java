package com.sjw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁ReentrantReadWriteLock的标准用法
 */
public class RwLockTemplate {

    static final Map<String,String> map = new HashMap<>();

    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    //获取读锁
    static Lock readLock = reentrantReadWriteLock.readLock();

    //获取写锁
    static Lock writeLock = reentrantReadWriteLock.writeLock();

    //写操作
    public void put(){
        writeLock.lock();
        try{
            // do my work.....
        }finally{
            writeLock.unlock();
        }
    }

    //读操作
    public void get(){
        readLock.lock();
        try{
            // do my work.....
        }finally{
            readLock.unlock();
        }
    }

}
