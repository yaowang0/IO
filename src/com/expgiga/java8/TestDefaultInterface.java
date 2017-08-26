package com.expgiga.java8;

/**
 * "类优先"原则
 * 若继承多个interface，并且多个interface含有相同的默认的方法，则必须要是是实现了
 */
public class TestDefaultInterface {
    public static void main(String[] args) {
        SubClass sc = new SubClass();
        System.out.println(sc.getName()); //调用的是Class的方法，"类优先"原则

        MyInterface.show();
    }
}
