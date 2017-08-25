package com.expgiga.java8;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 一、Lambda表达式的基础语法：Java8中引入了新的操作符 -> 该操纵符称为Lambda操作符
 * 左侧：Lambda的参数列表
 * 右侧：Lambda所需要执行的功能，Lambda体
 *
 * 语法格式一：
 * 无参数、无返回值
 * () -> System.out.println("Lambda");
 *
 * 语法格式二：
 * 一个参数、无返回值
 * (x) -> System.out.println(x);
 *
 * 语法格式三：
 * 若只有一个参数，小括号可以省略不写。
 * x -> System.out.println(x);
 *
 * 语法格式四：
 * 有两个以上的参数，有返回值，并且Lambda体中有多条语句。
 * Comparator<Integer> com = (x, y) -> {
 *        System.out.println("函数式接口：");
 *        return Integer.compare(x, y);
 * };
 *
 * 语法格式五：
 * 若Lambda只有一条语句，return和大括号都可以省略不写
 *
 * 语法格式六：
 * Lambda表达式中的采参数列表的数据类型可以省略不写，因为JVM的编译器可以通过上下文类型，称为类型推断。
 * （Integer x, Integer y） -> Integer.compare(x, y);
 *
 * 二、Lambda表达式需要函数式接口的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。可以使用注解@FunctionalInterface修饰
 * 可以检查是否是函数式接口。
 */
public class TestLambda2 {

    @Test
    public void test1() {
        final int num = 0;//jdk1.7之前，必须final

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!" + num);
            }
        };
        r.run();

        System.out.println("--------------------");

        Runnable r1 = () -> System.out.println("Hello World!");
        r1.run();
    }

    @Test
    public void test2() {
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("Hello World!");
    }

    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口：");
            return Integer.compare(x, y);
        };
    }

    @Test
    public void test4() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    }

    @Test
    public void test5() {
        String[] strs = {"aa", "bb"};
    }

    //需求：对一个数进行运算
    @Test
    public void test6() {
        Integer num = operation(100, (x) -> x * x);
        System.out.println(num);

        System.out.println(operation(200, x -> x + 100));
    }

    public Integer operation(Integer num, MyFunc mf) {
        return mf.getValue(num);
    }
}

