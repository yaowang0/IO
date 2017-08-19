package com.expgiga.net;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端给服务端发送信息，服务端输出此信息到控制台
 *
 * 网络编程实际上就是Socket编程
 */
public class TestTCP1 {

    //客户端
    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        try {
            //1.创建Socket，通过构造器指名服务端的IP地址，以及其接受程序的端口号
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
            //2.getOuputStream()发送数据，方法返回OutputStream对象
            os = socket.getOutputStream();
            //3.具体输出
            os.write("I am Client!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    //4.关闭相应的流和Socket
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //服务端
    @Test
    public void server() {
        ServerSocket ss = null;
        InputStream is = null;
        Socket s = null;
        try {
            //1.创建ServerSocket，指名自身的端口号
            ss = new ServerSocket(9090);
            //2.调用accept()方法，返回一个Socket对象
            s = ss.accept();
            //3.调用Socket对象的getInputStream()获取一个从客户端发送过来的输入流
            is = s.getInputStream();
            //4.对获取的输入流进行的操作
            byte[] b = new byte[20];
            int len;
            while (-1 != (len = is.read(b))) {
                String str = new String(b, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5.关闭相应的流和Socket，ServerSocket
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != s) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ss) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
