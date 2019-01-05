package com.sjw;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 使用线程池
 */
public class UseThreadPool {

    static class MyTask implements Runnable {

        private String name;


        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {// 执行任务
            try {
                Random r = new Random();
                Thread.sleep(r.nextInt(1000) + 2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId()+" sleep InterruptedException:"
                        +Thread.currentThread().isInterrupted());
            }
            System.out.println("任务 " + name + " 完成");
        }
    }

    public static void main(String[] args) {

        System.out.println("当前计算机有 " + Runtime.getRuntime().availableProcessors() + " 个CPU");
        //1.创建1个完全定义的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        //2.使用Executors的静态方法，创建一些常用的线程池
        ExecutorService threadPool2 = Executors.newFixedThreadPool(2);
        ExecutorService threadPool3 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool4 = Executors.newCachedThreadPool();
        ExecutorService threadPool5 = Executors.newWorkStealingPool();

        //关于Scheduled更详细的在相关包下
        ExecutorService threadPool6 = Executors.newScheduledThreadPool(2);
        ExecutorService threadPool7 = Executors.newSingleThreadScheduledExecutor();

        for(int i = 0;i <= 5; i++){
            MyTask task = new MyTask("Task_"+i);
            System.out.println("A new task will add:" + task.getName());
            threadPoolExecutor.execute(task);

        }
        threadPoolExecutor.shutdown();
    }


}
