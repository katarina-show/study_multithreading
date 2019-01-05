package com.sjw.bq;

/**
 * 手写队列的测试
 * 效率上可能不如jdk的有界阻塞队列 ArrayBlockingQueue，但是功能已经有了
 */
public class BqTest {
    public static void main(String[] args) {
        //定义队列
        BlockingQueueWN bq = new BlockingQueueWN(10);
        Thread threadA = new ThreadPush(bq);
        threadA.setName("Push");
        Thread threadB = new ThreadPop(bq);
        threadB.setName("Pop");
        threadB.start();
        threadA.start();
    }

    //推数据入队列的线程
    private static class ThreadPush extends Thread{
        BlockingQueueWN<Integer> bq;

        public ThreadPush(BlockingQueueWN<Integer> bq) {
            this.bq = bq;
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            int i = 20;
            while(i > 0){
                try {
                    //入队线程睡眠是为了让出队线程先调用wait，因为list初始是空的，出队线程理应保持在wait状态
                    Thread.sleep(1000);
                    System.out.println(" i=" + i + " will push");
                    bq.enqueue(i--);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }

            }
        }
    }

    //取数据出队列的线程
    private static class ThreadPop extends Thread{
        BlockingQueueWN<Integer> bq;

        public ThreadPop(BlockingQueueWN<Integer> bq) {
            this.bq = bq;
        }
        @Override
        public void run() {
            while(true){
                try {
                    System.out.println(Thread.currentThread().getName()
                            +" will pop.....");
                    Integer i = bq.dequeue();
                    System.out.println(" i="+i.intValue()+" alread pop");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }

        }
    }
}
