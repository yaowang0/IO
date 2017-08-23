package com.expgiga.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TestCopyOnWriteArrayList/CopyOnWriteArraySet:"写入并复制"
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代多时，可以选择，提高效率。
 */
public class TestCopyOnWriteArrayList2 {
    public static void main(String[] args) {

        HelloThread2 ht2 = new HelloThread2();

        for (int i = 0; i < 10; i++) {
            new Thread(ht2).start();
        }
    }
}


class HelloThread2 implements Runnable {

//    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

    //添加操作多时，不适合用CopyOnWriteArrayList，它适合用于迭代操作
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();


    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
    }

    @Override
    public void run() {
        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("AA");
        }
    }
}