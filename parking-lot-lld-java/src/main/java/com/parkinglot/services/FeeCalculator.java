package com.parkinglot.services;

import com.parkinglot.models.Vehicle;

import java.time.Duration;

public interface FeeCalculator {
    double calculateFee(Vehicle vehicle, Duration duration);
}
