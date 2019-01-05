package com.sjw.bq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用 Lock 和 Condition 实现的有界阻塞队列
 */
public class BlockingQueueLC<T> {

    private List queue = new LinkedList<>();

    private final int limit;

    Lock lock = new ReentrantLock();

    //1个锁可以定义多个condition

    private Condition needNotEmpty = lock.newCondition();

    private Condition needNotFull = lock.newCondition();


    public BlockingQueueLC(int limit) {
        this.limit = limit;
    }

    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        try{
            //容量满了，再次入队的线程（生产者）会被阻塞
            while(this.queue.size() == this.limit){
                needNotFull.await();
            }

            //容量没满，成功添加
            this.queue.add(item);
            //唤醒被await在needNotEmpty上的线程，也就是通知可以出队的线程（消费者），告诉他们现在有数据可以取出了
            needNotEmpty.signal();
        }finally{
            lock.unlock();
        }
    }

    public T dequeue() throws InterruptedException {
        lock.lock();
        try{
            //容量为0，出队线程（消费者）被挂起
            while(this.queue.size() == 0){
                needNotEmpty.await();
            }
            //容量不为0，唤醒被await在needNotFull上的线程，也就是通知可以入队的线程（生产者），告诉他们现在可以放数据进来了
            needNotFull.signal();

            //可以发现和notifyAll方法的区别：我们指定condition进行唤醒

            return (T) this.queue.remove(0);
        }finally{
            lock.unlock();
        }
    }
}
