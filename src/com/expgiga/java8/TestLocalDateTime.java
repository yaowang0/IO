package com.expgiga.java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * //1.LocalDate LocalTime LocalDateTime
 *
 * //2.TemporalAdjuster
 *
 * //3.DateTimeFormatter
 */
public class TestLocalDateTime {
    //人读的时间
    @Test
    public void test1() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = LocalDateTime.of(2017, 1, 19, 13, 22, 33);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.plusYears(2);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ldt.minusYears(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonth());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
    }

    //时间戳（Unix元年：1970年1月1日，00:00:00到某个时间之前的毫秒值）
    @Test
    public void test2() {
        Instant instant1 = Instant.now(); //默认是UTC时区
        System.out.println(instant1);

        OffsetDateTime odt = instant1.atOffset(ZoneOffset.ofHours(8)); //本地时间
        System.out.println(odt);

        System.out.println(instant1.toEpochMilli());//时间戳

        Instant instant2 = Instant.ofEpochSecond(60);
        System.out.println(instant2);
    }

    //Duration
    //Period
    @Test
    public void test3() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1000);
        Instant end = Instant.now();

        System.out.println(Duration.between(start, end));

        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());

        System.out.println("----------------------");
        LocalTime lt1 = LocalTime.now();
        Thread.sleep(1000);
        LocalTime lt2 = LocalTime.now();
        System.out.println(Duration.between(lt1, lt2).toMillis());
    }

    @Test
    public void test4() {
        LocalDate localDate1 = LocalDate.of(2015,1,1);
        LocalDate localDate2 = LocalDate.now();

        Period period = Period.between(localDate1, localDate2);
        System.out.println(period);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }

    //TemporalAdjuster:时间校正器
    @Test
    public void test5() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);//指定日为10号
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义:下一个工作日
        LocalDateTime ldt5 = ldt.with(l -> {
            LocalDateTime ldt4 = (LocalDateTime) l;
            DayOfWeek dw = ldt4.getDayOfWeek();

            if (dw.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dw.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });
        System.out.println(ldt5);
    }

    @Test
    public void test6() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);
        System.out.println(strDate);

        System.out.println("----------------------------");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strDate2 = dtf1.format(ldt);
        System.out.println(strDate2);

        LocalDateTime newDate = ldt.parse(strDate2, dtf1);
        System.out.println(newDate);
    }

    //ZonedDate ZonedTime ZonedDateTime
    @Test
    public void test7() {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    @Test
    public void test8() {
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        System.out.println(ldt);

        LocalDateTime ldt2 = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zdt = ldt2.atZone(ZoneId.of("Europe/Tallinn"));
        System.out.println(zdt);
    }
}
