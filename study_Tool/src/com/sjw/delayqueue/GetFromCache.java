package com.sjw.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 消费者线程
 */
public class GetFromCache implements Runnable {

    private DelayQueue<CacheBean<User>> queue;

    public GetFromCache(DelayQueue<CacheBean<User>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                //take--阻塞的拿，当能够取出数据时，说明缓存时间到了
                CacheBean<User> item = queue.take();
                System.out.println("GetFromCache：cache id = " + item.getId() + "--- cache name =" + item.getName()
                        + "--- cache data name = "+ item.getData().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
