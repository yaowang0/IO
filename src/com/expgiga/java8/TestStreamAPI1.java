package com.expgiga.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数据源 一系列流水式的中间操作 产生一个新的数据流，原来的数据源没有变化
 *
 * 1.创建流
 * 2.中间操作
 * 3.终止操作
 */
public class TestStreamAPI1 {

    //创建Stream
    @Test
    public void test1() {
        //1.通过Collection，系列集合提供的stream()或者parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.通过Arrays中的静态方法stream()，获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(emps);

        //3.通过Stream类中的静态方法of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4.创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
        stream4.limit(10)
        .forEach(System.out::println);
        //生成
        Stream.generate(() -> (Math.random()))
                .forEach(System.out::println);
    }
}
