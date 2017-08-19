package com.expgiga.io;

import org.junit.Test;

import java.io.*;

/**
 * 用于存储和读取对象的处理流。它的强大之处就是可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。
 *
 * 序列化(Serialize):
 * 用ObjectOutputStream将一个Java对象写入IO流中
 *
 * 反序列化(Deserialize):
 * 用ObjectInputStream类从IO流中恢复该Java对象
 *
 * ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量。
 *
 * 对象序列化机制：允许把内存中的Java对象转换成与平台无关的二进制流，从而允许把这种二进制流持久的保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点。当程序获得了这种二进制流，就可以恢复成原来的Java对象。
 *
 * 序列化的好处在于将任何实现了Serialize接口的对象转化为字节数据，使其在保存和传输时可被还原。
 *
 * 序列化是RMI（Remote Method Invoke）过程的参数和返回值都必须实现的机制。而RMI是JavaEE的基础。因此序列化机制是JavaEE平台的基础。
 *
 * 如果需要让某个对象支持序列化机制，则必须让其类的可序列化的，为了让某个类是可序列化的，该类必须实现以下两个接口之一：Serializable, Externalizable
 *
 * 凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态变量。
 * private static final long serialVersionUID
 *
 * 显式的定义serialVersionUID的用途：
 * 1.希望类的不同版本对序列化兼容，因此需确保类的不同版本具有相同的serialVersionUID
 * 2.不希望类的不同版本对序列化兼容，因此需确保类的不同版本具有不同的serialVersionUID
 */
public class TestObjectInputOutputStream {
    //将内部中的序列化过程：将内部中的对象通过objectOutputStream转换成为二进制流，存储在硬盘文件中
    @Test
    public void testObjectOutputStream() {
        Person p1 = new Person("xiaomi", 23);
        Person p2 = new Person("hongmi", 21);

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("person.txt"));

            oos.writeObject(p1);
            oos.flush();
            oos.writeObject(p2);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //对象的反序列化的过程：将硬盘中的文件通过ObjectInputStream转换为相应的对象
    @Test
    public void testObjectInputStream() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("person.txt"));
            Person p1 = (Person) ois.readObject();
            System.out.println(p1);
            Person p2 = (Person) ois.readObject();
            System.out.println(p2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*
 * Person要实现序列化，要求：
 * 1.类要是可序列化的：实现接口Serialized/Externalizable
 * 2.类的属性也要求是实现了Serialized接口
 * 3.提供一个版本号：private static final long serialVersionUID
 * 4.不能序列化static和transient修饰的成员变量
 */

class Person implements Serializable {
    private static final long serialVersionUID = -9008441467926226186L;
    String name;
    int age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
