package com.expgiga.java;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 标准输入输出流
 * System.out
 * System.in
 */
public class TestStandInputOutputStream {
    //需求：从键盘输入整行字符串转成大写输出，然后继续进行输入操作，直至当输入e或exit时，退出程序。
    @Test
    public void test() {
        BufferedReader br = null;
        try {
            InputStream is = System.in;
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String str;
            while (true) {
                System.out.println("输入字符串：");
                str = br.readLine();
                if (str.equalsIgnoreCase("e") || str.equalsIgnoreCase("exit")) {
                    break;
                }
                String str1 = str.toUpperCase();
                System.out.println(str1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
