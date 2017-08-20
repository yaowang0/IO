package com.expgiga.net;

import jdk.internal.util.xml.impl.Input;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL(Uniform Resource Locator)
 * URL:<proxy>://<hostName>:<port>/<fileName>
 * 通过URL的对象调用其方法，将此资源读取.
 */
public class TestURL {
    public static void main(String[] args) throws Exception {
        //1.创建URL的对象
        URL url = new URL("http://127.0.0.1:8080/examples/HelloWorld.txt"); //File file = new File("Path");
        /*
         * public String getProtocal()
         * public String getHost()
         * public String getPort()
         * public String getPath()
         * public String getFile()
         * public String getRef()
         * public String getQuery()
         */
        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getFile());
        System.out.println(url.getRef());
        System.out.println(url.getQuery());

        //2.将服务端的资源读取进来:openStream()
        InputStream is = url.openStream();
        byte[] b = new byte[20];
        int len;
        while (-1 != (len = is.read(b))) {
            String str = new String(b, 0, len);
            System.out.println(str);
        }
        is.close();

        //3.如果既有数据输入，又有数据的输出，使用URLConnection
        URLConnection urlConnection = url.openConnection();
        InputStream is1 = urlConnection.getInputStream();
        FileOutputStream fos = new FileOutputStream(new File("url.txt"));
        byte[] b1 = new byte[20];
        int len1;
        while (-1 != (len1 = is1.read(b1))) {
            fos.write(b1, 0, len1);
        }
        fos.close();
        is1.close();
    }
}