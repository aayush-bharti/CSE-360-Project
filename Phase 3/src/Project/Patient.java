package Project;

public class Patient {
	private String firstName;
	private String lastName; 
	private String DOB; 
	private String phoneNumber; 
	private String email; 
	private String username;
	private String password;
	
	public Patient(String firstName, String lastName, String DOB, String phoneNumber, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getDOB() {
		return DOB;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}