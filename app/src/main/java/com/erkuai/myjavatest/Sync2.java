package com.erkuai.myjavatest;

import android.util.Log;

class Sync2 implements TestDemo {

    private int x = 0;

    private synchronized void count() {
        x++;
    }

    @Override
    public void runTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                Log.i("wmkwmk", "run:1 final x :" + x);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                Log.i("wmkwmk", "run:2 final x :" + x);
            }
        }).start();
    }

    /**
     *  理论上，有一个输出应该是 200w
     *
     *  这里对 x 加 volatile 修饰是没有用的，因为 x++ 不是一个原子操作
     *
     *  这里对方法加 synchronized 修饰，使其具有原子性，它在被访问的时候，其它线程不能再访问
     *
     *  AtomicInteger ：对Int的包装，使Int具有原子性和同步性
     *      所以 volatile 修饰的变量，可以用这些Atomic类替换
     *
     *
     *
     *
     *
     */
}
