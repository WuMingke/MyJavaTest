package com.erkuai.myjavatest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.erkuai.myjavatest.Interthreadcommunication.ThreadInteractionDemo;
import com.erkuai.myjavatest.Interthreadcommunication.WaitDemo;
import com.erkuai.myjavatest.androidmutil.CustomizableThreadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
//        syncDemo1();
//        syncDemo2();
//        syncDemo3();
        /*****以下 Java线程间通信 3种 ******/
        // 1 一个线程开启另一个线程
        // 2
        // 3
//        runThreadInteractionDemo(); // 一个线程结束另一个线程,通过抛异常实现
//        runWaitDemo(); // 两个线程互相配合工作，wait/notify、join、yield

        /*****以下 Android多线程******/
        runCustomizableThreadDemo();

//        runHandler();

//        ThreadLocal , 数据不共享，Looper设置在ThreadLocal里的
//        Looper.prepare(); // 当前线程的ThreadLocal设置Looper
//        Looper.myLooper(); // 从当前线程的ThreadLocal获取Looper
//        Looper.loop(); // 开启循环
//        线程之间的交互，是不存在真正的交流的，只能协同使用那些共享资源
//        在不循环的线程里面插入任务到指定的不同的线程里面去做，是不可能的
        // TODO: 2022/12/29 mingKE
//        （ThreadLocal线程不安全？

        // TODO: 2022/12/29 mingKE
        // 了解ReentrantLock、ReentrantReadWriteLock的实现
        // TODO: 2022/12/29 mingKE
        // volatile 和 synchronized 的实现原理


//        AsyncTask 内存泄漏
        // 被GC-root直接或间接引用的对象，不会被回收
        // GC-root 其中一种就是 运行中的线程（静态对象、native对象）
        // 本质上内存泄漏一小会儿是没关系的，GC它可能还晚来呢。LeakCanary的检测也是在多少秒之内的检测

        /**
         *
         *         Executors;
         *         AsyncTask;
         *         HandlerThread;
         *         IntentService;
         *
         *         选哪个？
         *         Executors
         *
         *         Executors的关闭：executor.shutdown();
         *         HandlerThread的关闭：handlerThread.quit(); // 这个其实就是停止 Looper 的循环
         */

        // IntentService 和 Service
        // Service：后台任务的活动空间。适用场景：音乐播放器等。
        // IntentService：执行单个任务后自动关闭的 Service

    }

    static void runHandler() {
        Handler handler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i("wmkwmk", "run: thread:" + Thread.currentThread().getName());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("wmkwmk", "run: handler:" + Thread.currentThread().getName());
                    }
                });
            }
        };
        thread.start();
    }

    static void runThreadInteractionDemo() {
        new ThreadInteractionDemo().runTest();
    }

    static void runWaitDemo() {
        new WaitDemo().runTest();
    }

    static void runCustomizableThreadDemo() {
        CustomizableThreadDemo threadDemo = new CustomizableThreadDemo();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        threadDemo.setTask(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("wmkwmk", "run: hahaha");
//            }
//        });
        threadDemo.start();
    }

    static void syncDemo3() {
        new Sync3().runTest();
    }

    static void syncDemo2() {
        new Sync2().runTest();
    }

    static void syncDemo1() {
        new Sync1().runTest();
    }

    static void callable() { // 有返回值的 Runnable
        Callable<String> callable = new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "Down!!";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> submit = executor.submit(callable);

        try {
            String s = submit.get(); // 这里是阻塞式地获取，api只能做到这里了，要想非阻塞，用循环 while (submit.isDone()){}
            Log.i("wmkwmk", "callable: 返回：" + s);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static void executor() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Log.i("wmkwmk", "run: executor runnable run");
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

//        executor.shutdown();  // 保守结束 executor，不允许有新的任务，但是在排队中的任务依然可以执行
//        executor.shutdownNow(); // 马上 interrupt() 所有线程

        // 适用于瞬时处理多个任务，爆发性的，快速使用快速回收，处理完回收线程
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
//        Runnable processBitmap = new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO: 处理
//            }
//        };
//        List<Bitmap> bitmaps = new ArrayList<>();
//        for (int i = 0; i < bitmaps.size(); i++) {
//            fixedThreadPool.execute(processBitmap);
//        }
//        fixedThreadPool.shutdown();
    }

    static void threadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) { // 工厂方法
                return new Thread(r, "Thread-" + count.incrementAndGet());
            }
        };

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Log.i("wmkwmk", "run: " + Thread.currentThread().getName() + "  start");
            }
        };

        Thread thread = threadFactory.newThread(runnable);
        thread.start();
        Thread thread1 = threadFactory.newThread(runnable);
        thread1.start();
    }

    static void runnable() { // 主要是方便重用 runnable
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Log.i("wmkwmk", "run: runnable run");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i("wmkwmk", "run: Thread.start");
            }
        };
        thread.start();
        // Java代码指挥虚拟机开了一个新的线程，在新的线程里面去执行了run方法，至于run方法是怎么切换到另一个线程里面去执行的，
        // 是虚拟机指挥操作系统来实现的，不同的操作系统切换线程的方式不一样，我们控制不了，也没有必要知道。Java语言本身是没有切换
        // 线程的能力的，必须交给操作系统来完成，
        // 进程和线程
        // 首先，进程大于线程，线程运行在进程里面；进程是操作系统上面的一块独立区域，每一个进程都是独立运行的，数据不共享。
        // 一个进程可以有多条线路共同做事，这些线路就是线程，线程之间共享资源。
        // 类似的比喻：家（进程）和家庭成员（线程）
        //
        // CPU线程和操作系统线程
        // 平时开发说的线程，都是指操作系统线程
        // "8核CPU"，指的是CPU在硬件级别定义的同一时间可以做8件事
        // "CPU4核8线程"，指的是通过技术手段，将一个核运行出两个CPU线程，
        // 操作系统线程是用分片的形式，将CPU线程进一步拆分，相当于在操作系统内部，模拟了CPU线程，
        // 线程，其实就是代码按序执行下来的线路，代码执行完了，线程就结束了


    }
}