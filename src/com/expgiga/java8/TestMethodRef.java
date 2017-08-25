package com.expgiga.java8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * 【方法引用】：若Lambda体中的内容，有方法已经实现了，可以使用方法引用。
 *
 * 三种语法格式：
 * 1.对象::实例方法名
 * 2.类::静态方法名
 * 3.类::实例方法名
 *
 * 注意：
 * ①Lambda体中调用方法的参数和返回值类型，要与函数式接口抽象的参数列表和返回值类型一致。
 * ②如Lambda参数列表中，第一个参数是实例方法的调用者，第二个参数是实例方法的参数，可以使用类::实例方法名
 *
 * 【构造器引用】：
 * 格式：
 * ClassName::new
 * 注意：需要调用的构造器的参数列表要与函数式接口中的抽象方法的参数列表保持一致。
 *
 * 【数组引用】：
 * Type::new
 *
 */
public class TestMethodRef {

    //对象::实例方法名
    @Test
    public void test1() {
        Consumer<String> con = (x) -> System.out.println(x);

        PrintStream ps = System.out;
        Consumer<String> con1 = ps::println;

        Consumer<String> con2 = System.out::println;
        con2.accept("abcdefghijklmn");
    }

    @Test
    public void test2() {
        Employee emp = new Employee();
        Supplier<String> sup = () -> emp.getName();
        String str = sup.get();
        System.out.println(str);

        Supplier<Integer> sup2 = emp::getAge;
        Integer num = sup2.get();
        System.out.println(num);
    }

    //类::静态方法
    @Test
    public void test3() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> com1 = Integer::compare;
    }

    //类::实例方法名
    @Test
    public void test4() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        BiPredicate<String, String> bp1 = String::equals; //第一个参数是实例方法的调用者，第二个参数是实例方法的参数
    }

    //构造器引用
    @Test
    public void test5() {
        Supplier<Employee> sup = () -> new Employee();
        Supplier<Employee> sup1 = Employee::new; //调用的是无参构造器
        Employee emp = sup1.get();
        System.out.println(emp);
    }

    @Test
    public void test6() {
        Function<Integer, Employee> fun = (x) -> new Employee(x);
        Function<Integer, Employee> fun2 = Employee::new;//1个参数的构造器
        Employee emp = fun2.apply(101);
        System.out.println(emp);

        BiFunction<Integer, Integer, Employee> bf = Employee::new; //2个参数的构造器
    }

    //数组引用
    @Test
    public void test7() {
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] strings = fun.apply(10);
        System.out.println(strings.length);

        Function<Integer, String[]> fun2 = String[]::new;
        String[] strings1 = fun.apply(20);
        System.out.println(strings1.length);
    }
}
