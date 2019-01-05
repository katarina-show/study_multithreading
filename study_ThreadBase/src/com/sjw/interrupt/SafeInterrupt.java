package com.sjw.interrupt;

/**
 * 安全的中断线程
 * 线程处于阻塞（如调用了java的sleep,wait等等方法时）的时候，是不会理会我们自己设置的取消标志位的
 * 但是这些阻塞方法都会检查线程的中断标志位
 */
public class SafeInterrupt implements Runnable {

    private volatile boolean on = true;
    private long i = 0;

    @Override
    public void run() {
        while(on && Thread.currentThread().isInterrupted()){
            i++;
        }
        System.out.println("TestRunable is runing :"+i);
    }

    public void cancel(){
        on = false;
        Thread.currentThread().interrupt();
    }
}
