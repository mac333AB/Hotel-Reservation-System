package menuUI;
import java.util.*;
import java.util.regex.Pattern;
import java.text.*;
import models.*;
import api.AdminResource;
import api.HotelResource;

public class MainMenu {

	private static final MainMenu mainMenu= new MainMenu();
	private int choice;
	AdminResource adminResource = AdminResource.getInstance();
	HotelResource hotelResource =  HotelResource.getInstance();
	Scanner input= new Scanner(System.in);

	public void showMainMenu() {
		do {
			while(true) {
				try {
					System.out.println("\n----------------------------------Main Menu----------------------------------\n");

					System.out.println("1. Find and reserve a room\n");
					System.out.println("2. See my reservations\n");
					System.out.println("3. Create an account\n");
					System.out.println("4. Admin\n");
					System.out.println("5. Exit\n");			


					System.out.println("\n-----------------------------------------------------------------------------\n");


					choice = input.nextInt();
					input.nextLine();
					break;
				}
				catch(Exception e) {
					System.out.println("Invalid choice, Please enter a valid option ");
					input.nextLine();
				}
			}

			switch(choice) {
			case 1:	
				System.out.println("Enter Your email : \n");
				String userEmail=input.nextLine();
				String regex="^(.+)@(.+).com$";
				Pattern pattern=  Pattern.compile(regex);
				if(!pattern.matcher(userEmail).matches()){
					System.out.println("Please enter a valid email");
					continue;
				}
				
				Customer customer;
				try {
					customer=hotelResource.getCostumer(userEmail);
				} catch (Exception e) {
					System.out.println("No account found with this email. Please create an account first.\n");
					continue;
				}
				
				
				System.out.println("Enter Check-In Date dd-mm-yyyy :\n");
				String checkIn=input.nextLine();
				System.out.println("Enter Check-Out Date dd-mm/yyyy :\n");
				String checkOut=input.nextLine();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				 sdf.setLenient(false);
				 Date checkOutDate;
				 Date checkInDate;
			        try {
			        	checkInDate=sdf.parse(checkIn);
			            checkOutDate=sdf.parse(checkOut);
			            			              
			        } catch (Exception e) {
						System.out.println(e.getMessage() + "\nInvalid date format or value. Please enter date in dd/mm/yyyy format.\n");
			            continue;
			        }
			        
                	 System.out.println("Enter the room number you want to book :\n");
                	 String roomNumber=input.nextLine();
                	 if(roomNumber.isEmpty()) {
                		 System.out.println("Room number cannot be empty. Returning to Main Menu.\n");
                		 continue;
                	 }
                	 try {
                		 IRoom room=hotelResource.getRoom(roomNumber);
                		 Reservation reservation= hotelResource.bookARoom(userEmail, room, checkInDate, checkOutDate);
                		 System.out.println("Room booked successfully! Here are your reservation details:\n");
                		 System.out.println(reservation);
                	 } catch (Exception e) {
                		 System.out.println("Error: " + e.getMessage() + "\n");
                		 continue;
                	 }
			     	   
				
				
			break;
			case 2: System.out.println("case 2 \n");
				System.out.println("Enter your Email :\n");
				String email = input.nextLine();
				try {
					hotelResource.getCostumer(email);
					System.out.println("Here are your reservations:\n");
					for (Reservation res : hotelResource.getCustomersReservations(email)) {
						System.out.println(res);
					}
				} 
				catch (Exception e) {
					System.out.println( e.getMessage() + "\n");
				}
				
			break;
			case 3: 
				System.out.println("Enter your Email :\n");
				String userEmail1=input.nextLine().trim();
				System.out.println("Enter your First Name :\n");
				String firstName=input.nextLine().trim();
				System.out.println("Enter your Last Name :\n");
				String lastName=input.nextLine().trim();
				try {
					hotelResource.getCostumer(userEmail1);
					System.out.println("An account with this email already exists. Please try again with a different email. \n");
					 continue;
					
				} catch (Exception e) {
					    System.out.println("Creating a new account...\n");
						try {
							hotelResource.createACustomer(userEmail1, firstName, lastName);
							System.out.println("Account created successfully!\n");
						}
						catch (IllegalArgumentException ex) {
							System.out.println("Error: " + ex.getMessage() + "\n");
							continue;
						}
				}
				
			break;
			case 4: AdminMenu admin= AdminMenu.getInstance();
			admin.showAdminMenu();
			break;
			case 5:	System.out.println("Do you want to exit?\n");
					System.out.println("Press [Y/N] for Yes or No? (anthing other then Y or YES will be considered NO)*\n");
					String confirm1=input.nextLine();
					if(confirm1.equalsIgnoreCase("Y") || confirm1.equalsIgnoreCase("YES")) {
						input.close();
						System.out.println("Exiting...App Stopped!");
						System.exit(0);			     		
					}
					else{
						System.out.println("Returning back to the Main Menu");
						continue;
					}
			default: System.out.println("Invalid choice, Please enter a valid option");
			}

		}
		while(true);

	}

	private MainMenu() {};

	public static MainMenu getInstance() {
		return mainMenu;
	}
}
