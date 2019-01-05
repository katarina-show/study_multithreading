package com.sjw.atomicarray;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray 原子更新数组
 */
public class AtomicArray {

    static int[] value = new int[]{1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        //第一个参数是下标，第二个是新值
        ai.getAndSet(0,3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);

        //会发现get(0)和value[0]的值不同，类会将数组复制一份，原数组不会发生变化
    }

}
