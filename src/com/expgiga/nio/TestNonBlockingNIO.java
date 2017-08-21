package com.expgiga.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;


/**
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
public class TestNonBlockingNIO {
    //客户端
    @Test
    public void client() {
        SocketChannel sChannel = null;
        try {
            //1.获取通道
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

            //2.切换成非阻塞模式
            sChannel.configureBlocking(false);

            //3.分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //4.发送数据给服务端
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()) {
                String str = scan.next();
                buf.put((new Date().toString() + "\n" + str).getBytes());
                buf.flip();
                sChannel.write(buf);
                buf.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5.关闭通道
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
        Selector selector = null;
        SocketChannel sChannel = null;
        SocketChannel sChannel1 = null;
        try {
            //1.获取通道
            ssChannel = ServerSocketChannel.open();

            //2.切换成非阻塞模式
            ssChannel.configureBlocking(false);

            //3.绑定连接
            ssChannel.bind(new InetSocketAddress(9898));

            //4.获取选择器
            selector = Selector.open();

            //5.将通道注册到选择器上，并且指定监听事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT); //SelectionKey OP_READ OP_WRITE OP_CONNECT OP_ACCEPT

            //6.轮询获取选择器上已经准备就绪的事件
            while (selector.select() > 0) {
                //7.获取当前选择器中的所有注册的选择键(已就绪的监听事件)
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                while (it.hasNext()) {
                    //8.获取准备就绪的事件
                    SelectionKey sk = it.next();

                    //9.判断具体是什么事件准备就绪
                    if (sk.isAcceptable()) {
                        //10.若接受就绪，就获取客户端连接
                        sChannel = ssChannel.accept();

                        //11.客户端切换成非阻塞模式
                        sChannel.configureBlocking(false);

                        //12.将该通道注册到选择器上
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        //13.获取当前选择器读就绪状态的通道
                        sChannel1 = (SocketChannel) sk.channel();

                        //14.读取数据
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len;
                        while ((len = sChannel.read(buf)) > 0) {
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len));
                            buf.clear();
                        }
                    }

                    //15.取消选择键 SelectionKey
                    it.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭相关的通道等
            if (null != selector) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ssChannel != null) {
                try {
                    ssChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
