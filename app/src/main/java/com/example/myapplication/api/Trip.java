package com.example.myapplication.api;

public class Trip {

    private String name;
    private String from_location;
    private String to_location;

    public Trip(String name, String from_location, String to_location) {
        this.name = name;
        this.from_location = from_location;
        this.to_location = to_location;
    }

    public String getName() {
        return name;
    }

    public String getFrom_location() {
        return from_location;
    }

    public String getTo_location() {
        return to_location;
    }
}
