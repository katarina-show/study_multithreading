package com.sjw.deadlock.bank.serivice;

import com.sjw.deadlock.bank.Account;

/**
 * 安全转账，通过hashcode 解决动态顺序死锁
 */
public class SafeTransfer implements ITransfer {

    private static Object tieLock = new Object();

    @Override
    public void transfer(Account from, Account to, int amount)
            throws InterruptedException {

        //identityHashCode使用jdk内部的hashcode，不管有没有覆写hashcode方法
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        //规定：先锁小的hashcode的对象
        if(fromHash < toHash){
            synchronized (from){
                System.out.println(Thread.currentThread().getName()+" get "+from.getName()+ "的锁");
                Thread.sleep(100);
                synchronized (to){
                    System.out.println(Thread.currentThread().getName() +" get "+to.getName() + "的锁");
                    from.flyMoney(amount);
                    to.addMoney(amount);
                    System.out.println(from);
                    System.out.println(to);
                }
            }
        //通过if来完成，永远先锁小的hashcode
        }else if(toHash < fromHash){
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+" get "+to.getName()+ "的锁");
                Thread.sleep(100);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName() +" get " + from.getName() + "的锁");
                    from.flyMoney(amount);
                    to.addMoney(amount);
                    System.out.println(from);
                    System.out.println(to);
                }
            }
        //这种情况出现的概率很低（千万分之一），即产生了hash冲突，但是不代表不会出现
        //即2个hashcode相等，再争夺1次 另外1个无关这两个对象的锁
        }else{
            synchronized (tieLock){
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()+" get "+from.getName()+ "的锁");
                    Thread.sleep(100);
                    synchronized (from){
                        System.out.println(Thread.currentThread().getName() +" get "+to.getName() + "的锁");
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }
    }
}
