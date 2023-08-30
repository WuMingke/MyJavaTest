package com.erkuai.myjavatest.Interthreadcommunication;

import android.text.TextUtils;
import android.util.Log;

import com.erkuai.myjavatest.TestDemo;

public class WaitDemo implements TestDemo {

    private String sharedString = "";

    private synchronized void initString() {
        sharedString = "wmkwmk";
//        notify(); // 唤醒一个，但是如果有多个线程排队，这里是不安全的，所以用notifyAll
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

    // wait 和 notifyAll 必须成对使用，而且必须是同一个monitor

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

        // join() 方法 将调用的线程插入到 当前线程 的前面，先执行完，再执行当前线程
        // 它在哪个线程里面执行，哪个线程就是当前线程，调用它的线程会在当前线程前执行
        try {
            thread.join(); // 把两个并行的线程，变成了先后关系
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
