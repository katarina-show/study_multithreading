package com.sjw.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 使用delayQueue 做数据缓存
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        DelayQueue<CacheBean<User>> queue = new DelayQueue<>();
        //分别启动一个生产者和消费者线程
        new Thread(new PutInCache(queue),"生产者").start();
        new Thread(new GetFromCache(queue),"消费者").start();

        for(int i = 1; i <= 20; i++){
            Thread.sleep(500);
            System.out.println(i * 500);
        }
    }
}
