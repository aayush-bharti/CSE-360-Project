package Project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.util.Pair;

public class Database extends Application {
	
	private static LocalDateTime time;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
	
	public void start(Stage primaryStage) {
		createPrimaryFolders();
		System.out.println("========================================");
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet patientResult = statement.executeQuery("SELECT * FROM PatientTable");
            while (patientResult.next()) {
            	String patientUsername = patientResult.getString("Username");
            	createSubFolder("Patient Info", "patient", "Patient-" + patientUsername);
            	createSubFolder("Summaries", "patient", patientUsername);
            	createSubFolder("Vitals", "patient", patientUsername);
            	createSubFolder("Questionnaires", "patient", patientUsername);
            	createSubFolder("Immunizations", "patient", patientUsername);
            	createSubFolder("Prescriptions", "patient", patientUsername);
            	createSubFolder("Physicals", "patient", patientUsername);
            	createSubFolder("Messages", "patient", patientUsername);	
            	System.out.println("---------------------------------------");
            }
            System.out.println("========================================");
            ResultSet nurseResult = statement.executeQuery("SELECT * FROM NurseTable");
            while (nurseResult.next()) {
            	String nurseUsername = nurseResult.getString("Username");
            	createSubFolder("Messages", "nurse", nurseUsername);
            	System.out.println("---------------------------------------");
            }
            System.out.println("========================================");
            ResultSet doctorResult = statement.executeQuery("SELECT * FROM DoctorTable");
            while (doctorResult.next()) {
            	String doctorUsername = doctorResult.getString("Username");
            	createSubFolder("Messages", "doctor", doctorUsername);
            	System.out.println("---------------------------------------");
            }
            System.out.println("========================================");
            connection.close(); 
        } catch (Exception exception) {
        	System.out.println("Start failed");
        }
		Login login = new Login();
		login.start(primaryStage);
	}
	
	/* === Table Update Functions === */
	
	public static HashMap<String, String> updateTable(String username, String phoneNumber, String email, String insurance, String pharmacy) {
	    HashMap<String, String> updateStatus = new HashMap<>();
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM PatientTable WHERE Username = '" + username + "'");
            if (result.next()) {
                Patient patient = patientSearch(username);
                String phoneResult = result.getString("PhoneNumber");
                String emailResult = result.getString("Email");
                String insuranceResult = result.getString("InsuranceProvider");
                String pharmacyResult = result.getString("PreferredPharmacy");

                updateStatus.put("PhoneNumber", updateField(username, phoneNumber, phoneResult, statement, "PhoneNumber", patient));
	            updateStatus.put("Email", updateField(username, email, emailResult, statement, "Email", patient));
	            updateStatus.put("InsuranceProvider", updateField(username, insurance, insuranceResult, statement, "InsuranceProvider", patient));
	            updateStatus.put("PreferredPharmacy", updateField(username, pharmacy, pharmacyResult, statement, "PreferredPharmacy", patient));
	            
	            String userSubFolderPath = getSubFolder("Patient Info", "patient", username);
	            String patientInfoFileName = userSubFolderPath + File.separator + username + "_PatientInfo.txt";
	            updatePatientInfoFile(patientInfoFileName, phoneNumber, email, insurance, pharmacy);
	        } else {
            	showAlert("No User");
            }
            connection.close(); 
        } catch (Exception exception) {
        	showAlert("No User");
        }
	    return updateStatus;
	}
	
	private static String updateField(String username, String newValue, String oldValue, Statement statement, String fieldName, Patient patient) throws Exception {
	    if (!newValue.equals(oldValue)) {
	        statement.executeUpdate("UPDATE PatientTable SET " + fieldName + " = '" + newValue + "' WHERE Username = '" + username + "'");
	        return newValue;
	    } else {
	        return oldValue;
	    }
	}
	
	private static void updatePatientInfoFile(String patientInfoFileName, String phoneNumber, String email, String insurance, String pharmacy) {
	    try {

	        List<String> lines = Files.readAllLines(Paths.get(patientInfoFileName));
	        List<String> updatedLines = new ArrayList<>();
	        for (String line : lines) {
	            if (line.startsWith("Phone Number:")) {
	                updatedLines.add("Phone Number: " + phoneNumber);
	            } else if (line.startsWith("Email:")) {
	                updatedLines.add("Email: " + email);
	            } else if (line.startsWith("Insurance Provider:")) {
	                updatedLines.add("Insurance Provider: " + insurance);
	            } else if (line.startsWith("Preferred Pharmacy:")) {
	                updatedLines.add("Preferred Pharmacy: " + pharmacy);
	            } else {
	                updatedLines.add(line);
	            }
	        }
	        Files.write(Paths.get(patientInfoFileName), updatedLines, StandardCharsets.UTF_8);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}

	/* ==== Search Functions === */
	
	public static Patient patientSearch(String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM PatientTable WHERE Username = '" + username + "'");
            if (result.next()) {
            	String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String DOB = result.getString("DOB");
                String phoneNumber = result.getString("PhoneNumber");
                String email = result.getString("Email");
                String password = result.getString("Password");
                String insurance = result.getString("InsuranceProvider");
                String pharmacy = result.getString("PreferredPharmacy");
                		
                return new Patient(firstName, lastName, DOB, phoneNumber, email, username, password, insurance, pharmacy);
            }
            connection.close(); 
        } catch (Exception exception) {
        	showAlert("No User");
        }
		return null;
	}
	
	public static String associateDoctorSearch(String patientUsername) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT AssociateDoctor FROM PatientTable WHERE Username = '" + patientUsername + "'");
            if (result.next()) {
            	String doctorUsername = result.getString("AssociateDoctor");
                return doctorUsername;
            }
            connection.close(); 
        } catch (Exception exception) {;
        	showAlert("No Doctor Yet");
        }
		return null;
	}
	
	public static Doctor doctorSearch(String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM DoctorTable WHERE Username = '" + username + "'");
            if (result.next()) {
            	String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String password = result.getString("Password");
                return new Doctor(firstName, lastName, username, password);
            } 
            connection.close(); 
        } catch (Exception exception) {
        	showAlert("No User");
        }
		return null;
	}
	
	public static String associateNurseSearch(String patientUsername) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT AssociateNurse FROM PatientTable WHERE Username = '" + patientUsername + "'");
            if (result.next()) {
            	String nurseUsername = result.getString("AssociateNurse");
                return nurseUsername;
            }
            connection.close(); 
        } catch (Exception exception) {
        	showAlert("No Nurse Yet");
        }
		return null;
	}
	
	public static Nurse nurseSearch(String username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM NurseTable WHERE Username = '" + username + "'");
            if (result.next()) {
            	String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String password = result.getString("Password");
                return new Nurse(firstName, lastName, username, password);
            }
            connection.close(); 
            return null;
        } catch (Exception exception) {
        	showAlert("No User");
        }
		return null;
	}
		
	/* === File Functions === */
	
	public static void createSummaryFile(String username, String date, String vitalWeight, String vitalHeight, String vitalTemp, String vitalBP, 
											String physicalQuestion, String mentalQuestion, String immunizationQuestion) {
		String patientSubFolderPath = getSubFolder("Summaries", "patient", "patient");
		if (!patientSubFolderPath.equals("No subfolder")) {	
			if (!date.isEmpty() && !vitalWeight.isEmpty() && !vitalHeight.isEmpty() && !vitalTemp.isEmpty() && !vitalBP.isEmpty() &&
				!physicalQuestion.isEmpty() && !mentalQuestion.isEmpty() && !immunizationQuestion.isEmpty()) {
				
				int fileNumber = getNumberOfFiles(username, "Summaries") + 1;
				String patientInfoFileName = patientSubFolderPath + File.separator + username + "_" + fileNumber + "Summary.txt";       
				try (FileWriter writer = new FileWriter(patientInfoFileName)) {	
					writer.write("Summary Date: " + date + "\n");
					writer.write("vitalWeight: " + vitalWeight + "\n");		
					writer.write("vitalHeight: " + vitalHeight + "\n");		
					writer.write("vitalTemp: " + vitalTemp + "\n");	
					writer.write("vitalBP: " + vitalBP + "\n");		
					writer.write("physicalQuestion: " + physicalQuestion + "\n");		
					writer.write("mentalQuestion: " + mentalQuestion + "\n");		
					writer.write("immunizationQuestion: " + immunizationQuestion + "\n");		
					
					Vitals newVitals = new Vitals(date, vitalWeight, vitalHeight, vitalTemp, vitalBP);
					Questionnaire newQuestionnaire = new Questionnaire(date, physicalQuestion, mentalQuestion, immunizationQuestion);
					Summary summary = new Summary(date, newVitals, newQuestionnaire);
					
					Patient patient = patientSearch(username);
					patient.addSummary(summary);					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
					Statement statement = connection.createStatement();
					statement.executeUpdate("UPDATE PatientTable SET Summary = 1 WHERE Username = '" + username + "'");
				} catch (Exception exception) {
					showAlert("No User");
    
				}
			} else {
				showAlert("Missing Field");
			}
		} else {
			showAlert("Missing Folder");
			return;
		}
	}
	
	public static String createMessageFile(String result, String userType, String username, String recipient, 
											String date, String subject, String messageBody) { 
		if (userType.equals("patientd")) {
			if (patientSearch(recipient) != null) {
				return "Don't create";
			} else if (doctorSearch(recipient) == null) {
				return "Don't create";
			}
		} else if (userType.equals("patientn")) {
			if (patientSearch(recipient) != null) {
				return "Don't create";
			} else if (nurseSearch(recipient) == null) {
				return "Don't create";
			}
		} else {
			if (userType.equals("doctor") || userType.equals("nurse")) {
				if (doctorSearch(recipient) != null || nurseSearch(recipient) != null) {
					return "Don't create";
				}
				if (patientSearch(recipient) == null) {
					return "Don't create";
				}
			}
		}
		
		if (username.equals(recipient)) {
			return "Don't create";
		}
		
		String recipientType = getContactType(userType);
		String senderMessagesFolderPath = getSubFolder("Messages", userType, username);
		String recipientSubFolderPath = getSubFolder("Messages", recipientType, recipient);
		
		File sentDirectory = new File(senderMessagesFolderPath, "Sent");
		File receivedDirectory = new File (recipientSubFolderPath, "Received");	
		if (sentDirectory.exists() && receivedDirectory.exists()) {	
			if (!subject.isEmpty() && !messageBody.isEmpty()) {
				int fileNumber = getNumberOfMessages(userType, username, recipient, "sent") + 1;
				String contactFolderPath = getContactFolderPath(userType, username, recipient, "sent");	
				String recipientFolderPath = getContactFolderPath(recipientType, recipient, username, "received");	
				
				if (contactFolderPath.equals("No Message Contact Folder")) {
					contactFolderPath = createMessageContactFolder(userType, username, recipient).getKey();
					recipientFolderPath = createMessageContactFolder(userType, username, recipient).getValue();
				}				
				if (!contactFolderPath.equals("No Message Contact Folder") && result.equals("Can create")) {
					String contactFileName = contactFolderPath + File.separator + username + "To" + recipient + "_" + fileNumber + "Message.txt" ;  
					try (FileWriter writer = new FileWriter(contactFileName)) {	
						time = LocalDateTime.now();
						String timeString = time.format(formatter);
						writer.write("File Creation Stamp: " + timeString + "\n");
						writer.write("Recipient: " + recipient + "\n");
						writer.write("Subject: " + subject + "\n");
						writer.write("Message Body: " + messageBody + "\n");
						System.out.println("Message for " + userType + " " + username + " has been stored.");					
						Message message = new Message(date, recipient, subject, messageBody);
						if (userType.equals("patientd") || userType.equals("patientn")) {
							Patient patient = patientSearch(username);
							patient.addMessage(userType, message);	
						} else if (userType == "nurse") {
							Nurse nurse = nurseSearch(username);
							nurse.addMessage(message);
						} else if (userType == "doctor") {
							Doctor doctor = doctorSearch(username);
							doctor.addMessage(message);
						}									
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					
					String recipientFileName = recipientFolderPath + File.separator + username + "To" + recipient + "_" + fileNumber + "Message.txt" ;  
					try (FileWriter writer = new FileWriter(recipientFileName)) {	
						time = LocalDateTime.now();
						String timeString = time.format(formatter);
						writer.write("File Creation Stamp: " + timeString + "\n");
						writer.write("Recipient: " + recipient + "\n");
						writer.write("Subject: " + subject + "\n");
						writer.write("Message Body: " + messageBody + "\n");
						System.out.println("Message for " + recipientType + " " + recipient + " has been stored.");					
						Message message = new Message(date, recipient, subject, messageBody);
						if (recipientType == "patient") {
							Patient patient = patientSearch(recipient);
							patient.addReceivedMessage(userType, message);	
						} else if (recipientType == "nurse") {
							Nurse nurse = nurseSearch(recipient);
							nurse.addReceivedMessage(message);
						} else if (recipientType == "doctor") {
							Doctor doctor = doctorSearch(recipient);
							doctor.addReceivedMessage(message);
						}									
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
						Statement statement = connection.createStatement();
						if (userType == "patient") {
							statement.executeUpdate("UPDATE PatientTable SET Messages = 1 WHERE Username = '" + username + "'");	
						} else if (userType == "nurse") {
							statement.executeUpdate("UPDATE NurseTable SET Messages = 1 WHERE Username = '" + username + "'");
						} else if (userType == "doctor") {
							statement.executeUpdate("UPDATE DoctorTable SET Messages = 1 WHERE Username = '" + username + "'");
						}
						connection.close();
					} catch (Exception exception) {
						showAlert("No User");
	    
					}
					return "Created";
				} else {
					createMessageContactFolder(userType, username, recipient);
					return "Can create";
				}
			} else {
				showAlert("Missing Field");
			}
		} else {
			showAlert("Missing Folder");
			return "Don't create";
		}
		return "Don't create";
	}
	
	public static void createVitalsFile(String username, String date, String weight, String height, String temp, String bloodPressure) {
		String patientSubFolderPath = getSubFolder("Vitals", "patient", username);
		if (!patientSubFolderPath.equals("No subfolder")) {	
			if (!date.isEmpty() && !weight.isEmpty() && !height.isEmpty() && !temp.isEmpty() && !bloodPressure.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "Vitals") + 1;
				String patientInfoFileName = patientSubFolderPath + File.separator + username + "_" + fileNumber + "Vitals.txt";       
				try (FileWriter writer = new FileWriter(patientInfoFileName)) {	
					writer.write("Vitals Exam Date: " + date + "\n");
					writer.write("Patient Weight: " + weight + "\n");
					writer.write("Patient Height: " + height + "\n");
					writer.write("Patient Temperature: " + temp + "\n");
					writer.write("Patient Blood Pressure: " + bloodPressure + "\n");
					System.out.println("Vitals for patient " + username + " has been stored.");					
					Vitals vitals = new Vitals(date, weight, height, temp, bloodPressure);
					Patient patient = patientSearch(username);
					patient.addVitals(vitals);					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
					Statement statement = connection.createStatement();
					statement.executeUpdate("UPDATE PatientTable SET Vitals = 1 WHERE Username = '" + username + "'");
				} catch (Exception exception) {
				showAlert("No User");
				}
			} else {
				showAlert("Missing Field");
			}
		} else {
			showAlert("Missing Folder");
			return;
		}
	}
	
	public static void createImmunizationFile(String username, String immunizationQuestion) {
		String patientSubFolderPath = getSubFolder("Immunizations", "patient", username);
		if (!patientSubFolderPath.equals("No subfolder")) {	
			if (!immunizationQuestion.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "Immunizations") + 1;
				String patientInfoFileName = patientSubFolderPath + File.separator + username + "_" + fileNumber + "Immunization.txt";       
				try (FileWriter writer = new FileWriter(patientInfoFileName)) {	
					time = LocalDateTime.now();
					String timeString = time.format(formatter);
					writer.write("File Creation Stamp: " + timeString + "\n");
					writer.write("Question 1 [Any Past Immunizations?]: " + immunizationQuestion + "\n");
					System.out.println("Immunization History for patient " + username + " has been stored.");
					Immunization immunization = new Immunization(timeString, immunizationQuestion);
					Patient patient = patientSearch(username);
					patient.addImmunization(immunization);					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
					Statement statement = connection.createStatement();
					statement.executeUpdate("UPDATE PatientTable SET Immunizations = 1 WHERE Username = '" + username + "'");
				} catch (Exception exception) {
				showAlert("No User");
				}
			} else {
				showAlert("Missing Field");
			}
		} else {
			showAlert("Missing Folder");
			return;
		}
	}
	
	public static void createQuestionnaireFile(String username, String physicalQuestion, String mentalQuestion, String immunizationQuestion) {
		String patientSubFolderPath = getSubFolder("Questionnaires", "patient", username);
		if (!patientSubFolderPath.equals("No subfolder")) {	
			if (!physicalQuestion.isEmpty() && !mentalQuestion.isEmpty() && !immunizationQuestion.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "Questionnaires") + 1;
				String patientInfoFileName = patientSubFolderPath + File.separator + username + "_" + fileNumber + "Questionnaire.txt";       
				try (FileWriter writer = new FileWriter(patientInfoFileName)) {	
					time = LocalDateTime.now();
					String timeString = time.format(formatter);
					writer.write("File Creation Stamp: " + timeString + "\n");
					writer.write("Question 1 [Any Physical Health Concerns?]: " + physicalQuestion + "\n");
					writer.write("Question 2 [Any Mental Health Concerns?]: " + mentalQuestion + "\n");
					writer.write("Question 3 [Any Past Immunizations?]: " + immunizationQuestion + "\n");
					System.out.println("Questionnaire for patient " + username + " has been stored.");
					
					Questionnaire questionnaire = new Questionnaire(timeString, physicalQuestion, mentalQuestion, immunizationQuestion);
					Patient patient = patientSearch(username);
					patient.addQuestionnaire(questionnaire);					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
					Statement statement = connection.createStatement();
					statement.executeUpdate("UPDATE PatientTable SET Questionnaires = 1 WHERE Username = '" + username + "'");
				} catch (Exception exception) {
					showAlert("No User");
				}
			} else {
				showAlert("Missing Field");
			}
		} else {
			showAlert("Missing Folder");
			return;
		}
		createImmunizationFile(username, immunizationQuestion);
	}
	
	public static void createPharmacyFile(String username, String prescription, String phoneNumber, 
											String email, String insurance, String pharmacy) {
		String patientSubFolderPath = getSubFolder("Prescriptions", "patient", username);
		if (!patientSubFolderPath.equals("No subfolder")) {		
			if (!prescription.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty() && 
	        		!insurance.isEmpty() && !pharmacy.isEmpty()) {
				
				int fileNumber = getNumberOfFiles(username, "Prescriptions") + 1;
				String patientInfoFileName = patientSubFolderPath + File.separator + username + "_" + fileNumber + "PharmacyInformation.txt";       
				try (FileWriter writer = new FileWriter(patientInfoFileName)) {
					time = LocalDateTime.now();
					String timeString = time.format(formatter);
					writer.write("File Creation Stamp: " + timeString + "\n");
					writer.write("Prescription: " + prescription + "\n");
					writer.write("Phone Number: " + phoneNumber + "\n");
					writer.write("Email: " + email + "\n");
					writer.write("Insurance Provider: " + insurance + "\n");
					writer.write("Preferred Pharmacy: " + pharmacy + "\n");
					System.out.println("Prescription for patient " + username + " has been stored and sent.");	
					
					Prescription patientPrescription = new Prescription(timeString, prescription, phoneNumber, email, insurance, pharmacy);
					Patient patient = patientSearch(username);
					patient.addPrescription(patientPrescription);	
				} catch (IOException ex) {
				    ex.printStackTrace();
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
		            Statement statement = connection.createStatement();
		            statement.executeUpdate("UPDATE PatientTable SET PharmacyFile = 1 WHERE Username = '" + username + "'");
				} catch (Exception exception) {
		        	showAlert("No User");
		        }
				
			} else {
				showAlert("Missing Field");
			}
		} else {
			System.out.println("Patient folder does not exist.");
			return;
		}
	}
	
	public static void createPhysicalFile(String firstName, String lastName, String DOB, String examDate, 
											String temperature, String heartRate, String bloodPressure, String patientID, 
											String appearanceResults, String earResults, String lungResults, 
											String vascularResults, String appearanceComment, String earComment, 
											String lungComment, String vascularComment) {

		String username = patientID;
		String patientSubFolderPath = getSubFolder("Physicals", "patient", username);
		if (patientSubFolderPath.equals("No subfolder")) {
			createSubFolder("Physicals", "patient", username);
		}
		if (!patientSubFolderPath.equals("No subfolder")) {	
			if (!firstName.isEmpty() && !lastName.isEmpty() && !DOB.isEmpty() && !examDate.isEmpty() && !temperature.isEmpty() &&
					!heartRate.isEmpty() && !bloodPressure.isEmpty() && !patientID.isEmpty() && !appearanceResults.isEmpty() && 
					!earResults.isEmpty() && !lungResults.isEmpty() && !vascularResults.isEmpty() && !appearanceComment.isEmpty() && 
					!earComment.isEmpty() && !lungComment.isEmpty() && !vascularComment.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "Physicals") + 1;
				String patientInfoFileName = patientSubFolderPath + File.separator + username + "_" + fileNumber + "PhysicalInformation.txt";       
				try (FileWriter writer = new FileWriter(patientInfoFileName)) {
					writer.write("First Name: " + firstName + "\n");
					writer.write("Last Name: " + lastName + "\n");
					writer.write("Date of Birth: " + DOB + "\n");
					writer.write("Exam Date: " + examDate + "\n");
					writer.write("Temperature: " + temperature + "\n");
					writer.write("Heart Rate: " + heartRate + "\n");
					writer.write("Blood Pressure: " + bloodPressure + "\n");
					writer.write("Patient ID: " + patientID + "\n");
					writer.write("General Appearance Results: " + appearanceResults + "\n");
					writer.write("Ear, Nose, Throat Results: " + earResults + "\n");
					writer.write("Lungs and Chest Results: " + lungResults + "\n");
					writer.write("Vascular Results: " + vascularResults + "\n");
					writer.write("General Appearance Comments: " + appearanceComment + "\n");
					writer.write("Ear, Nose, Throat Comments: " + earComment + "\n");
					writer.write("Lungs and Chest Comments: " + lungComment + "\n");
					writer.write("Vascular Comments: " + vascularComment + "\n");
					System.out.println("Physical for patient " + patientID + " has been stored.");
					
					Physical physicalExam = new Physical(firstName, lastName, DOB, examDate, temperature, heartRate, bloodPressure, 
															patientID, appearanceComment, earComment, lungComment, vascularComment);
					Patient patient = patientSearch(username);
					patient.addPhysicalExam(physicalExam);					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
		            Statement statement = connection.createStatement();
		            statement.executeUpdate("UPDATE PatientTable SET PhysicalFile = 1 WHERE Username = '" + username + "'");
				} catch (Exception exception) {
		        	showAlert("No User");
		        }
			} else {
				showAlert("Missing Field");
			}
		} else {
			System.out.println("Patient folder does not exist.");
			return;
		}
	}

	public static int getNumberOfMessages(String userType, String username, String recipient, String sentOrReceived) {
		int messageCounter = 0;
		String contactListPath = getContactFolderPath(userType, username, recipient, sentOrReceived);
		File directory = new File(contactListPath);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith("Message.txt")) {
                    messageCounter = messageCounter + 1;
                }
			}
			return messageCounter;
		}
		return 0;
	}
	
	public static int getNumberOfFiles(String username, String folderType) {
		int physicalCounter = 0;
		int pharmacyCounter = 0;
		int questionnaireCounter = 0;
		int immunizationCounter = 0;
		int vitalsCounter = 0;
		int summaryCounter = 0;
		
		String patientSubFolderPath = getSubFolder(folderType, "patient", username); 
		File directory = new File(patientSubFolderPath);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith("PhysicalInformation.txt")) {
                    physicalCounter++;
                } else if (file.isFile() && file.getName().endsWith("PharmacyInformation.txt")) {
                	pharmacyCounter++;
                } else if (file.isFile() && file.getName().endsWith("Questionnaire.txt")) {
                	questionnaireCounter++;
                } else if (file.isFile() && file.getName().endsWith("Immunization.txt")) {
                	immunizationCounter++;
                } else if (file.isFile() && file.getName().endsWith("Vitals.txt")) {
                	vitalsCounter++;
                } else if (file.isFile() && file.getName().endsWith("Summary.txt")) {
                	summaryCounter++;
                }
			}
		}
		switch (folderType) {
			case "Physicals":
				return physicalCounter;
			case "Prescriptions":
				return pharmacyCounter;
			case "Questionnaires":
				return questionnaireCounter;
			case "Immunizations":
				return immunizationCounter;
			case "Vitals":
				return vitalsCounter;
			case "Summaries":
				return summaryCounter;
		}
		return 0;
	}
	
	/* === Folder Functions === */
	
	public static void createPrimaryFolders() {
		try {
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainPatientFolder = new File(currentDirectory, "Patient List");
			File mainNurseFolder = new File(currentDirectory, "Nurse List");
			File mainDoctorFolder = new File(currentDirectory, "Doctor List");
			
			if (mainPatientFolder.mkdir()) {
			    System.out.println("Main patient file repository has been created successfully.");
			} else {
			    System.out.println("Main patient file repository already exists.");
			}
			
			if (mainNurseFolder.mkdir()) {
			    System.out.println("Main nurse file repository has been created successfully.");
			} else {
			    System.out.println("Main nurse file repository already exists.");
			}
			
			if (mainDoctorFolder.mkdir()) {
			    System.out.println("Main doctor file repository has been created successfully.");
			} else {
			    System.out.println("Main doctor file repository already exists.");
			}
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		}
	}
	
	public static String createPatientFolder(String folderName) {
		try {
			if (folderName.equals("patient")) {
				folderName = "Patient-patient";
			}
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Patient List");
			File patientFolder = new File (mainFolder, folderName);
			if (!patientFolder.exists()) {
				patientFolder.mkdir();
			    System.out.println("Patient file repository has been created successfully.");
			} else {
			    System.out.println("Patient file repository already exists.");
			}
			return patientFolder.getPath();
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String createNurseFolder(String folderName) {
		try {
			if (folderName.equals("nurse")) {
				folderName = "Nurse-nurse";
			}
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Nurse List");
			File nurseFolder = new File (mainFolder, folderName);
			if (!nurseFolder.exists()) {
				nurseFolder.mkdir();
			    System.out.println("Nurse file repository has been created successfully.");
			} else {
			    System.out.println("Nurse file repository already exists.");
			}
			return nurseFolder.getPath();
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String createDoctorFolder(String folderName) {
		try {
			if (folderName.equals("doctor")) {
				folderName = "Doctor-doctor";
			}
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Doctor List");
			File doctorFolder = new File (mainFolder, folderName);
			if (!doctorFolder.exists()) {
				doctorFolder.mkdir();
			    System.out.println("Doctor file repository has been created successfully.");
			} else {
			    System.out.println("Doctor file repository already exists.");
			}
			return doctorFolder.getPath();
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static File createSubfolderHelper(String folderType, String userType, String username, String parentFolderPath) {
		String formattedUserType = userType.substring(0, 1).toUpperCase() + userType.substring(1);
		String formattedFolderType = folderType.substring(0, 1).toLowerCase() + folderType.substring(1);
		File parentFolder = new File(parentFolderPath);
		File subFolder = new File(parentFolder, folderType);
		if (!subFolder.exists()) {
			subFolder.mkdir();
			if (folderType.equals("Messages")) {
				File sentFolder = new File (subFolder, "Sent");
				File receivedFolder = new File(subFolder, "Received");
				sentFolder.mkdir();
				receivedFolder.mkdir();
			}
			System.out.println(formattedUserType + " " + username + "'s " + formattedFolderType + " repository has been created successfully.");
		} else {
			System.out.println(formattedUserType + " " + username + "'s " + formattedFolderType + " repository already exists.");
		}
		return new File(parentFolder, folderType);
	}
	
	public static String createSubFolder(String folderType, String userType, String username) {
		String userFolderPath = "";
		if (userType.equals("patient") || userType.equals("patientd") | userType.equals("patientn")) {
			userFolderPath = getPatientFolderPath(username);
			if (userFolderPath.equals("No Patient Folder")) {
				userFolderPath = createPatientFolder(username);
			}
		} else if (userType.equals("nurse")) {
			userFolderPath = getNurseFolderPath(username);
			if (userFolderPath.equals("No Nurse Folder")) {
				userFolderPath = createNurseFolder(username);
			}
		} else if (userType.equals("doctor")) {
			userFolderPath = getDoctorFolderPath(username);
			if (userFolderPath.equals("No Doctor Folder")) {
				userFolderPath = createDoctorFolder(username);
			}
		}
		if (folderType.equals("Patient Info")) {
			File subFolder = createSubfolderHelper("Patient Info", userType, username, userFolderPath);
			return subFolder.getPath();
		}
		if (folderType.equals("Messages")) {
			File subFolder = createSubfolderHelper("Messages", userType, username, userFolderPath);
			return subFolder.getPath();
		} else if (folderType.equals("Summaries")) {
			File subFolder = createSubfolderHelper("Summaries", userType, username, userFolderPath);	
			return subFolder.getPath();
		} else if (folderType.equals("Vitals")) {
			File subFolder = createSubfolderHelper("Vitals", userType, username, userFolderPath);
			return subFolder.getPath();
		} else if (folderType.equals("Questionnaires")) {
			File subFolder = createSubfolderHelper("Questionnaires", userType, username, userFolderPath);
			return subFolder.getPath();
		} else if (folderType.equals("Immunizations")) {
			File subFolder = createSubfolderHelper("Immunizations", userType, username, userFolderPath);
			return subFolder.getPath();
		} else if (folderType.equals("Prescriptions")) {
			File subFolder = createSubfolderHelper("Prescriptions", userType, username, userFolderPath);
			return subFolder.getPath();
		} else if (folderType.equals("Physicals")) {
			File subFolder = createSubfolderHelper("Physicals", userType, username, userFolderPath);
			return subFolder.getPath();
		}
		return "";
	}
	
	public static Pair<String, String> createMessageContactFolder(String userType, String username, String contact) {
		String folderName;
		String currentDirectory = getMessagesFolderPath(userType, username);
		
		String contactType = getContactType(userType);
		String contactDirectory = getMessagesFolderPath(contactType, contact);
		
		
		File sentDirectory = new File (currentDirectory, "Sent");
		File receivedDirectory = new File (contactDirectory, "Received");	
		
		if (username.equals(contact)) {
			return new Pair<>("Should not create.", "Should not create.");
		}
		
		if (userType == "patient" || userType == "patientd" || userType == "patientn") {
			folderName = username + "To" + contact;
			File contactFolder = new File (sentDirectory, folderName);
			File receivedContactFolder = new File (receivedDirectory, folderName);
			if (!contactFolder.exists()) {
				contactFolder.mkdir();
				receivedContactFolder.mkdir();
				System.out.println("Contact repository has been created successfully.");
			} else {
			    System.out.println("Contact repository already exists.");
			}
			return new Pair<>(contactFolder.getPath(), receivedContactFolder.getPath());
		} else if (userType == "nurse") {
			folderName = username + "To" + contact;
			File contactFolder = new File (sentDirectory, folderName);
			File receivedContactFolder = new File (receivedDirectory, folderName);
			
			if (!contactFolder.exists()) {
				contactFolder.mkdir();
				receivedContactFolder.mkdir();
				System.out.println("Contact repository has been created successfully.");
			} else {
			    System.out.println("Contact repository already exists.");
			}
			return new Pair<>(contactFolder.getPath(), receivedContactFolder.getPath());
		} else if (userType == "doctor") {
			folderName = username + "To" + contact;
			File contactFolder = new File (sentDirectory, folderName);
			File receivedContactFolder = new File (receivedDirectory, folderName);
			
			if (!contactFolder.exists()) {
				contactFolder.mkdir();
				receivedContactFolder.mkdir();
				System.out.println("Contact repository has been created successfully.");
			} else {
			    System.out.println("Contact repository already exists.");
			}
		}
		System.out.println("Failed to create contact repository.");
		return new Pair<>("", "");
	}
	
	public static String getMainFolderPath(String folderName) {
		try {
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Patient List");
			if (!mainFolder.mkdir()) { // If the folder exists
				return mainFolder.getPath();
			}
			createPrimaryFolders();
			return "";
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String getDoctorFolderPath(String username) {
		try {
			String folderName = "Doctor-" + username;
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Doctor List");
			File doctorFolder = new File (mainFolder, folderName);
			if (doctorFolder.exists()) { // If the folder exists
				return doctorFolder.getPath();
			}
			return "No Doctor Folder";
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String getNurseFolderPath(String username) {
		try {
			String folderName = "Nurse-" + username;
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Nurse List");
			File nurseFolder = new File (mainFolder, folderName);
			if (nurseFolder.exists()) { // If the folder exists
				return nurseFolder.getPath();
			}
			return "No Nurse Folder";
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String getPatientFolderPath(String username) {
		try {
			String folderName = "Patient-" + username;
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Patient List");
			File patientFolder = new File (mainFolder, folderName);
			if (patientFolder.exists()) { // If the folder exists ...
				return patientFolder.getPath();
			} 
			return "No Patient Folder";
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String getContactFolderPath(String userType, String username, String contact, String sentOrReceived) {
		String messagesFolderPath = getSubFolder("Messages", userType, username);
		if (!messagesFolderPath.equals("No subfolder")) {
			File subFolder;
			String folderName = username + "To" + contact;
			if (sentOrReceived.equals("received")) {
				folderName = contact + "To" + username;
				subFolder = new File (messagesFolderPath, "Received");
			} else {
				subFolder = new File (messagesFolderPath, "Sent");
			}
			File contactFolder = new File (subFolder, folderName);
			if (contactFolder.exists()) { // If the folder exists ...
				return contactFolder.getPath();
			}
			return "No Message Contact Folder";
		}
		return "No Message Contact Folder";
	}
	
	
	public static String getSubfolderHelper(String folderType, String userType, String username, String parentFolderPath) {
		String formattedUserType = userType.substring(0, 1).toUpperCase() + userType.substring(1);
		String formattedFolderType = folderType.substring(0, 1).toLowerCase() + folderType.substring(1);
		File parentFolder = new File(parentFolderPath);
		File subFolder = new File(parentFolder, folderType);
		if (subFolder.exists()) {
			return subFolder.getPath();
		} else {
			System.out.println("No " + formattedFolderType + " subfolder exists for " + formattedUserType + " " + username);
			return "No subfolder";
		}
	}
	
	public static String getSubFolder(String folderType, String userType, String username) {
		String userFolderPath = "";
		if (userType.equals("patient") || userType.equals("patientd") || userType.equals("patientn")) {
			userFolderPath = getPatientFolderPath(username);
		} else if (userType.equals("nurse")) {
			userFolderPath = getNurseFolderPath(username);
		} else if (userType.equals("doctor")) {
			userFolderPath = getDoctorFolderPath(username);
		}
		if (folderType.equals("Patient Info")) {
			String subFolderPath = getSubfolderHelper("Patient Info", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		}
		if (folderType.equals("Messages")) {
			String subFolderPath = getSubfolderHelper("Messages", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		} else if (folderType.equals("Summaries")) {
			String subFolderPath = getSubfolderHelper("Summaries", userType, username, userFolderPath);	
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		} else if (folderType.equals("Vitals")) {
			String subFolderPath = getSubfolderHelper("Vitals", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		} else if (folderType.equals("Questionnaires")) {
			String subFolderPath = getSubfolderHelper("Questionnaires", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		} else if (folderType.equals("Immunizations")) {
			String subFolderPath = getSubfolderHelper("Immunizations", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		} else if (folderType.equals("Prescriptions")) {
			String subFolderPath = getSubfolderHelper("Prescriptions", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		} else if (folderType.equals("Physicals")) {
			String subFolderPath = getSubfolderHelper("Physicals", userType, username, userFolderPath);
			if (!subFolderPath.equals("No subfolder")) {
				File subFolder = new File(subFolderPath);
				return subFolder.getPath();
			}
		}
		return "No subfolder";
	}
	
	public static String getMessagesFolderPath(String userType, String username) {		
		if (userType.equals("doctor")) {
			String doctorFolderPath = getDoctorFolderPath(username);
			if (!doctorFolderPath.equals("No Doctor Folder")) {
				File messagesFolder = new File(doctorFolderPath, "Messages");
				if (messagesFolder.exists()) {
					return messagesFolder.getPath();
				}
			}
		} else if (userType.equals("nurse")) {
			String nurseFolderPath = getNurseFolderPath(username);
			if (!nurseFolderPath.equals("No Nurse Folder")) {
				File messagesFolder = new File(nurseFolderPath, "Messages");
				if (messagesFolder.exists()) {
					return messagesFolder.getPath();
				}
			}
		} else {
			String patientFolderPath = getPatientFolderPath(username);
            if (!patientFolderPath.equals("No Patient Folder")) {
            	File messagesFolder = new File(patientFolderPath, "Messages");
    			if (messagesFolder.exists()) {
    				return messagesFolder.getPath();
    			}
 
            }
		}
		return "No Messages Folder";
	}
	
	/* === Sign In and Sign Up Functions === */
	
	public static void signIn(Stage primaryStage, String username, String password, String lastPressed) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
            Statement statement = connection.createStatement();
            if (!username.isEmpty() && !password.isEmpty()) {
            	if (lastPressed == "") {
            		showAlert("No Portal Selection");
        		} else if (lastPressed == "patient") {
        			ResultSet result = statement.executeQuery("SELECT * FROM PatientTable WHERE Username = '" + username + 
							"' AND Password = '" + password + "'"); 
        			if (result.next()) {
        				Patient patient = patientSearch(username);
        				PatientView patientView = new PatientView(patient);
            			patientView.start(primaryStage);
        			} else {
        				showAlert("No User");
        			}
        		} else if (lastPressed == "nurse") {
        			ResultSet result = statement.executeQuery("SELECT * FROM NurseTable WHERE Username = '" + username + 
							"' AND Password = '" + password + "'"); 
        			if (result.next()) {        				
        				Nurse nurse = nurseSearch(username);                		
        				PatientSearch searchView = new PatientSearch(nurse);
            			searchView.start(primaryStage);
        			} else {
        				showAlert("No User");
        			}
        		} else if (lastPressed == "doctor") {
        			ResultSet result = statement.executeQuery("SELECT * FROM DoctorTable WHERE Username = '" + username + 
							"' AND Password = '" + password + "'"); 
        			if (result.next()) {        				
        				Doctor doctor = doctorSearch(username);
        				Doctor newDoctor = new Doctor (doctor.getFirstName(), doctor.getLastName(), doctor.getUsername(), doctor.getPassword());
        				PatientSearch searchView = new PatientSearch(newDoctor);
            			searchView.start(primaryStage);
        			} else {
        				showAlert("No User");
        			}
        		}
            } else {
            	showAlert("Missing Field");
            }
            connection.close(); 
        } catch (Exception exception) {
        	showAlert("No User");
        	exception.printStackTrace();
        }
	}
	
	public static void signUp(Stage primaryStage, String firstName, String lastName, String DOB, 
								String phoneNumber, String email, String username, String password, 
								String insurance, String pharmacy) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
        	if (!firstName.isEmpty() && !lastName.isEmpty() && !DOB.isEmpty() && 
            		!phoneNumber.isEmpty() && !email.isEmpty() && !username.isEmpty() &&
            		!password.isEmpty() && !insurance.isEmpty() && !pharmacy.isEmpty()) {
      
        		if (validateInput("Date", DOB) && validateInput("Phone Number", phoneNumber) && validateInput("Email", email)) {
        			try {
            		    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PatientTable (" +
            		            "FirstName, LastName, DOB, PhoneNumber, Email, Username, Password, InsuranceProvider, " + 
            		            "PreferredPharmacy, PatientInfoFile, PharmacyFile, PhysicalFile, Questionnaires, Immunizations, Vitals, " + 
            		            "Messages, Summary, AssociateDoctor, AssociateNurse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            		    preparedStatement.setString(1, firstName);
            		    preparedStatement.setString(2, lastName);
            		    preparedStatement.setString(3, DOB);
            		    preparedStatement.setString(4, phoneNumber);
            		    preparedStatement.setString(5, email);
            		    preparedStatement.setString(6, username);
            		    preparedStatement.setString(7, password);
            		    preparedStatement.setString(8, insurance);
            		    preparedStatement.setString(9, pharmacy);
            		    preparedStatement.setString(10, "1");
            		    preparedStatement.setString(11, "0");
            		    preparedStatement.setString(12, "0");
            		    preparedStatement.setString(13, "0");
            		    preparedStatement.setString(14, "0");
            		    preparedStatement.setString(15, "0");
            		    preparedStatement.setString(16, "0");
            		    preparedStatement.setString(17, "0");
            		    preparedStatement.setString(18, "ABC");
            		    preparedStatement.setString(19, "ABC");
            		    int successful = preparedStatement.executeUpdate();
            		    if (successful > 0) {
                		    String patientSubFolderPath = createSubFolder("Patient Info", "patient", "Patient-" + username);
                        	createSubFolder("Summaries", "patient", username);
                        	createSubFolder("Vitals", "patient", username);
                        	createSubFolder("Questionnaires", "patient", username);
                        	createSubFolder("Immunizations", "patient", username);
                        	createSubFolder("Prescriptions", "patient", username);
                        	createSubFolder("Physicals", "patient", username);
                        	createSubFolder("Messages", "patient", username);
                		    String patientInfoFileName = patientSubFolderPath + File.separator + username + "_PatientInfo.txt";       
                		    try (FileWriter writer = new FileWriter(patientInfoFileName)) {
                		        writer.write("First Name: " + firstName + "\n");
                		        writer.write("Last Name: " + lastName + "\n");
                		        writer.write("Date of Birth: " + DOB + "\n");
                		        writer.write("Phone Number: " + phoneNumber + "\n");
                		        writer.write("Email: " + email + "\n");
                		        writer.write("Username: " + username + "\n");
                		        writer.write("Password: " + password + "\n");
                		        writer.write("Insurance Provider: " + insurance + "\n");
                		        writer.write("Preferred Pharmacy: " + pharmacy + "\n");
                		        System.out.println("Patient information saved to file: " + patientInfoFileName);
                		    } catch (IOException ex) {
                		        ex.printStackTrace();
                		    }
                		    Patient patient = new Patient(firstName, lastName, DOB, phoneNumber, email, username, password, insurance, pharmacy);
                		    PatientView patientView = new PatientView(patient);
                		    patientView.start(primaryStage);
            		    }
            		} catch (Exception e) {
            		    showAlert("Already Exists");
            		}
        		} else {
        			showAlert("Invalid Syntax");
        		}
			} else {
				showAlert("Missing Field");
            }
        	connection.close();
	    } catch (Exception e) {
	        showAlert("Already Exists");
	    }
	}
	
	/* === Function to Validate Input === */
	
	public static boolean validateInput(String inputType, String input) {
		switch(inputType) {
			case "Date":
				return input.matches("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$"); // MM/DD/YYYY
			case "Phone Number":
				return input.matches("^\\d{3}-\\d{3}-\\d{4}$"); // XXX-XXX-XXXX
			case "Email":
				return input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$"); // __@__.__
			case "Weight":
				return input.matches("^\\d+(\\.\\d{1,2})?$"); // Allows integer or Decimal to two digits
			case "Height":
				return input.matches("^\\d+(\\.\\d{1,2})?$");  // Allows integer or decimal to two digits
			case "Temperature":
				return input.matches("^\\d+(\\.\\d{1,2})?$");  // Allows integer or Decimal to two digits
			case "Blood Pressure":
				return input.matches("^\\d+$"); // Integer
			case "Heart Rate":
				return input.matches("^\\d+$"); // Integer
		}
		return false;
	}
	
	/* === Function to Display Errors === */
	
	public static void showAlert(String alertType) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		switch (alertType) {
			case "No User":
				alert.setContentText("This user does not exist.");
		        break;
			case "None Assigned":
				alert.setContentText("You are not currently assigned to a doctor or nurse.");
		        break;
			case "No Doctor Yet":
				alert.setContentText("You are not currently assigned to a doctor.");
		        break;
			case "No Nurse Yet":
				alert.setContentText("You are not currently assigned to a nurse.");
		        break;
			case "Already Exists":
				alert.setContentText("This username is taken.");
		        break;
			case "Missing Folder":
				alert.setContentText("SYSERROR: No Folder Exists.");
		        break;
			case "Missing Field":
				alert.setContentText("You are missing a field.");
		        break;
			case "Wrong Portal":
				alert.setContentText("Only a patient can sign up. Select the patient portal.");
			case "No Portal Selection":
				alert.setContentText("Please select a portal prior to sign in.");
		        break;
			case "Invalid Syntax":
				alert.setContentText("One or more fields contains invalid syntax.");
				break;
			case "Left Blank":
				alert.setContentText("Enter a username.");
		        break;
			case "One Per Session":
				alert.setContentText("Only one form submission is allowed per patient visit.");
		}
		alert.showAndWait(); 	
	}
	
	/* === Function to Get Contact Type === */
	
	public static String getContactType(String sendType) {
		if (sendType.equals("doctor") || sendType.equals("nurse")) {
			return "patient";
		} else if (sendType.equals("patientn")) {
			return "nurse";
		} else {
			return "doctor"; 
		}
	}
	
}