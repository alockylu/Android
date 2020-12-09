package com.chnulabs.employees.entities;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Employee {

    private String fullName;
    private String department;

    public Employee(String fullName, String department) {
        this.fullName = fullName;
        this.department = department;
    }

    @NonNull
    @Override
    public String toString() {
        return fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    private static final ArrayList<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee("Ivanov Ivan", "Sales"),
                    new Employee("Petr Sidorov", "Sales"),
                    new Employee("Gregory Robinson", "Marketing"),
                    new Employee("Oksana Galiv", "Software Development"),
                    new Employee("Smirnov Sergey", "Software Development"),
                    new Employee("Ivan Petrov", "Logistics"),
                    new Employee("Jin Beam", "Logistics"),
                    new Employee("Steve Medan", "Marketing")
            )
    );

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    public static Stream<Employee> getEmployees(final String department) {
        return employees.stream().filter(e -> e.department.equals(department));
    }

    public static long countEmployeesBy(Department dep) {
        return employees.stream().filter(employee -> employee.department.equals(dep.getName())).count();
    }
}
