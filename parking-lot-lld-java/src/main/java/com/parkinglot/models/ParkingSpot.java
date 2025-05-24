package com.parkinglot.models;

import com.parkinglot.enums.SpotStatus;
import com.parkinglot.enums.VehicleType;

public class ParkingSpot {
    private String spotId;
    private VehicleType vehicleType;
    private SpotStatus status;
    private Vehicle parkedVehicle;

    public ParkingSpot(String spotId, VehicleType vehicleType) {
        this.spotId = spotId;
        this.vehicleType = vehicleType;
        this.status = SpotStatus.AVAILABLE;
        this.parkedVehicle = null;
    }

    public boolean isAvailable() {
        return status == SpotStatus.AVAILABLE;
    }

    public boolean canPark(VehicleType type) {
        return isAvailable() && this.vehicleType == type;
    }

    public synchronized boolean occupySpot(Vehicle vehicle) {
        if(!canPark(vehicle.getType())) {
            return false;
        }
        this.parkedVehicle = vehicle;
        this.status = SpotStatus.OCCUPIED;
        return true;
    }

    public synchronized void freeSpot() {
        this.parkedVehicle = null;
        this.status = SpotStatus.AVAILABLE;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    @Override
    public String toString() {
        return String.format("Spot[%s-%s-%s]", spotId, vehicleType, status);
    }
}
