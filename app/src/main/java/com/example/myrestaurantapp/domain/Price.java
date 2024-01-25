package com.example.myrestaurantapp.domain;

public class Price {
    private int Id;
    private String value;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        value = value;
    }

    @Override
    public String toString() {
        return value ;
    }

    public Price(){

    }
}
