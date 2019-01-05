package com.sjw;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决方式
 * 通过判断时加入版本号stamp来实现的
 **/
public class AtomicStampedReferenceTest {

    static AtomicStampedReference<Integer> atomicStampedReference =
            new AtomicStampedReference(0,0);

    public static void main(String[] args) throws InterruptedException {
        final int stamp = atomicStampedReference.getStamp();
        final Integer reference = atomicStampedReference.getReference();
        System.out.println(reference + "==================" + stamp);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId() + ":"
                        + reference + "---" + stamp + "---"
                        + atomicStampedReference.compareAndSet(reference,
                        reference + 10,stamp,stamp + 1));
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //t2想把10再加10即改为20，并且该线程认为版本号是0.想改为1
                //但是CAS发现版本号已经为1，因此更新失败
                Integer reference = atomicStampedReference.getReference();
                System.out.println(Thread.currentThread().getId() + ":"
                        + reference + "---" + stamp + "---"
                        + atomicStampedReference.compareAndSet(reference,
                        reference + 10,stamp,stamp + 1));
            }
        });

        t1.start();
        t1.join();
        //t1跑完了，t2再开始运行
        t2.start();
        t2.join();

        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());


    }

}
