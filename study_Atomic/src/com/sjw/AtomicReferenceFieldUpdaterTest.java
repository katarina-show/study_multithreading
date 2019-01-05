package com.sjw;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater  原子方式更新1个对象的某1个字段
 * AtomicIntegerFieldUpdater    原子方式更新1个对象的某1个Integer类型的字段
 * AtomicLongFieldUpdater 。。。。。。
 **/
public class AtomicReferenceFieldUpdaterTest {

    public static void main(String[] args) {
        //参数1：更新哪个类 参数2：要更新的字段的引用类型 参数3：更新的字段名
        AtomicReferenceFieldUpdater nameUpdater = AtomicReferenceFieldUpdater
                .newUpdater(User.class,String.class,"name");

        //AtomicIntegerFieldUpdater功能类似,和上面比唯一的区别就是确定了更新的字段类型是Integer
        //方法和AtomicInteger差不多
        AtomicIntegerFieldUpdater ageUpdater = AtomicIntegerFieldUpdater
                .newUpdater(User.class,"old");

        User user = new User("sjw",23);
        System.out.println("User name:" + user.name);

        nameUpdater.compareAndSet(user,user.name,"caq");
        System.out.println("Now user name:" + user.name);
        System.out.println("User old age:" + ageUpdater.getAndIncrement(user));
        System.out.println("Now user age:" + user.old);
    }



    static class User{
        //必须声明成public volatile的（这还有人用吗？）
        public volatile String name;
        public volatile int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
