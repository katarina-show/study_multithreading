package com.sjw.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * 生产者线程
 */
public class PutInCache implements Runnable {

    private DelayQueue<CacheBean<User>> queue;

    public PutInCache(DelayQueue<CacheBean<User>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        //两个数据的过期时间分别设置为5秒和3秒
        CacheBean cacheBean = new CacheBean("1","5秒",new User("sjw"),5000);
        CacheBean cacheBean2 = new CacheBean("2","3秒",new User("caq"),3000);
        //delayQueue中，put、add和offer方法功能完全一样
        queue.offer(cacheBean);
        System.out.println("put in cache: cache id = " + cacheBean.getId() + "---" + "cache name = " + cacheBean.getName());
        queue.offer(cacheBean2);
        System.out.println("put in cache: cache id = " + cacheBean2.getId() + "---" + "cache name = " +cacheBean2.getName());
    }
}
