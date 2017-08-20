package com.expgiga.nio;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 需求：客户端给服务端发图片，服务端接收到了，回信息给客户端。
 */
public class TestBlockingNIO2 {
    //客户端
    @Test
    public void client() {
        SocketChannel sChannel = null;
        FileChannel inChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

            ByteBuffer buf = ByteBuffer.allocate(1024);

            while (inChannel.read(buf) != -1) {
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

            //告诉服务端，客户端已经发送完毕
            sChannel.shutdownOutput();

            //接受服务端的反馈
            int len = 0;
            while ((len = sChannel.read(buf)) != -1) {
                buf.flip();
                System.out.println(new String(buf.array(), 0, len));
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inChannel) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != sChannel) {
                try {
                    sChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //服务端
    @Test
    public void server() {
        ServerSocketChannel ssChannel = null;
        SocketChannel sChannel = null;
        FileChannel outChannel = null;

        try {
            //1.获取通道
            ssChannel = ServerSocketChannel.open();

            outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

            //2.绑定连接端口号
            ssChannel.bind(new InetSocketAddress(9898));

            //3.获取客户端连接的通道
            sChannel = ssChannel.accept();

            //4.分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //5.接受客户端的数据，保存到本地
            while (sChannel.read(buf) != -1) {
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }

            //发送反馈给客户端
            buf.put("I have receive the data!".getBytes());
            buf.flip();
            sChannel.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6. 关闭通道
            if (null != sChannel) {
                try {
                    sChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outChannel) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ssChannel) {
                try {
                    ssChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
