package com.expgiga.io;

import org.testng.annotations.Test;

import java.io.*;

/**
 * 转换流：字节流<->字符流
 *
 */
public class TestInputStreamReaderWriter {

    /*
     * 转换流：InputStreamReader、OutputStreamWriter
     * 编码：字符串  --> 字节数组
     * 解码：字节数组--> 字符串
     */
    @Test
    public void testInputStreamReaderWriter() {
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            //解码
            File file = new File("hello.txt");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            //编码
            File file1 = new File("hello4.txt");
            FileOutputStream fos = new FileOutputStream(file1);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            bw = new BufferedWriter(osw);
            String str;
            while (null != (str = br.readLine())) {
                bw.write(str);
                bw.newLine();
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
