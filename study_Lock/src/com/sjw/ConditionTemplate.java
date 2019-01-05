package com.sjw;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的标准用法
 */
public class ConditionTemplate {

    Lock lock = new ReentrantLock();

    //newCondition方法
    Condition condition = lock.newCondition();

    public void waitc() throws InterruptedException {
        lock.lock();
        try{
            condition.await();
        }finally{
            lock.unlock();
        }
    }

    public void waitnotify() throws InterruptedException {
        lock.lock();
        try{
            condition.signal();
            //condition.signalAll();尽量少使用
        }finally{
            lock.unlock();
        }
    }


}
