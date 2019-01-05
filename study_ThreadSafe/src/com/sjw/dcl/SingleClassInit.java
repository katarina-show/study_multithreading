package com.sjw.dcl;

/**
 * 延迟类占位符
 */
public class SingleClassInit {

    private String name;

    private SingleClassInit(){

    }

    private static class InstanceHolder{
        public static SingleClassInit instance = new SingleClassInit();
    }

    public static SingleClassInit getInstance(){
        return InstanceHolder.instance;
    }


}


