💡 Problem:
Design a Parking Lot System that supports the following features:

🎯 Functional Requirements:
Multiple Parking Lots, each with multiple Floors.

Each Floor has:

A unique floor number

A fixed number of Parking Spots of various types: TwoWheeler, FourWheeler, Truck

Vehicle entry and exit:

Vehicle enters — system allocates the nearest available spot for the vehicle type.

Vehicle exits — the spot becomes free.

Parking Ticket Generation:

Ticket must have: Entry time, Vehicle details, Parking Spot details

Parking Fee Calculation (Simple version):

First 2 hours free

₹10 per hour after that for TwoWheeler

₹20 for FourWheeler

₹50 for Truck

Admin can:

Add floors

Add parking spots

View current available spots

📌 Bonus Features (Optional for later):
Reservation of spots in advance

Support for electric vehicles and charging points

Display board for free/occupied spots

🧪 Edge Cases to Handle:
What happens when:

All spots of a particular vehicle type are full?

A vehicle tries to exit without an entry record?

The same vehicle tries to enter twice?

Two vehicles with same registration number exist? (Disallow or handle?)

Entry after system restart (add persistence or logs?)

Multiple vehicles entering at the same time (if concurrency added)