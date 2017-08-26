package com.expgiga.java8;

/**
 * java8中interface中允许有默认实现方法
 */
public interface MyFun {

    default String getName() {
        return "Hello";
    }
}
