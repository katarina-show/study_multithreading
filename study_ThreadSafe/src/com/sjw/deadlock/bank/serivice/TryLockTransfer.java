package com.sjw.deadlock.bank.serivice;

import com.sjw.deadlock.bank.Account;

import java.util.Random;

/**
 * 定时轮询获取锁  解决动态顺序死锁
 */
public class TryLockTransfer implements ITransfer {
    @Override
    public void transfer(Account from, Account to, int amount)
            throws InterruptedException {
        Random r = new Random();
        //死循环
        while(true){
            if(from.getLock().tryLock()){
                try{
                    System.out.println(Thread.currentThread().getName()
                            +" get from "+from.getName());

                    if(to.getLock().tryLock()){
                        try{
                            System.out.println(Thread.currentThread().getName()
                                    +" get to "+to.getName());
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            System.out.println(from);
                            System.out.println(to);
                            break;
                        }finally {
                            to.getLock().unlock();
                        }
                    }

                }finally {
                   from.getLock().unlock();
                }
            }
            //如果注释掉休眠，有很大概率产生活锁
            Thread.sleep(r.nextInt(5));
        }
    }
}
