package com.expgiga.java8;

/**
 *
 */
public class FilterEmployeeByAge  implements MyPredicate<Employee>{

    @Override
    public boolean test(Employee t) {
        return t.getAge() >= 35;
    }
}
