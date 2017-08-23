package com.expgiga.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * TestCopyOnWriteArrayList/CopyOnWriteArraySet:"写入并复制"
 *
 */
public class TestCopyOnWriteArrayList1 {
    public static void main(String[] args) {

        HelloThread1 ht1 = new HelloThread1();

        for (int i = 0; i < 10; i++) {
            new Thread(ht1).start();
        }
    }
}


class HelloThread1 implements Runnable {

    private static List<String> list = Collections.synchronizedList(new ArrayList<String>());

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
            list.add("AA"); //存在并发修改异常
        }
    }
}