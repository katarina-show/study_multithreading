package com.sjw.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义独占锁
 */
public class SingleLock implements Lock {

    static class Sync extends AbstractQueuedSynchronizer{

        //  独占锁获取
        public boolean tryAcquire(int arg){
            //原子操作0改成1
            if (compareAndSetState(0,1)){
                //设置独占线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //独占锁释放
        public boolean tryRelease(int arg){
            setExclusiveOwnerThread(null);
            //只有拿到锁的线程，才能释放锁，所以没必要CAS
            setState(0);
            return true;
        }

        //是否处于占用状态  1代表有线程在占用
        public boolean isHeldExclusively(){
            return getState()  == 1;
        }

        Condition newCondition(){
            return new ConditionObject();
        }

    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);

    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);

    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
