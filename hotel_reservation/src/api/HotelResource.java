package api;

import java.util.*;

import models.*;
import service.*;

public class HotelResource {
	
	private static final HotelResource hotelResource = new HotelResource();
	public static CustomerService customerService= CustomerService.getInstance();
	
	public static ReservationService reservationService= ReservationService.getInstance();
	
	public Customer getCostumer(String email) {
		if (email.isEmpty()) {// just in case, if UI validation is bypassed.
			throw new IllegalArgumentException("Email cannot be empty.");
		}
		return customerService.getCustomer(email);
	}
	
	
	public void createACustomer(String email, String firstName, String lastName) {
		if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
			throw new IllegalArgumentException("All fields are necessary to create a customer. ");
		}
		customerService.addCustomer(email, firstName, lastName);
		
	}
	
	public IRoom getRoom(String roomNumber) {
		if (roomNumber.isEmpty()) {
			throw new IllegalArgumentException("Room number cannot be empty.");
		}
		return reservationService.getARoom(roomNumber);
	}
	
	
	public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
		Customer customer = getCostumer(customerEmail);
		if (customer == null) {
			throw new IllegalArgumentException("No customer found with this email.");
		}
		if (room == null|| checkInDate==null || checkOutDate==null) {
			throw new IllegalArgumentException("Reservation Details can not be empty.");
		}
		
		return reservationService.resevreARoom(customer, room, checkInDate, checkOutDate);
		
	}
	
	
	public Collection<Reservation> getCustomersReservations(String customerEmail) {
		if (customerEmail.isEmpty()) {
			throw new IllegalArgumentException("Email cannot be empty.");
		}
		Customer customer = getCostumer(customerEmail);
		if (customer == null) {
			throw new IllegalArgumentException("No customer found with this email."); 
		}
		return reservationService.getCustomerReservation(customer);
		
	}
	
	
	public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
		if (checkIn == null || checkOut == null) {
			throw new IllegalArgumentException("Check-in and Check-out dates cannot be null.");
		}
		return reservationService.findRoom(checkIn, checkOut);
		
	}
	
	
	private HotelResource() {}

	public static HotelResource getInstance() {
		return hotelResource;
	}
}
