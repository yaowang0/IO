package com.expgiga.juc;

/**
 * HashTable是线程安全的(效率低)，HashMap是不安全的。
 * HashMap可以通过Collections.synchronizedMap()转化成线程安全的，实质是在每一个方法中添加了synchronized。
 * HashTable和HashMap底层是基本一样的。
 * 多线程访问HashTable，并行转为了串行，并且存在符合操作问题，比如："若存在则添加"，"若存在则删除"
 *
 * ConcurrentHashMap提供了线程安全的HashMap,
 * ConcurrentHashMap采用了"锁分段机制"：concurrentLevel为16，默认有16个段Segments,每个Segment，默认有16个表，每个表存在链表
 * 每个段是独立的锁。
 *
 * JDK8，将分段锁也变成了CAS算法了。
 *
 * ConcurrentHashMap优于同步的HashMap
 * ConcurrentSkipListMap优于同步的TreeMap
 * CopyOnWriteArrayList优于同步的ArrayList
 *
 */
public class TestConcurrentHashMap {
    public static void main(String[] args) {

    }
}
