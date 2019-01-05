package com.sjw;

import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference 原子更新引用（即对象）
 */
public class AtomicRef {

    static AtomicReference<User> userAtomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("sjw",23);
        userAtomicReference.set(user);
        User updateUser = new User("caq",18);
        userAtomicReference.compareAndSet(user,updateUser);
        System.out.println(userAtomicReference.get().getName());
        System.out.println(userAtomicReference.get().getOld());
    }

    static class User{
        private String name;
        private int old;

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
