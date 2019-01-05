package com.sjw.interrupt;

/**
 * 使用自定义的取消标志位中断线程（不靠谱）
 */
public class FlagCancel {

    private static class TestRunnable implements Runnable{

        private volatile boolean on = true;

        private long i = 0;

        @Override
        public void run() {
            while(on){
                System.out.println(i++);
                /*阻塞方法，on不起作用
                如wait,sleep,阻塞队列中的方法(put,take)
                */

                //这段try/catch可以注释，运行尝试，注释时，运行没什么问题
                //取消注释，出现问题
                /*try {
                    synchronized (this){
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/


            }
            System.out.println("TestRunnable is running :" + i);
        }

        public void cancel(){
            System.out.println("Ready stop thread......");
            on = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestRunnable testRunnable = new TestRunnable();
        Thread t = new Thread(testRunnable);
        t.start();
        Thread.sleep(10);
        testRunnable.cancel();
    }

}
