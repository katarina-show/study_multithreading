package com.sjw.tooluse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 线程间交换数据，有点类似管道流交换数据，也是仅限于2个线程之间交换
 */
public class ExchangeCase {

    static final Exchanger<List<String>> exchanger = new Exchanger<>();

    public static void main(String[] args) {


        //2个线程往各自的list里添加数据，最后交换
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(Thread.currentThread().getId()+" insert A1");
                    list.add(Thread.currentThread().getId()+" insert A2");
                    list = exchanger.exchange(list);//交换数据，阻塞在这里
                    for(String item:list){
                        System.out.println(Thread.currentThread().getId()+"------------"+item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> list = new ArrayList<>();
                    list.add(Thread.currentThread().getId()+" insert B1");
                    list.add(Thread.currentThread().getId()+" insert B2");
                    list.add(Thread.currentThread().getId()+" insert B3");
                    System.out.println(Thread.currentThread().getId()+" will sleep");
                    Thread.sleep(1500);
                    list = exchanger.exchange(list);//交换数据
                    for(String item:list){
                        System.out.println(Thread.currentThread().getId()+"--------------"+item);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
