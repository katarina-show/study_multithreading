package com.sjw.waitnotify;

/**
 *测试wait notify notifyAll方法
 */
public class TestUser {

    //对同一个对象操作
    private static User user = new User(30,User.CITY);

    private static class CheckAge extends Thread{
        @Override
        public void run() {
            user.waitAge();
        }
    }

    private static class CheckCity extends Thread{
        @Override
        public void run() {
            user.waitCity();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 3; i++){
            //启动三个等待用户年龄变化的线程
            new CheckAge().start();
        }
        for(int i = 0; i < 3; i++){
            //启动三个等待用户城市变化的线程
            new CheckCity().start();
        }
        Thread.sleep(1000);
        user.changeCity();//变动用户的城市
    }

}
