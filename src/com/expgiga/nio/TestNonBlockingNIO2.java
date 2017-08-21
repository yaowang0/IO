package com.expgiga.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * UDP
 */
public class TestNonBlockingNIO2 {

    @Test
    public void send() {
        DatagramChannel dc = null;

        try {
            dc = DatagramChannel.open();

            dc.configureBlocking(false);

            ByteBuffer buf = ByteBuffer.allocate(1024);

            Scanner scan = new Scanner(System.in);

            while (scan.hasNext()) {
                String str = scan.next();
                buf.put((new Date().toString() + "\n" + str).getBytes());
                buf.flip();
                dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != dc) {
                try {
                    dc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void receive() {
        DatagramChannel dc = null;
        Selector selector = null;
        try {
            dc = DatagramChannel.open();

            dc.configureBlocking(false);

            dc.bind(new InetSocketAddress(9898));

            selector = Selector.open();

            dc.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                while (it.hasNext()) {
                   SelectionKey sk = it.next();

                   if (sk.isReadable()) {
                        ByteBuffer buf = ByteBuffer.allocate(1024);

                        dc.receive(buf);
                        buf.flip();
                       System.out.println(new String(buf.array(), 0, buf.limit()));
                       buf.clear();
                   }
                }
                it.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != selector) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != dc) {
                try {
                    dc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
