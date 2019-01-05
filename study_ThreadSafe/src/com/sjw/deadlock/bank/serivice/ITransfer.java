package com.sjw.deadlock.bank.serivice;


import com.sjw.deadlock.bank.Account;

/**
 * 转账接口
 */
public interface ITransfer {

    //参数：转出账户，转入账户，钱的数量
    void transfer(Account from, Account to, int amount) throws InterruptedException;
}
