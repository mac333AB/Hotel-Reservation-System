package service;

import models.*;


import java.util.*;

public class ReservationService {

	private static ReservationService reservationService = new ReservationService();
	private static Map<String,List <Reservation>> reservations= new HashMap<>();
	private static Map<String, IRoom> allRooms = new HashMap<>();
	
	
	//addding dummy data
	static {
		allRooms.put("201", new Room("201", RoomType.SINGLE, 1000));
		allRooms.put("202", new Room("202",RoomType.SINGLE, 1000));
		allRooms.put("203", new Room("203", RoomType.DOUBLE, 2000));
		allRooms.put("204", new Room("204", RoomType.SINGLE, 1000));
		allRooms.put("205", new Room("205", RoomType.DOUBLE, 2000));
	}
	
	//adding reservation dummy data for testing purpose, will be commented out in final version
	/*static {
		Calendar cal = Calendar.getInstance();
		cal.set(2026, Calendar.JANUARY, 1);
		Date checkIn = cal.getTime();

		cal.set(2026, Calendar.JANUARY, 15);
		Date checkOut = cal.getTime();
		reservations.put("abhi@mail.com",new ArrayList<>(List.of(new Reservation(CustomerService.getInstance().getCustomer("abhi@mail.com"), allRooms.get("201"), checkIn, checkOut),
				new Reservation(CustomerService.getInstance().getCustomer("abhi@mail.com"), allRooms.get("202"), checkIn, checkOut),
				new Reservation(CustomerService.getInstance().getCustomer("abhi@mail.com"), allRooms.get("203"), checkIn, checkOut),
				new Reservation(CustomerService.getInstance().getCustomer("abhi@mail.com"), allRooms.get("204"), checkIn, checkOut),
				new Reservation(CustomerService.getInstance().getCustomer("abhi@mail.com"), allRooms.get("205"), checkIn, checkOut))));
		
	}*/
	
	
	
	
	
	
	
	
	
	

	public void addRoom(IRoom room) {
		if (room.getRoomNumber().isEmpty()) {
			throw new IllegalArgumentException("Room number cannot be empty.");
		}
		if (allRooms.containsKey(room.getRoomNumber())) {
			throw new IllegalArgumentException("Room with this number already exists.");
		}
		allRooms.put(room.getRoomNumber(), room);		
	}


	public IRoom getARoom(String roomId) {
		if (roomId.isEmpty()) {
			throw new IllegalArgumentException("Room ID cannot be null or empty.");
		}
		if (!allRooms.containsKey(roomId)) {
			throw new IllegalArgumentException("No room found with this ID.");
		}
		return allRooms.get(roomId);

	}



	public Reservation resevreARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

		// Validate inputs
		if (customer == null || room == null || checkInDate == null || checkOutDate == null) {
			throw new IllegalArgumentException("All fields are necessary for reservation.");
		}

		// date validation

		if (checkInDate.before(new Date())) {
			throw new IllegalArgumentException("Check-in date cannot be in the past.");
		}

		if (checkInDate.after(checkOutDate)) {
			throw new IllegalArgumentException("Check-in date cannot be after check-out date.");
		}

		if (!allRooms.containsKey(room.getRoomNumber())) {
			throw new IllegalArgumentException("Room does not exist.");
		}

		for (List<Reservation> res : reservations.values()) {
			for (Reservation reservation : res) {
				// Check if room is already reserved for these dates
				if (reservation.getRoom().equals(room) && dateOverlap(checkInDate, checkOutDate,
						reservation.getCheckInDate(), reservation.getCheckOutDate())) {
					throw new IllegalArgumentException("Room is already reserved for these dates.");
				}
			}
			
		}
		
		reservations.putIfAbsent(customer.getEmail(), new ArrayList<>()); // if the user  does not have a reservation yet, it will create a new list of reservations for user
		// Check if customer already has a reservation
		if(reservations.containsKey(customer.getEmail())) {
			Collection<Reservation> customerRes=getCustomerReservation(customer);
			for (Reservation r : customerRes) {
				if (dateOverlap(checkInDate, checkOutDate, r.getCheckInDate(), r.getCheckOutDate())) {
					throw new IllegalArgumentException("Customer already has a reservation for these dates.");
				}
			}
		}
	
			Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
			reservations.get(customer.getEmail()).add(reservation); 
			return reservation;

		}
	




	public Collection<IRoom> findRoom(Date checkInDate, Date checkOutDate){

		if (checkInDate == null || checkOutDate == null) {
			throw new IllegalArgumentException("Check-in and check-out dates cannot be null.");
		}

		if (checkInDate.after(checkOutDate)) {
			throw new IllegalArgumentException("Check-in date cannot be after check-out date.");
		}
		if (checkInDate.before(new Date())) {
			throw new IllegalArgumentException("Check-in date cannot be in the past.");
		}

		List<IRoom> availableRooms = new ArrayList<>();

		for (IRoom room : allRooms.values()) 
		{
			boolean isAvailable = true;
			for (List<Reservation> listRes : reservations.values()) 
			{
				for (Reservation res : listRes) 
				{
					if (res.getRoom().equals(room) && dateOverlap(checkInDate, checkOutDate, res.getCheckInDate(), res.getCheckOutDate()))					{
						isAvailable = false;
						break;
					}
					
				}	
				if (!isAvailable) {
					break;
				}
			}

			if (isAvailable) {
				availableRooms.add(room);
			}
		}
		if (availableRooms.isEmpty()) {
			throw new IllegalArgumentException("No rooms available for the given dates.");
		}

		return availableRooms;

	}




	public Collection<Reservation> getCustomerReservation(Customer customer){
		if (customer == null || !reservations.containsKey(customer.getEmail())) {
			throw new IllegalArgumentException("No reservations found for this customer.");
		}
		return reservations.get(customer.getEmail());

	}


	public void printAllReservation() {
		if (reservations.isEmpty()) {
			System.out.println("No reservations found.");
			return;
		}
		for (List<Reservation> res : reservations.values()) {
			System.out.println(res);
		}

	}

	
	public Collection<IRoom> getAllRooms() {
		if (allRooms.isEmpty()) {
			throw new IllegalArgumentException("No rooms found.");
		}
		return allRooms.values();
	}

	

	public static ReservationService getInstance() {
		return reservationService;
	}

	private ReservationService() {}

	public boolean dateOverlap(Date checkInNew, Date checkOutNew, Date checkInOld, Date checkOutOld) {
		return ((checkInNew.equals(checkInOld) && checkOutNew.equals(checkOutOld)) // if date same as existing reservation
				|| (checkInNew.after(checkInOld) && checkInNew.before(checkOutOld)) // new check-in is between existing reservation
				|| (checkOutNew.after(checkInOld) && checkOutNew.before(checkOutOld)) // new check-out is between existing reservation
				|| (checkInNew.before(checkInOld) && checkOutNew.after(checkOutOld)) ); // new reservation envelops existing reservation

	}

}
