package com.parkinglot.main;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.models.ParkingSpot;
import com.parkinglot.models.Ticket;
import com.parkinglot.models.Vehicle;
import com.parkinglot.system.ParkingLotSystem;

public class ParkingLotDemo {
    public static void main(String[] args) {
        ParkingLotSystem system = new ParkingLotSystem();

        // Setup parking lot
        system.addParkingLot("LOT1", "City Center Mall");
        system.addFloor("LOT1", 1);
        system.addFloor("LOT1", 2);

        // Add parking spots
        system.addParkingSpot("LOT1", 1, new ParkingSpot("1-A1", VehicleType.TWO_WHEELER));
        system.addParkingSpot("LOT1", 1, new ParkingSpot("1-A2", VehicleType.TWO_WHEELER));
        system.addParkingSpot("LOT1", 1, new ParkingSpot("1-B1", VehicleType.FOUR_WHEELER));
        system.addParkingSpot("LOT1", 1, new ParkingSpot("1-B2", VehicleType.FOUR_WHEELER));
        system.addParkingSpot("LOT1", 2, new ParkingSpot("2-C1", VehicleType.TRUCK));

        // Display initial availability
        system.displayAvailableSpots("LOT1");

        try {
            // Test vehicle entry
            Vehicle bike = new Vehicle("KA01-1234", VehicleType.TWO_WHEELER, "Red");
            Vehicle car = new Vehicle("KA02-5678", VehicleType.FOUR_WHEELER, "Blue");
            Vehicle truck = new Vehicle("KA03-9012", VehicleType.TRUCK, "White");

            Ticket ticket1 = system.parkVehicle("LOT1", bike);
            Ticket ticket2 = system.parkVehicle("LOT1", car);
            Ticket ticket3 = system.parkVehicle("LOT1", truck);

            system.displayAvailableSpots("LOT1");

            // Test edge case: same vehicle parking again
            try {
                system.parkVehicle("LOT1", bike);
            } catch (IllegalStateException e) {
                System.out.println("Edge case handled: " + e.getMessage());
            }

            // Simulate some time passing (in real system, this would be actual time)
            Thread.sleep(1000);

            // Test vehicle exit
            System.out.println("\n=== Vehicle Exit ===");
            double fee1 = system.exitVehicle(ticket1.getTicketId());
            double fee2 = system.exitVehicle(ticket2.getTicketId());

            system.displayAvailableSpots("LOT1");

            // Test edge case: invalid ticket
            try {
                system.exitVehicle("INVALID");
            } catch (IllegalArgumentException e) {
                System.out.println("Edge case handled: " + e.getMessage());
            }

            // Test edge case: no available spots
            try {
                Vehicle bike2 = new Vehicle("KA04-3456", VehicleType.TWO_WHEELER, "Green");
                Vehicle bike3 = new Vehicle("KA05-7890", VehicleType.TWO_WHEELER, "Black");
                system.parkVehicle("LOT1", bike2);
                system.parkVehicle("LOT1", bike3); // This should fail
            } catch (RuntimeException e) {
                System.out.println("Edge case handled: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
