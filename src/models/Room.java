package models;

public class Room implements IRoom {
	
	private final String roomNumber;
	private final double price;
	private final RoomType enumeration;
	
	@Override
	public String toString() {
		return "Room { \n Room Number= "+ roomNumber+ 
				"\n Price= "+ price+ 
				"\n RoomType= "+enumeration+"\n}";
		
	}
	
	
	public Room(String roomNumber, RoomType enumeration, double price) {
	    this.roomNumber = roomNumber;
	    this.enumeration = enumeration;
	    this.price = price;
	}
	
	@Override
	public String getRoomNumber() {

		return roomNumber;
	}

	@Override
	public double getRoomPrice() {
		return price;
	}

	@Override
	public RoomType getRoomType() {
		return enumeration;
	}

	@Override
	public boolean isFree() {
	    return price == 0.0;
	}

	

}
