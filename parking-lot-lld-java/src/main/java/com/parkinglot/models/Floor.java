package com.parkinglot.models;

import com.parkinglot.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Floor {
    private int floorNumber;
    private Map<String, ParkingSpot> spots;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spots = new ConcurrentHashMap<>();
    }

    public void addSpot(ParkingSpot spot) {
        spots.put(spot.getSpotId(), spot);
    }

    public ParkingSpot findAvailableSpot(VehicleType type) {
        return spots.values().stream()
                .filter(spot->spot.canPark(type))
                .findFirst()
                .orElse(null);
    }

    public int getAvailableSpots(VehicleType type) {
        return (int) spots.values().stream()
                .filter(spot -> spot.canPark(type))
                .count();
    }

    public List<ParkingSpot> getAllSpots() {
        return new ArrayList<>(spots.values());
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
