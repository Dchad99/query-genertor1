package com.ukraine.dc;

import com.ukraine.dc.api.domain.Column;
import com.ukraine.dc.api.domain.Entity;
import com.ukraine.dc.api.domain.Id;

@Entity(table = "persons")
public class Person {
    @Id
    @Column
    private int id;

    @Column(name = "person_name")
    private String name;

    @Column
    private double salary;

    public Person(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}