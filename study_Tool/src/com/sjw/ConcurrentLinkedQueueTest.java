package com.sjw;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * isEmpty()和size()的性能差异
 * size()带来性能损失，并且不一定取得正确的数量
 *
 * 结论：多用isEmpty，而不是size
 */
public class ConcurrentLinkedQueueTest {
    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private static int count = 50000;
    private static int count2 = 2;
    private static CountDownLatch cd = new CountDownLatch(count2);

    public static void dothis() {
        for (int i = 0; i < count; i++) {
            queue.offer(i);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        ConcurrentLinkedQueueTest.dothis();
        //启用两个线程取数据
        for (int i = 0; i < count2; i++) {
            es.submit(new Poll());
        }
        cd.await();
        System.out.println("cost time "
                + (System.currentTimeMillis() - timeStart) + "ms");
        es.shutdown();
    }
    static class Poll implements Runnable {
        @Override
        public void run() {
            //while (queue.size()>0) {
            while (!queue.isEmpty()) {
                System.out.println(queue.poll());
            }
            cd.countDown();
        }
    }
}
