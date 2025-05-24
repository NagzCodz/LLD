package com.parkinglot.models;

import com.parkinglot.enums.VehicleType;

public class Vehicle {
    private String registrationNumber;
    private VehicleType type;
    private String color;

    public Vehicle(String registrationNumber, VehicleType type, String color) {
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.color = color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s)", registrationNumber, type, color);
    }
}
