package com.expgiga.java8;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 重复注解与类型注解TYPE_PARAMETER
 */
public class TestAnnotation {

    private @NotNull Object obj = null;

    @Test
    public void test() throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;

        Method m1 = clazz.getMethod("show");

        MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);

        for (MyAnnotation myAnnotation : mas) {
            System.out.println(myAnnotation.value());
        }
    }

    @MyAnnotation("Hello")
    @MyAnnotation("world")
    public void show(@MyAnnotation("abc") String string) {

    }
}
