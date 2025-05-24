package com.parkinglot.models;

import com.parkinglot.services.FeeCalculator;
import com.parkinglot.services.StandardFeeCalculator;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private FeeCalculator feeCalculator;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.feeCalculator = new StandardFeeCalculator();
    }

    public double calculateFee() {
        if(exitTime==null) {
            throw new IllegalStateException("Vehicle hasn't exited yet");
        }
        Duration duration = Duration.between(entryTime, exitTime);
        return feeCalculator.calculateFee(vehicle, duration);
    }

    public void markExit() {
        this.exitTime = LocalDateTime.now();
    }

    // Getters
    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }

    public String toString() {
        return String.format("Ticket[%s] - %s at %s (Entry: %s)",
                ticketId, vehicle, spot.getSpotId(), entryTime);
    }
}
