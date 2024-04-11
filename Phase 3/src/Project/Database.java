package Project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
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

public class Database extends Application {
	
	static LocalDateTime time;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
	
	public void start(Stage primaryStage) {
		createPrimaryFolders();
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
            } else {
            	showAlert("No User");
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
        } catch (Exception exception) {
        	System.out.println("inside");
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
        } catch (Exception exception) {
        	System.out.println("inside1");
        	showAlert("No User");
        }
		return null;
	}
		
	/* === File Functions === */
	
	public static boolean isSameVisit(Questionnaire questionnaire, Vitals vital) {
        LocalDateTime questionnaireDateTime = parseDateTime(questionnaire.getDateTime());
        LocalDateTime vitalDateTime = parseDateTime(vital.getDate());

        // Check if the two datetimes are within 20 minutes of each other
        long minutesDifference = ChronoUnit.MINUTES.between(questionnaireDateTime, vitalDateTime);
        return Math.abs(minutesDifference) <= 20;
    }

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
	
	
	
	public static void createSummaryFile(String username, String date, String vitalWeight, String vitalHeight, String vitalTemp, String vitalBP, 
											String physicalQuestion, String mentalQuestion, String immunizationQuestion) {
		String patientFolderPath = getPatientFolderPath(username);
		if (patientFolderPath != "No Folder") {	
			if (!date.isEmpty() && !vitalWeight.isEmpty() && !vitalHeight.isEmpty() && !vitalTemp.isEmpty() && !vitalBP.isEmpty() &&
				!physicalQuestion.isEmpty() && !mentalQuestion.isEmpty() && !immunizationQuestion.isEmpty()) {
				
				int fileNumber = getNumberOfFiles(username, "summary") + 1;
				String patientInfoFileName = patientFolderPath + File.separator + username + "_" + fileNumber + "Summary.txt";       
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
	
	public static void createMessageFile(String userType, String username, String recipient, 
											String date, String subject, String messageBody) {
		String userFolderPath;
		
		if (userType == "doctor") {
			userFolderPath = getDoctorFolderPath(username);
		} else if (userType ==  "nurse") {
			userFolderPath = getNurseFolderPath(username);
		} else {
			userFolderPath = getPatientFolderPath(username);
		}
		
		if (userFolderPath == "No Folder") {
			System.out.println("New Contact. Creating Folder.");
			userFolderPath = createMessageContactFolder(userType, recipient);
		}
		
		if (userFolderPath != "No Folder") {	
			if (!subject.isEmpty() && !messageBody.isEmpty()) {
				int fileNumber = getNumberOfFiles(userType, username, recipient) + 1;
				String contactFileName = getContactFolderPath(userType, username, recipient) + 
											File.separator + username + "_" + recipient + "_" + fileNumber + "Message.txt" ;   
				try (FileWriter writer = new FileWriter(contactFileName)) {	
					time = LocalDateTime.now();
					String timeString = time.format(formatter);
					writer.write("File Creation Stamp: " + timeString + "\n");
					writer.write("Recipient: " + recipient + "\n");
					writer.write("Subject: " + subject + "\n");
					writer.write("Message Body: " + messageBody + "\n");
					System.out.println("Message for " + userType + " " + username + " has been stored.");					
					Message message = new Message(date, recipient, subject, messageBody);
					if (userType == "patient" ) {
						Patient patient = patientSearch(username);
						patient.addMessage(message);	
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
			} else {
				showAlert("Missing Field");
			}
		} else {
			showAlert("Missing Folder");
			return;
		}
	}
	
	public static void createVitalsFile(String username, String date, String weight, String height, String temp, String bloodPressure) {
		String patientFolderPath = getPatientFolderPath(username);
		if (patientFolderPath != "No Folder") {	
			if (!date.isEmpty() && !weight.isEmpty() && !height.isEmpty() && !temp.isEmpty() && !bloodPressure.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "vitals") + 1;
				String patientInfoFileName = patientFolderPath + File.separator + username + "_" + fileNumber + "Vitals.txt";       
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
		String patientFolderPath = getPatientFolderPath(username);
		if (patientFolderPath != "No Folder") {	
			if (!immunizationQuestion.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "immunization") + 1;
				String patientInfoFileName = patientFolderPath + File.separator + username + "_" + fileNumber + "Immunization.txt";       
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
		String patientFolderPath = getPatientFolderPath(username);
		if (patientFolderPath != "No Folder") {	
			if (!physicalQuestion.isEmpty() && !mentalQuestion.isEmpty() && !immunizationQuestion.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "questionnaire") + 1;
				String patientInfoFileName = patientFolderPath + File.separator + username + "_" + fileNumber + "Questionnaire.txt";       
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
		
		String patientFolderPath = getPatientFolderPath(username);
		if (patientFolderPath != "No Folder") {		
			if (!prescription.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty() && 
	        		!insurance.isEmpty() && !pharmacy.isEmpty()) {
				
				int fileNumber = getNumberOfFiles(username, "pharmacy") ;
				String patientInfoFileName = patientFolderPath + File.separator + username + "_" + fileNumber + "PharmacyInformation.txt";       
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
		String patientFolderPath = getPatientFolderPath(username);
		if (patientFolderPath != "No Folder") {	
			if (!firstName.isEmpty() && !lastName.isEmpty() && !DOB.isEmpty() && !examDate.isEmpty() && !temperature.isEmpty() &&
					!heartRate.isEmpty() && !bloodPressure.isEmpty() && !patientID.isEmpty() && !appearanceResults.isEmpty() && 
					!earResults.isEmpty() && !lungResults.isEmpty() && !vascularResults.isEmpty() && !appearanceComment.isEmpty() && 
					!earComment.isEmpty() && !lungComment.isEmpty() && !vascularComment.isEmpty()) {
				int fileNumber = getNumberOfFiles(username, "physical") + 1;
				String patientInfoFileName = patientFolderPath + File.separator + username + "_" + fileNumber + "PhysicalInformation.txt";       
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

	public static int getNumberOfFiles(String userType, String username, String recipient) {
		int messageCounter = 0;
		String contactListPath = getContactFolderPath(userType, username, recipient);
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
	
	public static int getNumberOfFiles(String username, String filetype) {
		int physicalCounter = 0;
		int pharmacyCounter = 0;
		int questionnaireCounter = 0;
		int immunizationCounter = 0;
		int vitalsCounter = 0;
		int summaryCounter = 0;
		
		String patientFolderPath = getPatientFolderPath(username);
		File directory = new File(patientFolderPath);
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
		switch (filetype) {
			case "physical":
				return physicalCounter;
			case "pharmacy":
				return pharmacyCounter;
			case "questionnaire":
				return questionnaireCounter;
			case "immunization":
				return immunizationCounter;
			case "vitals":
				return vitalsCounter;
			case "summary":
				return summaryCounter;
		}
		showAlert("No User");
		return -1;
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
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Patient List");
			File patientFolder = new File (mainFolder, folderName);
			if (patientFolder.mkdir()) {
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
	
	public static String createMessageContactFolder(String userType, String contact) {
		try {
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder;
			String folderName;
			
			if (userType == "patient") {
				mainFolder = new File(currentDirectory, "Patient List");
				folderName = "doctor- " + contact;
				File contactFolder = new File (mainFolder, folderName);
				if (contactFolder.mkdir()) {
				    System.out.println("Contact repository has been created successfully.");
				} else {
				    System.out.println("Contact repository already exists.");
				}
				return contactFolder.getPath();
			} else if (userType == "nurse") {
				mainFolder = new File(currentDirectory, "Nurse List");
				folderName = "patient- " + contact;
				File contactFolder = new File (mainFolder, folderName);
				if (contactFolder.mkdir()) {
				    System.out.println("Contaact repository has been created successfully.");
				} else {
				    System.out.println("Contact repository already exists.");
				}
				return contactFolder.getPath();
			} else if (userType == "doctor") {
				mainFolder = new File(currentDirectory, "Doctor List");
				folderName = "patient- " + contact;
				File contactFolder = new File (mainFolder, folderName);
				if (contactFolder.mkdir()) {
				    System.out.println("Contaact repository has been created successfully.");
				} else {
				    System.out.println("Contact repository already exists.");
				}
				return contactFolder.getPath();
			}
			System.out.println("Failed to create contact repository.");
			return "";
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String getMainFolderPath(String folderName) {
		try {
			String currentDirectory = new File(URLDecoder.decode(Database.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
			File mainFolder = new File(currentDirectory, "Patient List");
			if (!mainFolder.mkdir()) { // If the folder exists
				return mainFolder.getPath();
			}
			return "No folder";
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
			if (!doctorFolder.mkdir()) { // If the folder exists
				return doctorFolder.getPath();
			}
			return "No folder";
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
			if (!nurseFolder.mkdir()) { // If the folder exists
				return nurseFolder.getPath();
			}
			return "No folder";
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
			if (!patientFolder.mkdir()) { // If the folder exists ...
				return patientFolder.getPath();
			}
			return "No folder";
		} catch (UnsupportedEncodingException e) {
		    System.out.println("Failed to decode path: " + e.getMessage());
		    return "";
		}
	}
	
	public static String getContactFolderPath(String userType, String username, String contact) {
		if (userType.equals("doctor") || userType.equals("doctor")) {
			String folderName = "patient-" + contact;
			if (userType == "doctor") {
				String doctorFolderPath = getDoctorFolderPath(username);
				File subFolder = new File(doctorFolderPath, folderName);
				if (!subFolder.mkdir()) { // If the folder exists ...
					return subFolder.getPath();
				}
				return "No folder";
			} else if (userType == "nurse") {
				String nurseFolderPath = getNurseFolderPath(username);
				File subFolder = new File(nurseFolderPath, folderName);
				if (!subFolder.mkdir()) { // If the folder exists ...
					return subFolder.getPath();
				}
				return "No folder";
			}
			return "No folder";
		} else {
			if (userType.equals("patientd")) {
				String folderName = "doctor-" + contact;
				String patientFolderPath = getPatientFolderPath(username);
				File subFolder = new File(patientFolderPath, folderName);
				if (!subFolder.mkdir()) { // If the folder exists ...
					return subFolder.getPath();
				}
				return "No folder";
			} else if (userType.equals("patientn")) {
				String folderName = "nurse-" + contact;
				String patientFolderPath = getPatientFolderPath(username);
				File subFolder = new File(patientFolderPath, folderName);
				if (!subFolder.mkdir()) { // If the folder exists ...
					return subFolder.getPath();
				}
				return "No folder";
			}
			return "No folder";
		}
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
        				Patient newPatient = new Patient (patient.getFirstName(), patient.getLastName(), patient.getDOB(), 
        													patient.getPhoneNumber(), patient.getEmail(), patient.getUsername(), 
        													patient.getPassword(), patient.getInsurance(), patient.getPharmacy());
        				PatientView patientView = new PatientView(newPatient);
            			patientView.start(primaryStage);
        			} else {
        				showAlert("No User");
        			}
        		} else if (lastPressed == "nurse") {
        			ResultSet result = statement.executeQuery("SELECT * FROM NurseTable WHERE Username = '" + username + 
							"' AND Password = '" + password + "'"); 
        			if (result.next()) {        				
        				Nurse nurse = nurseSearch(username);
        				Nurse newNurse = new Nurse (nurse.getFirstName(), nurse.getLastName(), nurse.getUsername(), nurse.getPassword());        		
                		
        				PatientSearch searchView = new PatientSearch(newNurse);
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
        		
            	try {
            		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PatientTable " +
            				"(FirstName, LastName, DOB, PhoneNumber, Email, Username, Password, InsuranceProvider, " + 
            				"PreferredPharmacy, PatientInfoFile, PharmacyFile, PhysicalFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            		preparedStatement.executeUpdate();
					
					String patientFolder = "Patient-" + username;
					String patientFolderPath = createPatientFolder(patientFolder);
					String patientInfoFileName = patientFolderPath + File.separator + username + "_PatientInfo.txt";       
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
				} catch (Exception e) {
			        e.printStackTrace();
				}
			} else {
				showAlert("Missing Field");
            }
        	connection.close();
	    } catch (Exception e) {
	        showAlert("Already Exists");
	    }
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
		}
		alert.showAndWait(); 	
	}
}