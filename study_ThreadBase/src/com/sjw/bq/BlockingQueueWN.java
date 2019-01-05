package com.sjw.bq;

import java.util.LinkedList;
import java.util.List;

/**
 * 手写模拟1个有界阻塞队列
 */
public class BlockingQueueWN<T> {

    private List<T> queue = new LinkedList<>();
    //容量大小
    private final int limit;

    public BlockingQueueWN(int limit) {
        this.limit = limit;
    }

    //入队
    public synchronized void enqueue(T item) throws InterruptedException {
        //满了就wait
        while(this.queue.size() == this.limit){
            wait();
        }
        //将数据入队，可以肯定有出队的线程正在等待
        if (this.queue.size() == 0){
            notifyAll();
        }
        this.queue.add(item);
    }

    //出队
    public synchronized T dequeue() throws InterruptedException {
        //没有元素可出队，就先wait
        while(this.queue.size() == 0){
            wait();
        }
        if (this.queue.size() == this.limit){
            notifyAll();
        }
        return this.queue.remove(0);
    }
}
