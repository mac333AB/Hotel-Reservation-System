package models;
import java.util.regex.*;

public class Customer {

	protected String firstName;
	protected String lastName;
	protected String email;
	
	public Customer(String fname,String lname,String uemail){
		
		String regex="^(.+)@(.+).com$";
		Pattern pattern=  Pattern.compile(regex);
		if(!pattern.matcher(uemail).matches()){
			throw new IllegalArgumentException("Please enter a valid email ");
		}
		if (fname.isEmpty() || lname.isEmpty() || uemail.isEmpty()) {
			throw new IllegalArgumentException("All fields are necessary to create a customer.");
		}
		this.firstName=fname;
		this.lastName=lname;
		this.email=uemail;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		
		return "Costumer{ \n First Name= "+ firstName+ 
				"\n Last Name= "+ lastName+ 
				"\n Email= "+email+"\n}";
	}
}