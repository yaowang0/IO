package com.expgiga.io;

import org.junit.Test;

import java.io.*;

/**
 * IO流的体系：
 * 抽象基类         节点流(文件流)         缓冲流(处理流的一种，用来加速节点流处理速度)
 * InputStream     FileInputStream       BufferedInputStream
 * OutputStream    FileOutputStream      BufferedOutputStream (flush())
 * Reader          FileReader            BufferedReader       (readLine())
 * Writer          FileWriter            BufferedWriter       (flush())
 *
 * Buffered为什么能够加速？
 * 1.底层数组
 * 2.non-block
 */
public class TestBuffered {
    //使用BufferedInputStream和BufferedOutputStream实现非文本文件的复制
    @Test
    public void testBufferedInputOutputStream() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //1.创建相应的文件
            File file1 = new File("1.jpg");
            File file2 = new File("2.jpg");

            //2.先创建相应的节点流FileInputStream FileOutputStream
            FileInputStream fis = new FileInputStream(file1);
            FileOutputStream fos = new FileOutputStream(file2);

            //3.将创建的节点流对象作为形参，传递给缓冲流的构造器中
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            //4.具体的实现文件复制的操作
            byte[] b = new byte[1024];
            int len;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5.关闭相应的流
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testBufferedReader() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            File file1 = new File("hello.txt");
            File file2 = new File("hello3.txt");
            FileReader fr = new FileReader(file1);
            FileWriter fw = new FileWriter(file2);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

//            char[] c = new char[1024];
//            int len;
//            while((len = br.read(c)) != -1) {
//                String str = new String(c, 0, len);
//                System.out.print(str);
//            }

            String str;
            while (null != (str = br.readLine())) {
//                System.out.println(str);
                bw.write(str + "\n");
//                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}