package com.project.gymi;

public class Meal {

    String userUID;
    String name;
    Double kCal;
    int Quantity;

    public Meal() {
    }

    public Meal(String UserUID,String name, Double kCal,int quantity) {
        this.userUID = UserUID;
        this.name = name;
        this.kCal = kCal;
        this.Quantity = quantity;
    }

    public String getUID() {
        return userUID;
    }

    public void setUID(String UserUID) {
        this.userUID = UserUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity () {return this.Quantity;}

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public Double getkCal() {
        return kCal;
    }

    public void setkCal(Double kCal) {
        this.kCal = kCal;
    }
}
