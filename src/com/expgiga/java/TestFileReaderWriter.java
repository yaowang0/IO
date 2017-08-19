package com.expgiga.java;

import org.testng.annotations.Test;

import java.io.*;

/*
 * 使用FileReader FileWriter实现文本文件的复制！
 * 对于非文本文件(视频文件、音频文件、图片)只能够使用字节流！
 */
public class TestFileReaderWriter {
    @Test
    public void testFileReaderd() {
        File file = new File("hello.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            char[] c = new char[24];
            int len;
            while ((len = fileReader.read(c)) != -1) {
                String str = new String(c, 0, len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void testFileReaderWriter() {
        //1.输入流对应的文件要存在，输出流对应的文件可以不存在，执行过程中会自动创建
        FileReader fr = null;
        FileWriter fw = null;
        try {
            File src = new File("hello.txt");
            File dest = new File("helloCopy.txt");
            //2.
            fr = new FileReader(src);
            fw = new FileWriter(dest);
            //3.
            char[] c = new char[24];
            int len;
            while ((len = fr.read(c)) != -1) {
                fw.write(c, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
