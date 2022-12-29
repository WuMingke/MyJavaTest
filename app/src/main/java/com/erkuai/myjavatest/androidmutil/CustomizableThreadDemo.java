package com.erkuai.myjavatest.androidmutil;

import android.os.HandlerThread;


public class CustomizableThreadDemo extends Thread {
    private final Looper looper = new Looper();

    @Override
    public void run() {
        super.run();
//        while (!quit) {
//            synchronized (this) {
//                if (task != null) {
//                    task.run();
//                    task = null;
//                }
//            }
//        }
        looper.loop();
    }

//    1以上，HandlerThread 的雏形。（HandlerThread可以看作一个壳，各种操作都是由Looper管理的）

    // 2再将run方法内部抽象成Looper
    static class Looper {

        private Runnable task;
        private volatile boolean quit;

        public synchronized void setTask(Runnable task) {
            this.task = task;
        }

        public void quit() {
            quit = true;
        }

        void loop() {
            while (!quit) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }
    }

    // 3再将Looper拆出来，交给Handler处理

}
