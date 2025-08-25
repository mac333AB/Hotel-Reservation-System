package menuUI;

import java.util.*;

import api.*;
import models.*;


public class AdminMenu {
	AdminResource adminResource = AdminResource.getInstance();
	HotelResource hotelResource =  HotelResource.getInstance();
	private static final AdminMenu adminMenu = new AdminMenu();
	private int choice;
	Scanner input = new Scanner(System.in);

	public void showAdminMenu() {
		do {
			System.out.println("\n---------------------------------Admin Menu----------------------------------\n");

			System.out.println("1. See all Customers\n");
			System.out.println("2. See all Rooms\n");
			System.out.println("3. See all Reservations\n");
			System.out.println("4. Add a Room\n");
			System.out.println("5. Back to Main Menu\n");
			System.out.println("6. Exit\n");

			System.out.println("\n-----------------------------------------------------------------------------\n");
			while(true) {
				try {
					choice = input.nextInt();
					input.nextLine();
					break;
				}
				catch(Exception e) {
					System.out.println("Invalid choice, Please enter a valid option \n");
					input.nextLine();
				}
			}

			switch (choice) {
			case 1:
				System.out.println("------------------------------------------------ Customers ------------------------------------------------\n");
				try{
					List<Customer> customers=new ArrayList<>( adminResource.getAllCustomers());
				

				for (Customer customer : customers) {
					System.out.println("\n"+customer+"\n");
				}
				} catch (Exception e) {
					System.out.println("No customers found.\n");
					break;
				}
				
				break;
			case 2:
				System.out.println("------------------------------------------------ Rooms ------------------------------------------------\n");
				try{
					List<IRoom> rooms=new ArrayList<>(adminResource.getAllRooms());
				
				
				for (IRoom room : rooms) {
					System.out.println("\n" + room + "\n");
				}
				}
				catch (Exception e) {
					System.out.println("No rooms found.\n");
						break;
				}
				break;
			case 3:
				System.out.println("------------------------------------------------ Reservations ------------------------------------------------\n");
				try{
					adminResource.displayAllReservation();
				}
				catch (Exception e) {
					System.out.println("No reservations found.\n");
					break;
				}
				break;
			case 4:
				List<IRoom> newRooms = new ArrayList<>();
				boolean more = true;
				do {
					System.out.println("Please enter the room number:\n");
					String roomNumber = input.nextLine(); // this is not validated for number because room number can be alphanumeric or can be named like "suit" or "deluxe".
					if(roomNumber.isEmpty()) {
						System.out.println("Room number cannot be empty. Please enter a valid room number.\n");
						continue; 
					}
						try{
							for(IRoom r: newRooms)
							{
								if( r.getRoomNumber().equals(roomNumber)) {
									throw new IllegalArgumentException("Room with this number already added. Please enter a different room number.\n");
									}                    
							}
						}
						catch(Exception e) {
							System.out.println("Error: " + e.getMessage() + "\n");
							continue;
						}
                    try {
					if (hotelResource.getRoom(roomNumber) != null) {
						System.out.println("Room with this number already exists. Please enter a different room number.\n");
						continue; 
					}
                    }
					catch (Exception e) {
					System.out.println("Please enter the room type [\"Single\" or \"S\" /\"Double\" or \"D\"] :\n");
					String type = input.nextLine().trim().toUpperCase();
				    RoomType roomType; 
				    if(type.equals("SINGLE") ||type.equals("S") ) {
                    	roomType = RoomType.SINGLE;
                    }
                    else if(type.equals("DOUBLE") ||type.equals( "D")){
                    	roomType = RoomType.DOUBLE;
                    }
                    else {
                    	System.out.println("Invalid room type. Please enter either 'Single' or 'Double'.\n");
                    	continue;
                    }
				    
					System.out.println("Please enter the room price:\n");
					double roomPrice;
					try
					{
						roomPrice = input.nextDouble();
						input.nextLine(); // cleaning input buffer
					}
					catch(Exception ex) {
                        System.out.println("Invalid price. Please enter a valid Price.\n");
                        input.nextLine(); 
                        continue;
                    }
					try {
					newRooms.add(new Room(roomNumber, roomType, roomPrice));
					
					System.out.println("Room: "+roomNumber+" added successfully.\n");
					}
					catch(Exception e1) {
                        System.out.println("Error: " + e1.getMessage() + "\n");
                        continue;
                    }
					System.out.println("Do you want to add more rooms? [Y/N] (anything other than \"Y\" will be considered \"NO\" :\n");
					String add = input.nextLine().trim().toUpperCase();
					if (!add.equals("Y")) {
						more = false;
					}
					}
				} while (more);
				
				
				adminResource.addRoom(newRooms);
				System.out.println("All New Rooms Updated");
				break;
				
			
			
			case 5:
				MainMenu mainMenu= MainMenu.getInstance();
				mainMenu.showMainMenu();
				break;
			case 6:
				System.out.println("Do you want to exit?\n");
				System.out.println("Press [Y/N] for Yes or No? (anthing other then Y or YES will be considered NO)*\n");
				String confirm=input.nextLine();
				if(confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("YES")) {
					input.close();
					System.out.println("Exiting...App Stopped!\n");
					System.exit(0);			     		
				}
				else{
					System.out.println("Returning back to the Admin Menu\n");
					continue;
				}
			default:
				System.out.println("Invalid choice, Please enter a valid option\n");

			}
		}
		while(true);

	}

	private AdminMenu(){}

	public static AdminMenu getInstance() {
		return adminMenu;
	}

}
