package com.parkinglot.services;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.models.Vehicle;

import java.time.Duration;
import java.util.Map;

public class StandardFeeCalculator implements FeeCalculator {
    private static final int FREE_HOURS = 2;
    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
            VehicleType.TWO_WHEELER, 10.0,
            VehicleType.FOUR_WHEELER, 20.0,
            VehicleType.TRUCK, 50.0
    );

    @Override
    public double calculateFee(Vehicle vehicle, Duration duration) {
        long totalHours = duration.toHours();
        if(totalHours<= FREE_HOURS) {
            return 0.0;
        }

        long chargeableHours = totalHours - FREE_HOURS;
        double hourlyRate = HOURLY_RATES.get(vehicle.getType());
        return chargeableHours * hourlyRate;
    }
}
