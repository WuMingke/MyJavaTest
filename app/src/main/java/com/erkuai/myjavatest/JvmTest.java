package com.erkuai.myjavatest;

import java.util.List;

public class JvmTest {

    Object lock = new Object();


    void monitor() {
        synchronized (lock) {
            fun();
        }
    }

    void fun() {

    }

    synchronized void fun1() {
    }

    static synchronized void fun2() {
    }
}
