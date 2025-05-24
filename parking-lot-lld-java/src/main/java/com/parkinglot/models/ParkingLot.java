package com.parkinglot.models;

import com.parkinglot.enums.VehicleType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private String id;
    private String name;
    private Map<Integer, Floor> floors;

    public ParkingLot(String id, String name) {
        this.id = id;
        this.name = name;
        this.floors = new ConcurrentHashMap<>();
    }

    public void addFloor(Floor floor) {
        floors.put(floor.getFloorNumber(), floor);
    }

    public ParkingSpot findNearestAvailableSpot(VehicleType type) {
        return floors.values().stream()
                .sorted(Comparator.comparing(Floor::getFloorNumber))
                .map(floor -> floor.findAvailableSpot(type))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public int getAvailableSpots(VehicleType type) {
        return floors.values().stream()
                .mapToInt(floor -> floor.getAvailableSpots(type))
                .sum();
    }

    public List<Floor> getAllFloors() {
        return floors.values().stream()
                .sorted(Comparator.comparing(Floor::getFloorNumber))
                .collect(ArrayList::new, (list, floor) -> list.add(floor), ArrayList::addAll);
    }

    public String getId() { return id; }
    public String getName() { return name; }
}
