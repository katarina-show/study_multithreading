package com.sjw.syn;

/**
 * javap查看字节码
 * 会发现monitorenter monitorexit
 */
public class SynDetail {
    public static void main(String[] args) {
        synchronized (SynDetail.class){
            //do my work

        }

    }
}
