package com.sjw.rwlock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 测试读写锁和synchronized的性能
 */
public class Test {

    //读多写少的场景，定义30个读线程，3个写线程
    static final int threadRatio = 10;

    static final int threadBaseCount = 3;

    //发令枪
    static CountDownLatch countDownLatch= new CountDownLatch(1);

    //模拟实际的数据库读操作
    private static class ReadThread implements Runnable{

        private IGoodsNum goodsNum;

        public ReadThread(IGoodsNum goodsNum) {
            this.goodsNum = goodsNum;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            //每个线程执行100次读操作
            for(int i = 0; i < 100; i++){
                goodsNum.getGoodsNumber();
            }
            long duration = System.currentTimeMillis()-start;
            System.out.println(Thread.currentThread().getName() + "读取库存数据耗时：" + duration + "ms");

        }
    }


    //模拟实际的数据库写操作
    private static class WriteThread implements Runnable{

        private IGoodsNum goodsNum;

        public WriteThread(IGoodsNum goodsNum) {
            this.goodsNum = goodsNum;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            //随机模拟生成1个ID
            Random r = new Random();
            //每个线程执行10次写操作
            for(int i = 0; i < 10; i++){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                goodsNum.setGoodsNumber(r.nextInt(10));
            }
            long duration = System.currentTimeMillis()-start;
            System.out.println(Thread.currentThread().getName() + "写库存数据耗时：" + duration + "ms");

        }
    }

    public static void main(String[] args) {

        GoodsVo goodsVo = new GoodsVo("goods001",100000,10000);


        //使用synchronized
        //IGoodsNum goodsNum = new NumSyn(goodsVo);

        //使用读写锁，性能会翻好几倍
        IGoodsNum goodsNum = new RwNumImpl(goodsVo);


        for(int i = 0; i < threadBaseCount * threadRatio; i++){
            Thread readT = new Thread(new ReadThread(goodsNum));
            readT.start();
        }


        for(int i = 0; i < threadBaseCount; i++){
            Thread writeT = new Thread(new WriteThread(goodsNum));
            writeT.start();
        }

        //发令枪响，所有线程一起运行
        countDownLatch.countDown();

    }

}
