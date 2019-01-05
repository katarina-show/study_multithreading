package com.sjw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock的标准用法
 */
public class LockTemplate {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try{
            // do my work.....
            System.out.println("锁起来的代码逻辑");
        }finally{
            //一定不要忘记unlock
            lock.unlock();
        }
    }

}
