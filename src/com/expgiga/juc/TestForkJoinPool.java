package com.expgiga.juc;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 大任务拆分fork成若干个小任务，再将一个个的小任务运算的结果进行Join汇总。
 *
 * 工作窃取
 *
 */
public class TestForkJoinPool {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 100000000L);

        Long sum = pool.invoke(task);

        System.out.println(sum);
    }

    @Test
    public void test1() {
        Instant start = Instant.now();
        long sum = 0;
        for (long i = 0; i < 10000000000L; i++) {
            sum += i;
        }
        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis());
    }

    @Test
    public void test2() {
        Long sum = LongStream.rangeClosed(0L, 100000000000L)
                .parallel()
                .reduce(0L, Long::sum);
        System.out.println(sum);
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID = 3165602864332289096L;

    private long start;
    private long end;

    private static final long THURSHOLD = 10000L; //临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= THURSHOLD) {
            long sum = 0L;

            for (long i = start; i <= end; i++) {
                sum += i;
            }

            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();//进行拆分，压入线程队列

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}