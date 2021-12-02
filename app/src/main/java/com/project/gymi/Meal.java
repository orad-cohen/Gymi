package com.project.gymi;

public class Meal {

    String name;
    Double kCal;

    public Meal() {
    }

    public Meal(String name, Double kCal) {
        this.name = name;
        this.kCal = kCal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getkCal() {
        return kCal;
    }

    public void setkCal(Double kCal) {
        this.kCal = kCal;
    }
}
