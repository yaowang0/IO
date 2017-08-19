package com.expgiga.java;

/**
 * 用于存储和读取对象的处理流。它的强大之处就是可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。
 *
 * 序列化(Serialize):用ObjectOutputStream将一个Java对象写入IO流中
 *
 * 反序列化(Deserialize):用ObjectInputStream类从IO流中恢复该Java对象
 *
 * ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量。
 */
public class TestObjectInputOutputStream {

}
