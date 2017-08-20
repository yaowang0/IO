## java.io.File
**文件流：**
- FileInputStream/FileOutputStream/FileReader/FileWriter

**缓冲流：**
- BufferedInputStream/BufferedOutputStream
- BufferedReader/BufferedWriter

**转换流：**
- InputStreamReader/OutputStreamWriter

**对象流：涉及序列化、反序列化**
- ObjectInputStream/ObjectOutputStream

**随机存取文件流**
- RandomAccessFile


## java.net
> IP Address：InetAddress
> port：标识正在计算机上运行的进程。

IP address + port = socket

## 
Java NIO(NEW IO Non Blocking IO)以更加高效的方式进行文件的读写。
NIO系统的核心在于：通道(Channel)负责传输和缓冲区(Buffer)负责存储。
- Buffer和Channel
- FileChannel
- Selector
- SocketChannel、ServerSocketChannel、DatagramChannel
- pipe

IO | NIO|
----|----|
面向流(Stream Oriented) |面向缓冲区(Buffer Oriented) |
阻塞IO(Blocking IO) | 非阻塞IO(Non Blocking IO) |
无  |  选择器(Selectors)