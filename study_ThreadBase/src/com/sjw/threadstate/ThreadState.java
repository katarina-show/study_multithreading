package com.sjw.threadstate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 查看线程的状态
 * 新创建  线程被创建，但是没有调用start方法
 * 可运行（RUNNABLE）  运行状态，由cpu决定是不是正在运行
 * 被阻塞（BLOCKING）  阻塞，线程被阻塞于锁，注意：是锁，synchronized 和 lock
 * 等待/计时等待（WAITING） 等待某些条件成熟，通常指sleep和wait，需要被中断和notify
 * 被终止  线程执行完毕
 */
public class ThreadState {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(new SleepAlways(), "SleepAlwaysThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
        new Thread(new Sync(), "SyncThread-1").start();
        new Thread(new Sync(), "SyncThread-2").start();
    }

    /**
     * 该线程不断的进行睡眠
     */
    static class SleepAlways implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    /**
     * 该线程在Waiting.class实例上等待
     */
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 该线程在Blocked.class实例上加锁后，不会释放该锁
     */
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }

    /**
     * 该线程获得锁休眠后，又释放锁
     */
    static class Sync implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                SleepUtils.second(3000);
            } finally {
                lock.unlock();
            }

        }

    }
}
