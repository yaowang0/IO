package com.expgiga.net;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * DatagramSocket和DatagramPacket实现了基于UDP协议网络程序。
 *
 * UDP网络通信
 * 流程：
 * 1.DatagramSocket与DatagramPacket
 * 2.建立发送端，接收端
 * 3.建立数据包
 * 4.调用Socket的发送、接收方法
 * 5.关闭Socket
 *
 * 发送端和接收端是两个独立的运行程序
 */
public class TestUDP {

    //发送端
    @Test
    public void send() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            byte[] b = "I am the DATA!".getBytes();
            //创建一个数据报，每一个数据报不能大于64K，每一个都记录着数据信息，发送端的IP，端口号，要发送到的接收端的IP及端口号
            DatagramPacket packet = new DatagramPacket(b, 0, b.length, InetAddress.getByName("127.0.0.1"), 9090);

            ds.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ds) {
                ds.close();
            }
        }
    }

    //接收端
    @Test
    public void receive() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(9090);
            byte[] b = new byte[1024];
            DatagramPacket packet = new DatagramPacket(b, 0, b.length);
            ds.receive(packet);

            String str = new String(packet.getData(), 0, packet.getLength());
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ds) {
                ds.close();
            }
        }
    }
}