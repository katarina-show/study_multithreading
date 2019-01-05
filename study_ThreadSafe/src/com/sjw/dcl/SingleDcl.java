package com.sjw.dcl;

/**
 * 懒汉式单例-双重检查
 */
public class SingleDcl {

    private volatile static SingleDcl single;

    private SingleDcl(){

    }

    public static SingleDcl getInstance(){
        //双重检查，即2次if判断
        if(single == null){
            synchronized (SingleDcl.class){
                if(single == null){
                    single = new SingleDcl();
                }
            }
        }
        return single;
    }


}
