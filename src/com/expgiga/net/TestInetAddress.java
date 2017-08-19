package com.expgiga.net;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络通信第一要素：IP地址。通过IP地址，唯一定位互联网上一台主机。
 * InetAddress位于java.net包下
 * 1.InetAddress代表IP地址，一个InetAddress对象代表着一个IP地址
 * 2.创建InetAddress的对象，getByName(String host)
 * 3.getHostName()
 *   getHostAddress()
 *
 * HostName  ->DNS   ->HostAddress -> Service
 *           ->hosts
 */
public class TestInetAddress {
    @Test
    public void test() throws UnknownHostException {
        //创建一个InetAddress对象：getByName()
        InetAddress inet = InetAddress.getByName("www.google.com");
        System.out.println(inet);
        //两个方法
        System.out.println(inet.getHostName());
        System.out.println(inet.getHostAddress());

        //获取本机的IP:getLocalHost()
        InetAddress inet1 = InetAddress.getLocalHost();
        System.out.println(inet1);
        System.out.println(inet1.getHostName());
        System.out.println(inet1.getHostAddress());
    }
}
