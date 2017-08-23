package com.expgiga.juc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、i++的原子性问题:i++的操作分为三个步骤："read-update-write"
 *  int i = 10;
 *  i = i++ //10
 *  因为：
 *  int temp;
 *  i = i + 1;
 *  i = temp;
 *
 * 二、原子变量jdk1.5后，java.util.concurrent.atomic包下提供了许多常用的原子变量。
 * 原子变量包含：1. volatile的特性，里面的变量都是volatile，保证内存的可见性
 *             2. CAS（Compare-And-Swap）算法，保证数据的原子性
 *                    CAS算法是硬件对于并发操作共享数据的支持
 *                    CAS包含三个操作数：
 *                    内存值V
 *                    预估值A
 *                    更新值B
 *                    当且仅当V == A时， V = B，否则将不做任何操作
 *
 */
public class TestAtomic {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {

//    private int serialNumber = 0;

    //修改如下：
    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

//    public int getSerialNumber() {
//        return serialNumber++;
//    }

    //相应的修改如下：
    private int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }
}