package com.expgiga.java8;

/**
 *
 */
public class FilterEmployeeBySalary implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee e) {
        return e.getSalary() >= 5000;
    }
}
