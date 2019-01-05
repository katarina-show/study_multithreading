package com.sjw;

/**
 * Run和start方法辨析
 */
public class RunAndStart {

    private static class TestThread extends Thread{

        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        //run就是一个普通的方法，跟其他类的实例方法没有任何区别
        @Override
        public void run() {
            int i = 90;
            while(i > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I am " + name + " i = " + i);
            }


        }
    }

    public static void main(String[] args) {
//        TestThread beInvoked = new TestThread("beInvoked_thread");
//        beInvoked.run();

        TestThread parent = new TestThread("beInvoked");
        parent.setName("beInvoked_thread");
        parent.start();

    }

}
