package com.expgiga.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java8内置了四大核心函数式接口：
 * Consumer<T>:消费性接口
 *    void accept(T t);
 * Supplier<T>:供给型接口
 *    T get();
 * Function<T, R>:函数型接口
 *    R apply(T);
 * Predicate<T>:断言型接口
 *    boolean test(T t);
 */
public class TestLambda3 {

    //Predicate<T t>
    //需求：将满足条件的字符串，放入集合中
    private List<String> filterStr(List<String> list, Predicate<String> pre) {
        List<String> strList = new ArrayList<>();

        for (String str : strList) {
            if(pre.test(str)) {
                strList.add(str);
            }
        }

        return strList;
    }
    @Test
    public void test4() {
        List<String> list = Arrays.asList("Hello", "World", "Java8", "Lambda");
        List<String> newList = filterStr(list, s -> s.length() > 5);
        for (String str : newList) {
            System.out.println(str);
        }
    }

    //Function<T t, R r>
    //需求：用于处理字符串
    private String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    @Test
    public void test3() {
        String newStr = strHandler("\t\t\t Hello World ", (str) -> str.trim());
        System.out.println(newStr);

        String newStr2 = strHandler("Hello Lambda!", str -> str.substring(2, 5));
        System.out.println(newStr2);
    }

    //Supplier<T t>
    @Test
    public void test2() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer num : numList) {
            System.out.println(num);
        }
    }

    //需求：产生指定个数的整数，并放入集合中
    private List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }

        return list;
    }

    //Consumer<T t>
    @Test
    public void test1() {
        happy(100, x -> System.out.println("spend " + x + "yuan!"));
    }

    private void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }
}
