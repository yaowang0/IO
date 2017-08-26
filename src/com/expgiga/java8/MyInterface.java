package com.expgiga.java8;

/**
 *
 */
public interface MyInterface {
    default String getName() {
        return "Hey!";
    }

    public static void show() {
        System.out.println("接口中的静态方法！");
    }
}
