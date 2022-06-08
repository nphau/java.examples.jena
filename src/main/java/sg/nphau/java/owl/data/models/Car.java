/**
 * Created by nphau on 04/05/2022, 10:30
 * Copyright (c) 2021 nphau. All rights reserved.
 * Last modified 04/05/2022, 15:54
 */
package sg.nphau.java.owl.data.models;

public class Car {

    private String name;
    private double price;
    private int year;
    private int seat;
    private int mpg;

    public Car() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getSeat() {
        return seat;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

}
