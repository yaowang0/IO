package com.expgiga.io;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile类支持"随机访问"的方式，程序可以直接跳到文件的任意地方来读写文件。
 * 1.既可以充当一个输入流，也可以充当一个输出流
 * 2.支持从文件的开头读取、写入
 * 3.支持从文件的任意位置读取和写入
 *
 * RandomAccessFile对象包含一个记录指针，用以标记当前读写处的位置。RandomAccessFile类对象可以自由移动记录指针：
 * long getFilePointer():获取文件记录指针的当前的位置
 * long seek(long pos):将文件记录指针定位到pos位置
 *
 * 构造器
 * public RandomAccessFile(File file, String mode)
 * public RandomAccessFile(String name, String mode)
 * mode:
 * r:只读
 * rw:读和写
 * rwd:读取和写入，同步文件内容的更新
 * rws:读取和写入，同步文件内容和元数据的更新
 */
public class TestRandomAccessFile {

    //进行文件的读、写
    @Test
    public void testRandomAccessFile() {

        RandomAccessFile raf1 = null;
        RandomAccessFile raf2 = null;
        try {
            raf1 = new RandomAccessFile(new File("randomAccessFile1.txt"), "r");
            raf2 = new RandomAccessFile(new File("randomAccessFile2.txt"),"rw");
            byte[] b = new byte[20];
            int len;
            while ((len = raf1.read(b)) != -1) {
                raf2.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf2) {
                try {
                    raf2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != raf1) {
                try {
                    raf1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //这个实现的覆盖的效果
    @Test
    public void test2() {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File("randomAccessFile1.txt"), "rw");
            raf.seek(3);
            raf.write("xy".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //实现插入的效果，在第3个字符后面插入xy，此时的文件初始只有一行
    @Test
    public void test3() {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File("randomAccessFile1.txt"), "rw");
            raf.seek(3);
            String str = raf.readLine();
//            long l = raf.getFilePointer();
//            System.out.println(l);
            raf.seek(3);
            raf.write("xy".getBytes());
            raf.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //实现插入的效果，在第3个字符后面插入xy，此时的文件初始有多行
    @Test
    public void test4() {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File("randomAccessFile1.txt"), "rw");
            raf.seek(3);
            byte[] b = new byte[10];
            int len;
            StringBuffer sb = new StringBuffer();
            while (-1 != (len = raf.read(b))) {
                sb.append(new String(b, 0, len));
            }
            raf.seek(3);
            raf.write("xy".getBytes());
            raf.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
