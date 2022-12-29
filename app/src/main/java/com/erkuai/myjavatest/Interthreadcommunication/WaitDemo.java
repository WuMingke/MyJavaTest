package com.erkuai.myjavatest.Interthreadcommunication;

import android.text.TextUtils;
import android.util.Log;

import com.erkuai.myjavatest.TestDemo;

public class WaitDemo implements TestDemo {

    private String sharedString = "";

    private synchronized void initString() {
        sharedString = "wmkwmk";
//        notify();
        notifyAll(); // 将等待区域的所有线程唤醒，排队拿锁
    }

    private synchronized void printString() {
        while (TextUtils.isEmpty(sharedString)){
            try {
                wait(); // 释放锁，进入等待区域，否则的话这个线程一直占用锁，initString也访问不到
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("wmkwmk", "content: " + sharedString);
    }

    @Override
    public void runTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printString();
            }
        };
        thread.start();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                yield(); 将时间片短暂地让给与自己同优先级的线程，在线程内使用
                initString();
            }
        };
        thread1.start();

        // join() 方法 将调用的线程插入到当前线程的前面，先执行完，再执行当前线程
        try {
            thread.join(); //
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
