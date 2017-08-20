package com.expgiga.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 一、缓冲区(Buffer)：在Java NIO负责数据存取。缓冲区就是数组，用于存储不同数据类型的数据。
 *
 * 根据数据类型不同(boolean除外)，提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 这些缓冲区的管理方式几乎一样，通过allocate()获取缓冲区。
 *
 * 二、缓冲区存储数据的两个核心方法：
 * put()：存入数据到缓冲区
 * get()：获取缓冲区的数据
 *
 * 三、缓冲区的四个核心属性
 * (1)capacity：表示缓冲区中的最大存储数据的容量，一旦声明不能改变
 * (2)limit：表示缓冲区中可以操作数据的大小。(limit后面的数据是不能进行读写的)
 * (3)position：表示缓冲区中正在操作数据的位置
 * (4)mark：标记，表示记录当前position的位置，可以通过reset()恢复到mark的位置
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * 四、直接缓冲区与非直接缓冲区
 * 非直接缓冲区：通过allocate()方法分配的缓冲区，将缓冲区建立在JVM的内存中
 * 直接缓冲区：通过allocateDirect()方法分配的缓冲区，将缓冲区建立在物理内存中，可以提高效率。
 *
 * 非直接缓冲区：
 * 应用程序->write()->用户地址空间->copy->内核地址空间->物理磁盘
 * ->read()->内核地址空间->copy->用户地址空间->read()->应用程序
 *
 * 直接缓冲区：无copy过程，存在物理内存映射文件
 * 物理内存映射文件由操作系统管理
 * 也存在问题：
 * 1.分配和销毁开销较大；
 * 2.用户不易控制；
 * 3.
 */
public class TestBuffer {

    @Test
    public void test1() {
        String str = "abcde";

        //1.分配一个指定大小的缓冲区:allocate()
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-----------allocate()-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2.利用put()存入数据到缓冲区中 ->进入写数据模式，此时position和之前改变，limit和capacity不变
        buf.put(str.getBytes());
        System.out.println("-----------put()-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3.利用flip()方法进入读模式，此时position，limit改变，capacity改变
        buf.flip();
        System.out.println("-----------flip()-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4.利用get()方法读取数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("-----------get()-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5.rewind(),此时回到读模式，可重复读数据
        buf.rewind();
        System.out.println("-----------rewind()-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6.clear()，清空缓冲区，回到最初状态，里面的数据没有被清空，缓冲区的数据依然存在，处在被遗忘状态，只是指针回到了最初的状态。
        buf.clear();
        System.out.println("-----------clear()-----------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println((char) buf.get());//数据依然存在
    }

    @Test
    public void test2() {
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2)); //ab，此时position为2
        System.out.println(buf.position());

        //mark标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));//cd，此时position为4
        System.out.println(buf.position());

        //reset()：恢复到mark的位置
        buf.reset();
        System.out.println(buf.position());

        //判断缓冲区中是否还有剩余的数据
        if (buf.hasRemaining()) {
            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void test3() {
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect()); //判断是否是直接缓冲区
    }
}
