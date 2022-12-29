package com.erkuai.myjavatest.Interthreadcommunication;

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

                }
            }
        };
        thread.start();

//        thread.stop(); // stop() 方法不可控，暴力结束，并不管运行到什么状态
//        thread.interrupt(); // interrupt() 方法把线程标记为中断状态，不是立即结束，不是强制的，
                                //  配合 isInterrupted() 方法，结束线程
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
