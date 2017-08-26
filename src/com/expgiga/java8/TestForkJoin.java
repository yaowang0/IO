package com.expgiga.java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 *
 */
public class TestForkJoin {

    @Test
    public void test() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 1000000000);
        System.out.println(pool.invoke(task));

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }


    //java8
    @Test
    public void test3() {
        LongStream.rangeClosed(0, 10000000000000L)
                .parallel() //并行流 （串行流sequential()）
                .reduce(0, Long::sum);
    }
}
