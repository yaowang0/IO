package com.expgiga.juc;

/**
 * volatile与内存可见性
 * 一、内存可见性问题
 * 主存
 * 每个分线程都有自己独立的缓存，对于共享数据彼此不可见。
 *
 * 当多个线程操作共享数据时，彼此是不可见的。
 * volatile关键字：当多个线程操作共享数据时，可以保证内存中的数据可见。
 *
 */
public class TestVolatile {
    public static void main(String[] args) {

        ThreadDemo td = new ThreadDemo();

        new Thread(td).start();

//        while (true) {
//            if (td.isFlag()) { //false! 内存可见性问题
//                System.out.println("-------------------");
//                break;
//            }
//        }

        //解决方法一：synchronized，加锁效率低
        while (true) {
            synchronized (td) {
                if (td.isFlag()) {
                    System.out.println("---------------");
                    break;
                }
            }
        }
    }
}

class ThreadDemo implements Runnable {

//    private boolean flag = false;

    //解决方法二：volatile
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println("flag = " + isFlag());

    }
    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
