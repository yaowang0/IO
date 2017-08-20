package com.expgiga.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 阻塞：服务端一直等待客户端的请求，而不去做其他的操作。
 * 非阻塞：存在Selector选择器，会把每一个传输数据的通道都注册，Selector监控通道上的状况。当Channel数据准备就绪，Selector就会将该Channel的任务，放在服务器的一个线程或者多个线程上去执行。
 *
 *
 * 一、使用NIO完成网络通信的三个核心：
 * 1.Channel:负责连接
 *   java.nio.channels.Channel
 *       |--SelectableChannel
 *          |--SocketChannel
 *          |--ServerSocketChannel
 *          |--DatagramChannel
 *
 *          |--Pipe.SinkChannel
 *          |--Pipe.SourceChannel
 * 2.Buffer:负责数据的存取
 * 3.Selector:SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况。
 */
public class TestBlockingNIO {

    //客户端
    @Test
    public void client() {
        SocketChannel sChannel = null;
        FileChannel inChannel = null;
        try {
            //1.获取通道Channel
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

            //2.分配指定大小的缓冲区域
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //3.读取本地文件，发送到服务端去
            while (inChannel.read(buf) != -1) {
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭通道
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
        } catch(IOException e) {
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
