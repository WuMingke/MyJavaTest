package com.erkuai.myjavatest;

class Sync3 implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name = "";

    private void count(int newValue) {
        x = newValue;
        y = newValue;
    }

    private void setName(String newName) {
        this.name = newName;
    }

    @Override
    public void runTest() {
        // 假如多线程对 count() setName() 都有调用

    }

    /**
     * synchronized 保护数据的互斥访问，当对方法加上修饰时，它是对方法加上一个监视器（monitor），
     * 让这个monitor看着，当方法已经被访问的时候，monitor不让另外的线程访问
     *
     * 假如给 count、setName 方法加上 synchronized 修饰，那么它们是共用同一个monitor，
     * 即是一个线程访问count，那么另一个线程也不能访问setName
     *
     * 如果想单独控制，那么可以使用synchronized代码块:
     *     private void setName(String newName) {
     *         synchronized (this) {
     *             this.name = newName;
     *         }
     *     }
     *     和
     *     private synchronized void setName(String newName) {
     *         this.name = newName;
     *     }
     *     是等价的
     * synchronized 加在方法上，monitor默认是 方法所有在的对象
     * 这里的 this 参数是指定 monitor 对象
     *
     *  同时，如果 synchronized 是修饰静态（static）方法，那么 monitor 也需要是静态的，
     *  可以是static修饰的变量，也可是 xx.class
     */

    /**
     *
     * 死锁 ：多重锁的场景喜爱，发生竞争等待
     *
     * 乐观锁、悲观锁：在Android开发的场景下，一般不会出现这类问题，都是数据库管理相关的思想
     * 乐观锁，就是乐观并发控制，它的点是在于不加锁。比如在数据库读写的场景下，我读数据，修改，再写入数据库，
     * 这时候问题发生读之后，写之前，别的线程已经修改了数据，那么我们写数据进去，就是错误的数据，这时候应该重新读写一遍
     * 读的时候不加锁，写的时候加锁
     * 悲观锁，悲观并发控制，读的时候就加锁，别的线程不允许读不允许写，等我操作完，再释放锁，让别的线程操作
     */
}
