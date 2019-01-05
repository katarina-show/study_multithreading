package com.sjw.aim;

/**
 *
 * 用sleep模拟实际的业务操作
 */
public class BusiMock {

    public static void buisness(int sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
