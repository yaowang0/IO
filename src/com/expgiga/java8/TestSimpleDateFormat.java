package com.expgiga.java8;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 传统时间的多线程安全问题
 * <p>
 * Instant
 * Duration
 * <p>
 * LocalDate
 * LocalDateTime
 * LocalTime
 * Period
 */
public class TestSimpleDateFormat { //

    //存在线程安全问题
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Callable<Date> task = new Callable<Date>() {

            @Override
            public Date call() throws Exception {
                return sdf.parse("20161218");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> futureList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futureList.add(pool.submit(task));
        }

        for (Future<Date> future : futureList) {
            System.out.println(future.get());
        }

        pool.shutdown();

    }

    //通过ThreadLocal方法解决上述问题i
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Callable<Date> task = new Callable<Date>() {

            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20161218");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> futureList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futureList.add(pool.submit(task));
        }

        for (Future<Date> future : futureList) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }

    //jdk8中的时间
    @Test
    public void test3() throws ExecutionException, InterruptedException {

//        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        Callable<LocalDate> task = new Callable<LocalDate>() {

            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20161218", dtf);
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<LocalDate>> futureList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futureList.add(pool.submit(task));
        }

        for (Future<LocalDate> future : futureList) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }

}
