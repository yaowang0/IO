package com.expgiga.io;

import org.junit.Test;

import java.io.*;

/**
 * DataInputStream 和 DataOutputStream分别"套接"在InputStream和OutputStream字节流上
 */
public class TestDataStream {
    /*
     * 数据流：用来处理基本数据类型、String、字节数组的数据
     * DataInputStream
     * DataOutputStream
     */
    @Test
    public void testDataOutputStream() {
        DataOutputStream dos = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("data.txt"));
            dos = new DataOutputStream(fos);

            dos.writeUTF("Hello World!");
            dos.writeBoolean(true);
            dos.writeLong(809472232);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != dos) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDataInputStream() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(new File("data.txt")));
//            byte[] b = new byte[20];
//            int len;
//            while ((len = dis.read(b)) != -1) {
//                System.out.println(new String(b, 0, len));
//            }
            String str = dis.readUTF();
            System.out.println(str);
            boolean b = dis.readBoolean();
            System.out.println(b);
            long l = dis.readLong();
            System.out.println(l);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
