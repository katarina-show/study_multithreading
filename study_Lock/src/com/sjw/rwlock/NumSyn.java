package com.sjw.rwlock;

/**
 * synchronized完成读多写少的场景
 */
public class NumSyn implements IGoodsNum {

    private GoodsVo goods;

    public NumSyn(GoodsVo goods) {
        this.goods = goods;
    }


    @Override
    public synchronized GoodsVo getGoodsNumber() {
        try {
            //读操作耗时5ms，在读写锁中我们耗时也一样
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.goods;
    }

    @Override
    public synchronized void setGoodsNumber(int changeNumber) {

        try {
            //写操作耗时50ms，在读写锁中我们耗时也一样
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.goods.setGoodsVoNumber(changeNumber);

    }
}
