package models;

import java.util.*;

public class Reservation {
	Customer customer;
	IRoom room;
	Date checkInDate;
	Date checkOutDate;

	public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
		this.customer = customer;
		this.room = room;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
	
	public Customer getCustomer() {
        return customer;
    }
	 
	@Override
	public String toString() {
		return "Reservation{ \n Customer= "+ customer+ 
				"\n Room= "+ room+ 
				"\n Check-in Date= "+checkInDate+
				"\n Check-out Date= "+checkOutDate+
				"\n}";
	}

	public Date getCheckInDate(){		
		return checkInDate;
	}

	public Date getCheckOutDate(){
		return checkOutDate;
	}
	
	public IRoom getRoom() {
		return room;
	}

}
