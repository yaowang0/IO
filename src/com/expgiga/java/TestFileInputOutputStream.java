package com.expgiga.java;

import java.io.*;

/**
 * 流的分类：
 * 按操作数据单位的不同分为：字节流(8 bit)，字符流(16 bit)
 * 按流的角色的不同分为：节点流 处理流
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
 * 1.流的分类
 * 按照流向的不同：输入流和输出流
 * 按照处理数据的单位不同：字节流和字符流(处理的是文本文件)
 * 按照角色的不同：节点流(直接作用在文件上的)和处理流
 *
 * 2.IO流的体系：
 * 抽象基类         节点流(文件流)
 * InputStream     FileInputStream
 * OutputStream    FileOutputStream
 * Reader          FileReader
 * Writer          FileWriter
 */
public class TestFileInputOutputStream {
//    public static void main(String[] args) throws IOException {
//
//        /*
//         * FileInputStream
//         * 从硬盘存在的一个文件读取其内容到程序中，使用FileInputStream
//         * 要读取的文件一定要存在，否则要抛出FileNotFoundException
//         */
//        //1.创建一个File类的对象
//        File file1 = new File("hello.txt");
//        //2.创建一个FileInputStream的对象
//        FileInputStream fis = new FileInputStream(file1);
//        //3.调用FileInputStream的方法，实现File文件的读取
//        /*
//         * read()读取文件的一个字节，当执行到文件的结尾时，返回-1
//         */
////        int b = fis.read();
////        while(b != -1) {
////            System.out.println((char)b);
////            b = fis.read();
////        }
//        int b;
//        while ((b = fis.read()) != -1) {
//            System.out.println((char) b);
//        }
//        //4.关闭相应的流
//        fis.close();
//    }

    //使用try-catch的方式更合理：保证流能关闭。
    public static void main(String[] args) throws IOException {
        //1.创建一个FileInputStream类的对象
        FileInputStream fis = null;
        try {
            //1.创建一个File类的对象
            File file1 = new File("hello.txt");
            //2.创建一个FileInputStream的对象
            fis = new FileInputStream(file1);
            //3.调用FileInputStream的方法，实现File文件的读取
            /*
             * read()读取文件的一个字节，当执行到文件的结尾时，返回-1
             */
            int b;
            while ((b = fis.read()) != -1) {
                System.out.println((char) b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭相应的流
            if (fis != null) {
                fis.close();
            }
        }


        /*
         * 在开发时都使用的是数组形式的
         */
        FileInputStream fis2 = null;
        try {
            File file2 = new File("hello.txt");
            fis2 = new FileInputStream(file2);
            byte[] b = new byte[5];  //一次读取5个字节，读取的数据要写入进数组
            int len; //每次读入到byte中的字节的长度
            while ((len = fis2.read(b)) != -1) {
                for (int i = 0; i < len; i++) {
                    System.out.println((char) b[i]);
                }
                //等价于
                String str = new String(b, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis2 != null) {
                fis2.close();
            }
        }

        /*
         * 输出FileOutputStream
         */
        //1.创建一个File对象，表明要写入文件的位置
        //输出的物理文件可以不存在，当执行过程中，若不存在，会自动的创建。若存在，会将原有的覆盖。
        File file3 = new File("hello2.txt");
        //2.创建一个FileOutputStream对象，将file的对象作为形参传递给FileOutputStream的构造器中
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file3);
            //3.写入操作
            fos.write(new String("I lova China!").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.关闭输出流
            if (null != fos) {
                fos.close();
            }
        }

        /*
         * FileInputStream读入FiLeOutputStream输出
         * 文件的复制
         */
        //1.提供读入和写出的文件
        File file1 = new File("hello.txt");
        File file2 = new File("hello3.txt");

        //2.提供相应的流
        FileInputStream fis1 = null;
        FileOutputStream fos1 = null;

        try {
            fis1 = new FileInputStream(file1);
            fos1 = new FileOutputStream(file2);

            //3.文件的复制
            byte[] b = new byte[20];
            int len;
            while ((len = fis1.read()) != -1) {
                fos1.write(b, 0, len);
//                fos1.write(b); //错误的写法，同样错误的写法fos1.write(b, 0, b.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos1) {
                fos1.close();
            }
            if (null != fis1) {
                fis1.close();
            }
        }
    }

    //实现文件复制的方法
    public static void copyFile(String src, String dest) throws IOException {
        //1.提供读入和写出的文件
        File file1 = new File(src);
        File file2 = new File(dest);

        //2.提供相应的流
        FileInputStream fis1 = null;
        FileOutputStream fos1 = null;

        try {
            fis1 = new FileInputStream(file1);
            fos1 = new FileOutputStream(file2);

            //3.文件的复制
            byte[] b = new byte[20];
            int len;
            while ((len = fis1.read()) != -1) {
                fos1.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos1) {
                fos1.close();
            }
            if (null != fis1) {
                fis1.close();
            }
        }
    }
}
