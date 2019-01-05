package com.sjw.atomicint;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 原子更新Integer
 */
public class AtomicIntTest {
    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {

        //以原子方式：返回的是自增前的值，然后+1
        System.out.println(ai.getAndIncrement());

        //以原子方式：返回的是自增后的值
        ai.incrementAndGet();
        System.out.println(ai.get());
    }
}
