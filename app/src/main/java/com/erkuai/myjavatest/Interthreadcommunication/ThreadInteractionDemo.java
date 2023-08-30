package com.erkuai.myjavatest.Interthreadcommunication;

import android.os.SystemClock;
import android.util.Log;

import com.erkuai.myjavatest.TestDemo;

public class ThreadInteractionDemo implements TestDemo {
    @Override
    public void runTest() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (!isInterrupted()) {
                        Log.i("wmkwmk", "run: number : " + i);
                    }

                    /**
                     * isInterrupted()  只判断中断状态
                     * Thread.interrupted() 判断中断状态，并且执行之后，将中断置为false
                     *
                     */

                    // 子线程在睡10000，在等待的时间内，子线程是什么也不做的
                    // 外部线程睡1s后打断它，导致结束，所以收到异常，结束，
                    // 在catch代码块里面做收尾工作
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }

                }
            }
        };
        thread.start();

//        thread.stop(); // stop() 方法不可控，暴力结束，并不管运行到什么状态
//        thread.interrupt(); // interrupt() 方法把线程标记为中断状态，不是立即结束，不是强制的，
        //  配合 isInterrupted() 方法，结束线程
        // InterruptedException:如果在线程「等待」时中断，或者在中断状态直接结束等待过程 抛出
        //（因为等待过程什么也不会做，而interrupt() 的目的是让线程做完收尾工作后尽快终结，所以要跳过等待过程）

        // Android: SystemClock.sleep(1000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
