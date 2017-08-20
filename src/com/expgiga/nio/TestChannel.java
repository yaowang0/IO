package com.expgiga.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * CPU负责IO->改为内存的DMA直接存储器（向CPU要权限）负责IO->内存Channel（独立）负责IO
 *
 * 一、Channel：用于源节点与目标节点的连接。在Java NIO中负责缓冲区中数据的传输。Channel本身是不存储数据，需要配合缓冲区进行传输。
 *
 * 二、通道的一些主要实现类：
 * java.nio.channels.Channel 接口：
 *   |--FileChannel
 *   |--SocketChannel
 *   |--ServerSocketChannel
 *   |--DatagramChannel
 *
 * 三、获取通道
 * 1.Java针对支持通道的类getChannel()方法
 *    本地IO:
 *    FileInputStream/FileOutputStream
 *    RandomAccessFile
 *    网络IO:
 *    Socket
 *    ServerSocket
 *    DatagramSocket
 * 2.在JDK1.7中的NIO.2 针对各个通道提供了静态方法open()
 * 3.在JDK1.7中的NIO.2 的Files工具类的newByteChannel()
 *
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取(Scattering Reads):将通道中的数据分散到多个缓冲区中。
 * 聚集写入(Gathering Writers):将多个缓冲区的数据聚集到一个通道中。
 *
 * 六、字符集Charset
 * 编码：字符串->字节数组
 * 解码：字节数组 -> 字符串
 *
 */
public class TestChannel {

    //1.利用通道完成文件的复制(非直接缓冲区)
    @Test
    public void test1() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel= null;
        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");

            //①获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //③将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1) {
                buf.flip();//切换成读取数据模式
                //④将缓冲区内的数据写入通道
                outChannel.write(buf);
                buf.clear();//清空缓冲区
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outChannel) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inChannel) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //2.使用直接缓冲区完成文件的复制(内存映射文件的方式)
    @Test
    public void test2() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("3.jpg"),StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW); //CREATE_NEW文件存在会报错，CREATE直接覆盖

            //内部映射文件
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            //直接对缓冲区进行数据读写操作
            byte[] dst = new byte[inMappedBuf.limit()];
            inMappedBuf.get(dst);
            outMappedBuf.put(dst);

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
            if (null != outChannel) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //3.通道之间的数据传输 transferFrom() transferTo()，也是直接缓冲区的方式
    @Test
    public void test3() {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("1.avi"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("3.avi"),StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE); //CREATE_NEW文件存在会报错，CREATE直接覆盖

           inChannel.transferTo(0, inChannel.size(), outChannel); //等价于 outChannel.transferFrom(inChannel, 0, inChannel.size());

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
            if (null != outChannel) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //4.分散和聚集
    @Test
    public void test4() {
        RandomAccessFile raf1 = null;
        RandomAccessFile raf2 = null;
        FileChannel channel1 = null;
        FileChannel channel2 = null;
        try {
           raf1 = new RandomAccessFile("1.txt", "rw");

            //1.获取Channel
           channel1 = raf1.getChannel();

           //2.分配指定大小的缓冲区
            ByteBuffer buf1 = ByteBuffer.allocate(100);
            ByteBuffer buf2 = ByteBuffer.allocate(1024);

            //3.分散读取
            ByteBuffer[] bufs = {buf1, buf2};
            channel1.read(bufs);

            for (ByteBuffer byteBuffer : bufs) {
                byteBuffer.flip();
            }

            System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
            System.out.println("-----------------------");
            System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

            //4.聚集写入
            raf2 = new RandomAccessFile("2.txt", "rw");
            channel2 = raf2.getChannel();

            channel2.write(bufs);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf1) {
                try {
                    raf1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != raf2) {
                try {
                    raf2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != channel1) {
                try {
                    channel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != channel2) {
                try {
                    channel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //5.字符集Charset
    @Test
    public void test5() {
        Map<String, Charset> map = Charset.availableCharsets();

        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
    @Test
    public void test6() throws CharacterCodingException {
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("Hello World!");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);
        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());
    }
}
