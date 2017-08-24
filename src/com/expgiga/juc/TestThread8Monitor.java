package com.expgiga.juc;

/**
 * 线程8锁：
 * 需要：判断打印的是"one" or "two"
 *
 * 1.两个普通同步方法，两个线程标准打印 one， two
 * 2.新增Thread.sleep()给getOne 打印one, two
 * 3.新增普通的方法getThree， 打印three, one, two
 * 4.两个普通的同步方法，两个Number对象，打印two, one
 * 5.将getOne变为static同步方法，一个Number对象，打印two, one
 * 6.修改两个方法均为static同步方法，一个Number对象，打印one, two
 * 7.一个static同步方法一个非static同步方法，两个Number对象，打印two, one
 * 8.两个static同步方法，两个Number对象，打印one, two
 *
 * 线程8锁的关键：
 * 1.非静态方法的锁默认为this，静态方法的锁为 对应的Class实例
 * 2.某一个时刻内，只能有一个线程持有锁，无论几个方法。
 */
public class TestThread8Monitor {
    public static void main(String[] args) {

        Number number = new Number();

        Number number2 = new Number();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                number.getTwo();
                number2.getTwo();
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                number.getThree();
//            }
//        }).start();
    }
}

class Number {
    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

//    public void getThree() {
//        System.out.printf("three");
//    }
}