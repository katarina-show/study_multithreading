package com.sjw.connectpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 手写数据库连接池
 */
public class ConnectionPoolTest {
    static ConnectionPool pool  = new ConnectionPool(10);
    // 保证所有ConnectionRunner能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main线程将会等待所有ConnectionRunner结束后才能继续执行
    static CountDownLatch end;

    //main方法执行结果需等待几秒，每次运行结果都不一样
    public static void main(String[] args) throws Exception {
        // 线程数量，可以改变线程数量进行观察
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        //每个线程循环取20次，也就是说总共取threadCount * count 个数据库连接
        int count = 20;
        AtomicInteger got = new AtomicInteger();//获取到数据库连接的次数
        AtomicInteger notGot = new AtomicInteger();//没有获取到数据库连接的次数
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot),
                    "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection:  " + got);
        System.out.println("not got connection " + notGot);
    }

    static class ConnectionRunner implements Runnable {
        int           count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got,
                               AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        public void run() {
            try {
                //每个线程启动后，在执行逻辑前，调用await，保证所有线程都停在这里，countdown后一起开始
                start.await();
            } catch (Exception ex) {

            }
            while (count > 0) {
                try {
                    // 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
