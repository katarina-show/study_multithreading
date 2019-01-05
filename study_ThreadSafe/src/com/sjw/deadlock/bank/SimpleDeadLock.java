package com.sjw.deadlock.bank;

/**
 * 简单顺序死锁
 * 解决方法：规定获取锁的顺序
 */
public class SimpleDeadLock {

    private static Object left = new Object();
    private static Object right = new Object();

    private static void leftToRight() throws InterruptedException {
        synchronized (left){
            System.out.println(Thread.currentThread().getName()+" get left");
            Thread.sleep(100);
            synchronized (right){
                System.out.println(Thread.currentThread().getName()+" get right");
            }
        }
    }

    private static void rightToLeft() throws InterruptedException {
        synchronized (right){
            System.out.println(Thread.currentThread().getName()+" get right");
            Thread.sleep(100);
            synchronized (left){
                System.out.println(Thread.currentThread().getName()+" get left");
            }
        }
    }

    private static class TestThread extends Thread{
        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run(){
            try {
                rightToLeft();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("Main");
        TestThread  testThread = new TestThread("testThread");
        testThread.start();
        try {
            leftToRight();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
