package com.expgiga.java;

import org.testng.annotations.Test;

import java.io.*;

/*
 * 1.流的分类
 * 按照流向的不同：输入流和输出流
 * 按照处理数据的单位不同：字节流和字符流(处理的是文本文件)
 * 按照角色的不同：节点流(直接作用在文件上的)和处理流
 *
 * 2.IO流的体系：
 * 抽象基类         节点流(文件流)         缓冲流(处理流的一种，用来加速节点流处理速度)
 * InputStream     FileInputStream       BufferedInputStream
 * OutputStream    FileOutputStream      BufferedOutputStream
 * Reader          FileReader            BufferedReader
 * Writer          FileWriter            BufferedWriter
 *
 * 流的分类：
 * 按操作数据单位的不同分为：字节流(8 bit)，字符流(16 bit)
 * 按流的角色的不同分为：节点流 处理流
 *
 * 节点流：是最基本的，直接是作用在文件上的。
 * FileInputStream FileOutputStream (字节流)
 * FileReader FileWriter (字符流)
 *
 * 处理流：
 * 转换流：将字节流转换为字符流InputStreamReader OutputStreamWriter
 * 缓冲流：BufferedInputStream BufferedOutputStream BufferedReader BufferedWriter
 *
 * Java的IO流共涉及40多个类，实际上非常的规则，都是从这四个抽象基类派生出来的。
 * 输入流 InputStream(字节流)  Reader(字符流)
 * 输出流 OutputStream(字节流) Writer(字符流)
 *
 */
public class TestFileInputOutputStream {
    //从硬盘存在的一个文件中读取其内容到程序中，使用FileInputStream
    @Test
    public void testFileInputStream1() throws IOException {
        //1.创建一个File类的对象
        File file = new File("hello.txt");
        //2.创建一个FileInputStream类的对象
        FileInputStream fis = new FileInputStream(file);
        //3.调用FileInputStream的方法，实现File文件的读取
        /*
         * read()：读取文件的一个字节，当执行到文件的结尾时，返回-1
         */
//        int b = fis.read();
//        while (-1 != b) {
//            System.out.print(b);//ASCII
//            System.out.print((char) b);
//            b = fis.read();
//        }
        int b;
        while ((b = fis.read()) != -1) {
            System.out.print((char) b);
        }
        //4.关闭相应的流
        fis.close();
    }

    //使用try-catch的方式处理如下的异常，更合理：保证流的关闭操作，一定能够执行
    @Test
    public void testFileInputStream2() {
        FileInputStream fis = null;
        try {
            //1.创建一个File类的对象
            File file = new File("hello.txt");
            //2.创建一个FileInputStream类的对象
            fis = new FileInputStream(file);
            //3.调用FileInputStream的方法，实现File文件的读取
            int b;
            while ((b = fis.read()) != -1) {
                System.out.print((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭相应的流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //使用byte[]
    @Test
    public void testFileInputStream3() {
        FileInputStream fis = null;
        try {
            File file = new File("hello.txt");
            fis = new FileInputStream(file);
            byte[] b = new byte[20]; //要读取到的数据写入数组
            int len; //每次读入到byte中的字节的长度
            while ((len = fis.read(b)) != -1) {
//                for (int i = 0; i < len; i++) { //注意，此处不是b.length！
//                    System.out.print((char) b[i]);
//                }
                String str = new String(b, 0, len);//注意，此处不是b.length！
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //FileOutputStream
    @Test
    public void testFileOutputStream() {
        FileOutputStream fos = null;
        try {
            //1.创建一个File对象，表明要写入的文件位置
            File file = new File("Hello2.txt");
            //2.创建一个FileOutputStream的对象，将file的对象作为参数传递给FileOutputStream的构造函器中
            fos = new FileOutputStream(file);
            //3.写入操作
            fos.write("Hello World!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    //4.关闭输出流
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //FileInputStream->FileOutputStream从硬盘读取文件复制
    @Test
    public void testFileInputStreamOutputStream() {
        //2.相应的流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //1.读入和写出的文件
            File file1 = new File("hello.txt");
            File file2 = new File("hello3.txt");

            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);

            //3.实现文件的复制
            byte[] b = new byte[10];
            int len;
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);//注意不是：fos.write(b),fos.write(b. 0, b.length)
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
