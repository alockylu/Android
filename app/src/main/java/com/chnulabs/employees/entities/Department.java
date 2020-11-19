package com.chnulabs.employees.entities;

import java.util.Arrays;
import java.util.List;

public class Department {

    private String name;
    private int id;
    private boolean isRemote;
    private boolean hasTrainees;
    private boolean hasInvalids;

    public Department(String name, int id, boolean isRemote, boolean hasTrainees, boolean hasInvalids) {
        this.name = name;
        this.id = id;
        this.isRemote = isRemote;
        this.hasTrainees = hasTrainees;
        this.hasInvalids = hasInvalids;
    }

    public String getName() {
        return name;
    }

    public boolean hasTrainees() {
        return hasTrainees;
    }

    public boolean hasInvalids() {
        return hasInvalids;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public int getId() {
        return id;
    }

    private static List<Department> departments = Arrays.asList(
            new Department("Sales", 3, false, true, false),
            new Department("Marketing", 1, true, false, false),
            new Department("Software Development", 2, true, true, true),
            new Department("Logistics", 4, false, false, true)
    );

    public static Department getBy(String name) {
        return departments.stream().filter(dep -> dep.name.equals(name)).findAny().orElse(null);
    }
}
