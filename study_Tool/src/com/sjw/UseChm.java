package com.sjw;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用ConcurrentHashMap
 */
public class UseChm {

    HashMap<String,String> hashMap = new HashMap<>();
    ConcurrentHashMap<String,String> chm = new ConcurrentHashMap<>();

    //对key相同的对象，我们知道HashMap会覆盖原来的value
    //因此写一个方法：key存在时返回原值，key不存在时添加
    public String putIfAbsent(String key,String value){

        //目前hashMap也有了putIfAbsent方法了
        synchronized (hashMap){
            if(hashMap.get(key) == null){
                return hashMap.put(key,value);
            }else{
                return hashMap.get(key);
            }
        }
    }

    //ConcurrentHashMap一个方法就搞定了
    public String useChm(String key,String value){
        //独有putIfAbsent方法
        return chm.putIfAbsent(key,value);
    }

}
