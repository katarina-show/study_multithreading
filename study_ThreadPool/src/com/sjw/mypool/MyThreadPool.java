package com.sjw.mypool;

import java.util.LinkedList;
import java.util.List;

/**
 * 手写1个线程池
 */
public class MyThreadPool {

    //默认的线程个数
    private int work_num = 5;

    //线程的容器
    private WorkThread[] workThreads;

    //任务队列
    private List<Runnable> taskQueue = new LinkedList<>();

    public MyThreadPool(int work_num) {
        this.work_num = work_num;
        workThreads = new WorkThread[work_num];
        //在构造的同时，预先开启n个工作线程
        for(int i = 0; i < work_num; i++){
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    //默认开启5个工作线程
    public MyThreadPool() {
        workThreads = new WorkThread[work_num];
        for(int i=0;i<work_num;i++){
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    //提交任务的方法
    public void execute(Runnable task){
        //LinkedList线程不安全，所以锁一下
        synchronized (taskQueue){
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    //销毁线程池，中断所有工作线程
    public void destroy(){
        System.out.println("ready stop pool....");
        for(int i = 0; i < work_num; i++){
            workThreads[i].stopWorker();
            workThreads[i] = null;//加速垃圾回收
        }
        //清空任务队列
        taskQueue.clear();
    }

    //工作线程---主要的任务就是从队列里拿任务并执行
    private class WorkThread extends Thread{

        //工作线程的状态，默认是工作中
        private volatile boolean on = true;

        public void run(){
            Runnable r = null;
            try{
                while(on && !isInterrupted()){
                    synchronized (taskQueue){
                        //任务队列中无任务，工作线程等待
                        while(on && !isInterrupted() && taskQueue.isEmpty()){
                            taskQueue.wait(1000);
                        }
                        //任务队列中有任务，拿任务做事
                        if(on && !isInterrupted() && !taskQueue.isEmpty()){
                            r = taskQueue.remove(0);
                        }
                    }
                    if (r != null){
                        System.out.println(getId() + " ready execute....");
                        r.run();
                    }
                    //加速垃圾回收
                    r = null;
                }

            }catch(InterruptedException e){
                System.out.println(Thread.currentThread().getId()+" is Interrupted");
            }
        }

        public void stopWorker(){
            on = false;
            interrupt();
        }

    }

}
