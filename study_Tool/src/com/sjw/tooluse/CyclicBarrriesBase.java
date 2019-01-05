package com.sjw.tooluse;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier基本使用
 */
public class CyclicBarrriesBase {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                try {
                    //等待主线程完成
                    c.await();
                    System.out.println(Thread.currentThread().getId() + " is running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + " has completed...");

            }
        }).start();

        System.out.println("main will sleep.....");
        Thread.sleep(2000);
        c.await();////等待子线程完成

        System.out.println("All complete.");
    }



}
