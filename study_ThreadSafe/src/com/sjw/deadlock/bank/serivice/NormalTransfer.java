package com.sjw.deadlock.bank.serivice;

import com.sjw.deadlock.bank.Account;

/**
 * 普通转账  出现动态顺序死锁
 */
public class NormalTransfer implements ITransfer{

    @Override
    public void transfer(Account from, Account to, int amount)
            throws InterruptedException {
        //先锁转出账户，再锁转入账户
        synchronized (from){
            System.out.println(Thread.currentThread().getName()+" get "+from.getName()+ "的锁");
            Thread.sleep(100);
            synchronized (to){
                System.out.println(Thread.currentThread().getName() +" get "+to.getName()+ "的锁");
                from.flyMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
