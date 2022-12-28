package com.erkuai.myjavatest;

import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteDemo implements TestDemo {

//    private final ReentrantLock lock = new ReentrantLock();

    private int x = 0;


    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private void count() {
        writeLock.lock(); // 加锁
        try {
            x++;
        } finally {
            writeLock.unlock(); // 解锁
        }
    }

    private void print() {
        readLock.lock();
        try {
            Log.i("wmkwmk", "print: x = " + x);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void runTest() {

    }

    /**
     * 将锁拆分成读锁和写锁，使得读写更加灵活
     */
}
