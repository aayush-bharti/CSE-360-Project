package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Doctor {
	private String firstName;
	private String lastName; 
	private String username;
	private String password;
	private ArrayList<Message> messages;
	
	
	public Doctor(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		messages = new ArrayList<Message>();
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
	
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public ArrayList<Message> getMessages(String username, String contact) {
		String contactFolderPath = Database.getContactFolderPath("doctor", username, contact);
		int numberOfMessages = Database.getNumberOfMessages("doctor", username, contact);
		int decrementVariable = numberOfMessages;
		
		for (int i = 0; i < numberOfMessages; i++) {
			String contactFileName = contactFolderPath + File.separator + username + "To" + contact + "_" + decrementVariable + "Message.txt" ; 
			try (Scanner scanner = new Scanner(new File(contactFileName))) {
				String timeString = null;
				String recipient = null;
				String subj = null;
				String message = null;
		        while (scanner.hasNextLine()) {
		        	String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any unintentionally split parts
		            																			 // after the first instance of :
		            switch (key) {
		                case "File Creation Stamp":
		                	timeString = value;
		                    break;
		                case "Recipient":
		                	recipient = value;
		                    break;
		                case "Subject":
		                	subj = value;
		                    break;
		                case "Message Body":
		                	message = value;
		                    break;
		            }
		        }
		        Message createdMessage = new Message(timeString, recipient, subj, message);
		        if (!isDuplicateMessage(createdMessage)) {
		        	addMessage(createdMessage);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Message> uniqueMessages = new HashSet<>();
		for (Message message : messages) {
			uniqueMessages.add(message);
		}
		ArrayList<Message> uniqueMessagesList = new ArrayList<>(uniqueMessages);
		return uniqueMessagesList;
	}
	
	private boolean isDuplicateMessage(Message message) {
	    for (Message existingMessage : messages) {
	        if (existingMessage.equals(message)) {
	            return true;
	        }
	    }
	    return false; 
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}
}