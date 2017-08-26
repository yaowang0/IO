package com.expgiga.java8;

import jdk.nashorn.internal.runtime.options.Option;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 终止操作
 *
 */
public class TestStreamAPI3 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99, Employee.Status.FREE),
            new Employee("李四", 38, 5555.55, Employee.Status.BUSY),
            new Employee("王五", 50, 6666.66, Employee.Status.VOCATION),
            new Employee("赵六", 16, 3333.33, Employee.Status.FREE),
            new Employee("田七", 8, 7777.77, Employee.Status.BUSY)
    );

    /**
     * 搜集
     * collect:
     *
     */
    @Test
    public void test5() {
       Long count = employees.stream()
               .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("-----------------");
        double ave = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(ave);

        System.out.println("------------------");
        Double total = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(total);


    }

    @Test
    public void test4() {
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("------------------------");

        Set<String> stringSet = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        stringSet.forEach(System.out::println);

        System.out.println("------------------------");

        HashSet<String> hs = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);
    }

    /**
     * 规约
     * reduce(T identity, BinaryOperator)
     * reuuce(BinaryOperator)
     */
    @Test
    public void test3() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        Optional<Integer> op1 = list.stream()
                .reduce((x, y) -> x + y);
        System.out.println(op1.get());

        System.out.println("------------------");

        Optional<Double> optional = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(optional.get());
    }

    /*
     * 查找与匹配
     * allMatch
     * anyMatch
     * noneMatch
     * findFirst
     * findAny
     * count
     * max
     * min
     */
    @Test
    public void test1() {
        boolean b1 = employees.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = employees.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        boolean b3 = employees.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b3);

        Optional<Employee> op = employees.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(op.get());

        Optional<Employee> op2 = employees.parallelStream()
                .filter(e -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(op2.get());
    }

    @Test
    public void test2() {
        Long count = employees.stream()
                .count();
        System.out.println(count);

        Optional<Employee> op = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(op.get());

        Optional<Double> op2 = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(op2.get());
    }
}
