package com.sjw;

import com.sjw.threadstate.SleepUtils;

/**
 * 守护线程
 * 对于try/finally里的代码可能不会执行
 *
 */
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner());
        //通过这个方法变成守护线程
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(100);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
