package com.parkinglot.system;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.models.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotSystem {
    private Map<String, ParkingLot> parkingLots;
    private Map<String, Ticket> activeTickets;
    private int ticketcounter;

    public ParkingLotSystem() {
        this.parkingLots = new ConcurrentHashMap<>();
        this.activeTickets = new ConcurrentHashMap<>();
        this.ticketcounter = 1000;
    }

    public void addParkingLot(String id, String name) {
        parkingLots.put(id, new ParkingLot(id, name));
        System.out.println("Added parking lot: "+name);
    }

    public void addFloor(String lotId, int floorNumber) {
        ParkingLot lot = parkingLots.get(lotId);
        if (lot == null) {
            throw new IllegalArgumentException("Parking lot not found: " + lotId);
        }
        lot.addFloor(new Floor(floorNumber));
        System.out.println("Added floor: " + floorNumber + " to " + lot.getName());
    }

    public void addParkingSpot(String lotId, int floorNumber, ParkingSpot spot) {
        ParkingLot lot = parkingLots.get(lotId);
        if (lot ==null) {
            throw new IllegalArgumentException("Parking lot not found: "+ lotId);
        }

        Floor floor = lot.getAllFloors().stream()
                .filter(f->f.getFloorNumber()==floorNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Floor not found: "+floorNumber));
        floor.addSpot(spot);
        System.out.println("Added parking spot: " + spot);
    }

    public Ticket parkVehicle(String lotId, Vehicle vehicle) {
        boolean alreadyParked = activeTickets.values().stream()
                .anyMatch(ticket -> ticket.getVehicle().getRegistrationNumber()
                        .equals(vehicle.getRegistrationNumber()));

        if(alreadyParked) {
            throw new IllegalStateException("Vehicle already parked: " + vehicle.getRegistrationNumber());
        }

        ParkingLot lot = parkingLots.get(lotId);
        if(lot==null) {
            throw new IllegalArgumentException("Parking lot not found: "+ lotId);
        }

        ParkingSpot spot = lot.findNearestAvailableSpot(vehicle.getType());
        if(spot==null) {
            throw new RuntimeException("No available spots for vehicle type: " + vehicle.getType());
        }

        if(!spot.occupySpot(vehicle)) {
            throw new RuntimeException("Failed to occupy slot");
        }

        String ticketId = "T" + (++ticketcounter);
        Ticket ticket = new Ticket(ticketId, vehicle, spot);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    public double exitVehicle(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if(ticket==null) {
            throw new IllegalArgumentException("Invalid ticket or vehicle not found: " + ticketId);
        }

        ticket.markExit();
        ticket.getSpot().freeSpot();
        activeTickets.remove(ticketId);

        double fee = ticket.calculateFee();
        System.out.println("Vehicle exited successfully!");
        System.out.println("Parking fee: " + fee);
        return fee;
    }

    public int getAvailableSpots(String lotId, VehicleType type) {
        ParkingLot lot = parkingLots.get(lotId);
        if (lot == null) {
            return 0;
        }
        return lot.getAvailableSpots(type);
    }

    public void displayAvailableSpots(String lotId) {
        ParkingLot lot = parkingLots.get(lotId);
        if (lot == null) {
            System.out.println("Parking lot not found: " + lotId);
            return;
        }

        System.out.println("\n=== Available Spots in " + lot.getName() + " ===");
        for (VehicleType type : VehicleType.values()) {
            int available = lot.getAvailableSpots(type);
            System.out.println(type + ": " + available + " spots available");
        }
        System.out.println();
    }
}
