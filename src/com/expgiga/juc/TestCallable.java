package com.expgiga.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 一、创建线程的4种方式：
 * 1.Thread
 * 2.Runnable
 * 3.Callable
 * 4.ThreadPool
 *
 * 二、实现Callable接口和Runnable的区别：
 *  1.Callable有返回值，抛出异常。
 *  2.一个call()，一个run()
 *
 * 执行Callable方式，需要FutureTask实现类的支持，用于支持运算结果.
 * FutureTask是Future的实现类。
 */
public class TestCallable {
    public static void main(String[] args) {

        ThreadDemo2 td = new ThreadDemo2();

        //执行Callable方式，需要FutureTask实现类的支持，用于支持运算结果.
        FutureTask<Integer> result = new FutureTask<Integer>(td);

        new Thread(result).start();

        //2.接收线程运算后的结果
        try {
            Integer sum = result.get(); //FutureTask也可用于闭锁的操作。CountDownLatch
            System.out.println(sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        int sum = 0;

        for (int i = 0; i <=100; i++) {
            sum += i;
        }

        return sum;
    }
}

class ThreadDemo1 implements Runnable {

    @Override
    public void run() {

    }
}