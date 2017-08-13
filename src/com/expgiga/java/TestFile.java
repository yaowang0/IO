package com.expgiga.java;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * java.io.File类
 * 1.凡是与输入、输出相关的类、接口等都定义在java.io包下
 * 2.File是一个类，可以有构造器创建其对象，此对象对应的这一个文件(.txt .avi .doc .ppt .mp3 .jpg)或者目录
 * 3.File与平台无关
 * 4.File中的方法，仅涉及到如何创建、删除、重命名等。只要是涉及文件内容的，File是无能为力，必须由IO流来完成。
 * 5.File的对象常作为IO流的具体类的构造器的形参
 *
 * 一、访问文件名：
 * getName()
 * getPath()
 * getAbsoluteFile()
 * getAbsolutePath()
 * getParent()
 * renameTo(File newName)
 *
 * 二、文件检测
 * exists()
 * canWrite()
 * canRead()
 * isFile()
 * isDirectory()
 *
 * 三、获取常规文件信息
 * lastModified()
 * length()
 *
 * 四、文件操作类
 * createNewFile()
 * delete()
 *
 * 五、目录操作相关
 * mkDir()
 * mkDirs()
 * list()
 * listFile()
 */
public class TestFile {
    public static void main(String[] args) throws IOException {

        /*
         * 路径：
         * 1.绝对路径：包括盘符在内的完整的文件路径
         * 2.相对路径：在当前文件目录下的文件的路径
         */
        File file1 = new File("E:\\io\\HelloWorld.txt");
        File file2 = new File("hello.txt");

        File file3 = new File("E:\\io\\io1");

        System.out.println(file1.getName());
        System.out.println(file1.getPath());
        System.out.println(file1.getAbsoluteFile());
        System.out.println(file1.getParent());
        System.out.println(file1.getAbsolutePath());

        System.out.println();
        System.out.println(file3.getName());
        System.out.println(file3.getPath());
        System.out.println(file3.getAbsoluteFile());
        System.out.println(file3.getParent());
        System.out.println(file3.getAbsolutePath());

        //renameTo(File name)
        //file1.renameTo(file2):将file1重命名为file2，要求file1一定存在，file2一定不存在。
        boolean bl = file1.renameTo(file2);
        System.out.println(bl);


        /*
         * 文件检测与获取常规文件信息
         * exists()
         * canWrite()
         * canRead()
         * isFile() 是否是文件
         * isDirectory() 是否是目录
         * lastModified() 最后修改时间
         * length()  文件的大小
         */
        System.out.println(file1.exists());
        System.out.println(file1.canWrite());
        System.out.println(file1.canRead());
        System.out.println(file1.isFile());
        System.out.println(file1.isDirectory());
        System.out.println(file1.lastModified());
        System.out.println(new Date(file1.lastModified()));
        System.out.println(file1.length());

        /*
         * 文件操作和目录操作
         * createNewFile()
         * delete()
         * mkDir()  上层目录存在的情况下
         * mkDirs() 要创建的目录的上层目录不存在，一并创建
         * list()   以字符串的形式，列出目录下的所有文件。
         * listFile() 以文件的形式呈现
         */
        System.out.println(file1.delete());
        if (!file1.exists()) {
            boolean b = file1.createNewFile();
            System.out.println(b);
        }

        File file4 = new File("E:\\io\\io2");
        if (!file4.exists()) {
            boolean b = file4.mkdir();
            System.out.println(b);
        }

        File file5 = new File("E:\\io");
        String[] strs = file5.list();
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
        }

        File[] files = file5.listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
        }

    }
}
