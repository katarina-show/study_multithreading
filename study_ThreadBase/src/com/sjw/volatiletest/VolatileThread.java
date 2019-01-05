package com.sjw.volatiletest;

/**
 * 测试Volatile型变量的操作原子性
 */
public class VolatileThread implements Runnable {

    private volatile int a = 0;

    @Override
    public void run() {
        //这个锁如果存在，那么结果就是正常的
        //synchronized (this){
            a = a + 1;
            System.out.println(Thread.currentThread().getName()+"----"+a);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a = a + 1;
            System.out.println(Thread.currentThread().getName()+"----"+a);

        //}
    }
}
