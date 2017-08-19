package com.expgiga.io;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 */
public class TestPrintStreamWriter {
    /*
     * 打印流：
     * 字节流：PrintStream
     * 字符流：PrintWriter
     */

    @Test
    public void testPrint() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("print.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建打印输出流，设置为自动刷新模式(写入换行符或字节'\n'时都会刷新输出缓冲区)
        PrintStream ps = new PrintStream(fos, true);
        if (ps != null) { //把标准输出流(控制台输出)改成文件
            System.setOut(ps);
        }
        for (int i = 0; i <= 255; i++) { //输出ASCII字符
            System.out.print((char) i);
            if (i % 50 == 0) {
                System.out.println();
            }
        }
        ps.close();
    }
}
