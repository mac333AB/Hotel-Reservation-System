package api;
import java.util.*;
import service.*;
import models.*;
public class AdminResource {
	public static AdminResource adminResource = new AdminResource();
	public static CustomerService customerService = CustomerService.getInstance();
	public static ReservationService reservationService = ReservationService.getInstance();

	
	public Customer getcustomer(String email) {
		
		return customerService.getCustomer(email);
	}
	

	public void addRoom(List<IRoom> rooms) {
		for (IRoom room : rooms) {
			reservationService.addRoom(room);
		}
		
	}
	
	
	public Collection<IRoom> getAllRooms(){
		return reservationService.getAllRooms();
		
	}
	
	
	public Collection<Customer> getAllCustomers(){
		return customerService.getAllCustomers();
	}
	
	
	public void displayAllReservation() {
		reservationService.printAllReservation();
	}
	
	private AdminResource() {}
	
	public static AdminResource getInstance() {
		return adminResource;
	}
}
