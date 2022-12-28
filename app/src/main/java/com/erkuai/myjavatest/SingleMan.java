package com.erkuai.myjavatest;

class SingleMan {

    private volatile static SingleMan singleMan;

    private SingleMan() {
    }

    public static SingleMan newInstance() {
        if (singleMan == null) {
            synchronized (SingleMan.class) {
                if (singleMan == null) {
                    singleMan = new SingleMan();
                }
            }
        }
        return singleMan;
    }

    /**
     *
     * volatile 用在这里，主要是保证对象的初始化彻底完成，因为 new SingleMan() 其实是一个比较复杂的操作，
     * 即便是它很简单，对象创建出来了，标记可用了，但是里面的变量还没有赋值，这时候拿来用，是会出问题的，
     * volatile 就解决了这个问题，使对象彻底初始化完成，再标记可用
     *
     * 其它的判空操作，都可以用线程安全来解释，就是两个线程同时操作的时候，判断只创建一个对象
     */
}
