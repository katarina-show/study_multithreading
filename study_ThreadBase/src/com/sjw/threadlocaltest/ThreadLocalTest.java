package com.sjw.threadlocaltest;

/**
 * ThreadLocal 本质上是一个map
 * 以空间换时间
 */
public class ThreadLocalTest {

    //初始化
    static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "init";
        }
    };

    //创建3个线程并执行
    public void test(){
        Thread[] runs = new Thread[3];
        for(int i = 0; i < runs.length; i++){
            runs[i] = new Thread(new T1(i));
        }


        for(int i = 0; i < runs.length; i++){
            runs[i].start();
        }
    }

    private static class T1 implements Runnable{

        private int id;

        public T1(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId()+" start");
            //取出本线程里的值
            String s = threadLocal.get();
            s = s+"_" + id;
            //更新1个新值
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getId() + "---" + s);
        }
    }

    public static void main(String[] args) {
        ThreadLocalTest test = new ThreadLocalTest();
        test.test();
    }
}
