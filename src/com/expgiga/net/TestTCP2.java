package com.expgiga.net;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端给服务端发信息，服务端将信息打印的控制台，同时发送已收到信息给客户端
 */
public class TestTCP2 {

    //客户端
    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 8989);
            os = socket.getOutputStream();
            os.write("I ma client!".getBytes());

            is = socket.getInputStream();
            byte[] b= new byte[20];
            int len;
            while (-1 != (len = is.read(b))) {
                String str = new String(b, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
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
        OutputStream os = null;
        InputStream is = null;
        Socket s = null;
        try {
            ss = new ServerSocket(8989);
            s = ss.accept();
            is = s.getInputStream();
            byte[] b = new byte[20];
            int len;
            while (-1 != (len = is.read(b))) {
                String str = new String(b, 0, len);
                System.out.println(str);
            }
            os = s.getOutputStream();
            os.write("I have receive the message!".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
