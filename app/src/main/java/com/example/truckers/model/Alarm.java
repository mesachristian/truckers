package com.example.truckers.model;

public class Alarm {

    public String name;
    public String message;
    public int max;
    public int min;

    public Alarm() {
    }

    public Alarm(String name, String message, int max, int min) {
        this.name = name;
        this.message = message;
        this.max = max;
        this.min = min;
    }

}
