package models;

public class FreeRoom extends Room {

	public FreeRoom(String roomNumber, RoomType type) {
	
		super(roomNumber, type, 0.0);
	}
	
	@Override
	public String toString(){
		return  "Room { \n Room Number= "+ getRoomNumber()+ 
				"\n Price= "+ getRoomPrice()+ 
				"\n RoomType= "+getRoomType()+"\n}";
		
	}
	
	
}
