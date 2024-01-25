package com.example.myrestaurantapp.domain;

public class Time {
    private String id;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        value = value;
    }

    public Time() {
    }

    @Override
    public String toString() {
        return value;
    }
}
