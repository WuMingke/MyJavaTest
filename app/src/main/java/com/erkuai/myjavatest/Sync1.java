package com.erkuai.myjavatest;

import android.util.Log;

class Sync1 implements TestDemo {

    private volatile boolean running = true;

    private void stop() {
        running = false; // 主线程赋值
    }

    @Override
    public void runTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int round = 1;
                while (running){ // 子线程取值
                    Log.i("wmkwmk", "run: ---");
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stop();
    }
    /**
     * A线程        主线程        B线程
     *
     * A线程想要修改主线程中的一个值，是把主线程中的这个值复制到自己的内存中来，做完修改后，
     * 再复制到主线程去，这样主要是为了提高效率
     * volatile 同步性，
     * 1、只从主存中读取数据，
     * 2、线程修改完数据之后，立马同步到主内存，
     * 3、每次修改前，都检查主存的数据是否被修改，工作效率降低，但是安全性提高
     *
     * 多个线程 操作同一个变量的场景
     *
     *
     *
     *
     *
     *
     */
}
