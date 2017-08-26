package com.expgiga.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 1.创建流
 * 2.中间操作
 * 3.终止操作
 */
public class TestStreamAPI2 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 50, 6666.66),
            new Employee("赵六", 16, 3333.33),
            new Employee("田七", 8, 7777.77),
            new Employee("田七", 8, 7777.77),
            new Employee("田七", 8, 7777.77),
            new Employee("田七", 8, 7777.77)
    );

    //中间操作：是lazy的！
    /*
     * sorted(Comparable)
     * sorted(Comparator com)
     */
    @Test
    public void test8() {
        List<String> list = Arrays.asList("eee", "aaa", "fff", "ddd", "bbb", "ccc");

        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("--------------------------------");

        employees.stream()
                .sorted((e1, e2) -> {
                    if (e1.getSex().equals(e2.getSex())) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return e1.getSex().compareTo(e2.getSex());
                    }
                })
                .forEach(System.out::println);
    }

    /*
     * 映射
     * map
     * flatMap
     */
    //map
    @Test
    public void test6() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee", "fff");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("--------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("--------------------");

        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI2::filterCharacter);

        stream.forEach((sm) -> {
            sm.forEach(System.out::println);
        });

        System.out.println("--------------------");

        Stream<Character> stream1 = list.stream()
                .flatMap(TestStreamAPI2::filterCharacter);
        stream1.forEach(System.out::println);
    }

    @Test
    public void test7() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee", "fff");
        List list2 = new ArrayList<>();
        list2.add("111");
        list2.add("222");
//        list2.add(list);  //[111, 222, [aaa, bbb, ccc, ddd, eee, fff]]
        list2.addAll(list); //[111, 222, aaa, bbb, ccc, ddd, eee, fff]
        System.out.println(list2);

    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }

        return list.stream();
    }


    /*
     * 筛选与切片
     * filter
     * limit
     * skip
     * distinct 通过hashCode和equals去重
     */
    //内部迭代：迭代操作由Stream API完成
    //filter
    @Test
    public void test1() {
        Stream<Employee> stream = employees.stream()
                .filter(e -> {
                    System.out.println("Stream API的中间操作！");
                    return e.getAge() >= 35;
                });

        //终止操作
        stream.forEach(System.out::println);

    }

    //外部迭代
    public void test2() {
        Iterator<Employee> it = employees.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    //limit()
    @Test
    public void test3() {
        employees.stream()
                .filter((e) -> {
                    System.out.println("短路");
                    return e.getSalary() >= 5000;
                })
                .limit(2) //取前两个
                .forEach(System.out::println);
    }

    //skip
    @Test
    public void test4() {
        employees.stream()
                .filter(e -> e.getSalary() >= 5000)
                .skip(2) //跳过前两个
                .forEach(System.out::println);
    }

    //distinct
    @Test
    public void test5() {
        employees.stream()
                .filter(e -> e.getSalary() >= 5000)
                .skip(2)
                .distinct() //去重
                .forEach(System.out::println);
    }
}
