package Project;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class Patient {
	private String firstName;
	private String lastName; 
	private String DOB; 
	private String phoneNumber; 
	private String email; 
	private String username;
	private String password;
	private String insurance;
	private String pharmacy;
	private ArrayList<Physical> physicalExams;
	private ArrayList<Prescription> prescriptions;
	private ArrayList<Questionnaire> questionnaires;
	private ArrayList<Immunization> immunizations;
	private ArrayList<Vitals> vitals;
	private ArrayList<Message> doctorMessages;
	private ArrayList<Message> nurseMessages;
	private ArrayList<Message> receivedDoctorMessages;
	private ArrayList<Message> receivedNurseMessages;
	private ArrayList<Summary> summaries;
	
	public Patient(String firstName, String lastName, String DOB, String phoneNumber, String email, String username, String password, String insurance, String pharmacy) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.password = password;
		this.insurance = insurance;
		this.pharmacy = pharmacy;
		physicalExams = new ArrayList<Physical>();
		prescriptions = new ArrayList<Prescription>();
		questionnaires = new ArrayList<Questionnaire>();
		immunizations = new ArrayList<Immunization>();
		vitals = new ArrayList<Vitals>();
		doctorMessages = new ArrayList<Message>();
		nurseMessages = new ArrayList<Message>();
		receivedDoctorMessages = new ArrayList<Message>();
		receivedNurseMessages = new ArrayList<Message>();
		summaries = new ArrayList<Summary>();
	}
		
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
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
	
	public String getInsurance() {
		return insurance;
	}
	
	public String getPharmacy() {
		return pharmacy;
	}
	
	public ArrayList<Physical> getPhysicalExams(String username) {
		int numberOfPhysicals = Database.getNumberOfFiles(username, "Physicals");
		int decrementVariable = numberOfPhysicals;
		
        String patientFolderPath = Database.getSubFolder("Physicals", "patient", username);	
		for (int i = 0; i < numberOfPhysicals; i++) {
			String patientInfoFileName = patientFolderPath + File.separator + username + "_" + decrementVariable + "PhysicalInformation.txt";   
			try (Scanner scanner = new Scanner(new File(patientInfoFileName))) {
		        String examDate = null;
		        String temperature = null;
		        String heartRate = null;
		        String bloodPressure = null;
		        String generalAppearanceResults = null;
		        String entResults = null;
		        String lungsAndChestResults = null;
		        String vascularResults = null;
		        String generalAppearanceComments = null;
		        String entComments = null;
		        String lungsAndChestComments = null;
		        String vascularComments = null;
		
		        while (scanner.hasNextLine()) {
		        	String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any broken parts split after the first instance of :
		
		            switch (key) {
		                case "Exam Date":
		                    examDate = value;
		                    break;
		                case "Temperature":
		                    temperature = value;
		                    break;
		                case "Heart Rate":
		                    heartRate = value;
		                    break;
		                case "Blood Pressure":
		                    bloodPressure = value;
		                    break;
		                case "General Appearance Results":
		                    generalAppearanceResults= value;
		                    break;
		                case "Ear, Nose, Throat Results":
		                    entResults = value;
		                    break;
		                case "Lungs and Chest Results":
		                    lungsAndChestResults = value;
		                    break;
		                case "Vascular Results":
		                    vascularResults = value;
		                    break;
		                case "General Appearance Comments":
		                    generalAppearanceComments = value;
		                    break;
		                case "Ear, Nose, Throat Comments":
		                    entComments = value;
		                    break;
		                case "Lungs and Chest Comments":
		                    lungsAndChestComments = value;
		                    break;
		                case "Vascular Comments":
		                    vascularComments = value;
		                    break;
		            }
		        }
		        Physical exam = new Physical(examDate, Float.parseFloat(temperature), heartRate, bloodPressure, generalAppearanceResults, 
		        							entResults, lungsAndChestResults, vascularResults, generalAppearanceComments, entComments, 
		        							lungsAndChestComments, vascularComments);
		        
		        if (!isDuplicateExam(exam)) {
		        	addPhysicalExam(exam);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Physical> uniquePhysicals = new HashSet<>();
		for (Physical exam : physicalExams) {
			uniquePhysicals.add(exam);
		}
		ArrayList<Physical> uniquePhysicalList = new ArrayList<>(uniquePhysicals);
		return uniquePhysicalList;
	}
	
	public ArrayList<Prescription> getPrescriptions(String username) {
		int numberOfPrescriptions = Database.getNumberOfFiles(username, "Prescriptions");
		int decrementVariable = numberOfPrescriptions;
		
        String patientFolderPath = Database.getSubFolder("Prescriptions", "patient", username);	
		for (int i = 0; i < numberOfPrescriptions; i++) {
			String patientInfoFileName = patientFolderPath + File.separator + username + "_" + decrementVariable + "PharmacyInformation.txt";   
			try (Scanner scanner = new Scanner(new File(patientInfoFileName))) {
		        String fileCreationTime = null;
				String prescriptionBody = null;
		        String phoneNumber = null;
		        String email = null;
		        String insuranceProvider = null;
		        String preferredPharmacy = null;
		        while (scanner.hasNextLine()) {
		        	String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any broken parts split after the first instance of :
		
		            switch (key) {
		            	case "File Creation Stamp":
		            		fileCreationTime = value;
		                case "Prescription":
		                    prescriptionBody = value;
		                    break;
		                case "Phone Number":
		                    phoneNumber = value;
		                    break;
		                case "Email":
		                    email = value;
		                    break;
		                case "Insurance Provider":
		                    insuranceProvider = value;
		                    break;
		                case "Preferred Pharmacy":
		                	preferredPharmacy = value;
		                    break;
		            }
		        }
		        Prescription prescription = new Prescription(fileCreationTime, prescriptionBody, phoneNumber, email, insuranceProvider, preferredPharmacy);
		        if (!isDuplicatePrescription(prescription)) {
		        	addPrescription(prescription);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Prescription> uniquePrescriptions = new HashSet<>();
		for (Prescription prescription : prescriptions) {
			uniquePrescriptions.add(prescription);
		}
		ArrayList<Prescription> uniquePrescriptionList = new ArrayList<>(uniquePrescriptions);
		return uniquePrescriptionList;
	}
	
	public ArrayList<Questionnaire> getQuestionnaires(String username) {
		int numberOfQuestionnaires = Database.getNumberOfFiles(username, "Questionnaires");
		int decrementVariable = numberOfQuestionnaires;
		
        String patientFolderPath = Database.getSubFolder("Questionnaires", "patient", username);	
		for (int i = 0; i < numberOfQuestionnaires; i++) {
			String patientInfoFileName = patientFolderPath + File.separator + username + "_" + decrementVariable + "Questionnaire.txt";   
			try (Scanner scanner = new Scanner(new File(patientInfoFileName))) {
				String fileCreationTime = null;
		        String physicalQuestion = null;
		        String mentalQuestion = null;
		        String immunizationQuestion = null;
		        while (scanner.hasNextLine()) {
		            String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any broken parts split after the first instance of :
		            
		            switch (key) {
			            case "File Creation Stamp":
		            		fileCreationTime = value;
		                case "Question 1 [Any Physical Health Concerns?]":
		                    physicalQuestion = value;
		                    break;
		                case "Question 2 [Any Mental Health Concerns?]":
		                	mentalQuestion = value;
		                    break;
		                case "Question 3 [Any Past Immunizations?]":
		                	immunizationQuestion = value;
		                    break;
		            }
		        }
		        Questionnaire questionnaire = new Questionnaire(fileCreationTime, physicalQuestion, mentalQuestion, immunizationQuestion);
		        if (!isDuplicateQuestionnaire(questionnaire)) {
		        	addQuestionnaire(questionnaire);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Questionnaire> uniqueQuestionnaires = new HashSet<>();
		for (Questionnaire questionnaire : questionnaires) {
			uniqueQuestionnaires.add(questionnaire);
		}
		ArrayList<Questionnaire> uniquePrescriptionList = new ArrayList<>(uniqueQuestionnaires);
		return uniquePrescriptionList;
	}
	
	public ArrayList<Immunization> getImmunizations(String username) {
		int numberOfImmunizations = Database.getNumberOfFiles(username, "Immunizations");
		int decrementVariable = numberOfImmunizations;
		
        String patientFolderPath = Database.getSubFolder("Immunizations", "patient", username);	
		for (int i = 0; i < numberOfImmunizations; i++) {
			String patientInfoFileName = patientFolderPath + File.separator + username + "_" + decrementVariable + "Immunization.txt";   
			try (Scanner scanner = new Scanner(new File(patientInfoFileName))) {
				String fileCreationTime = null;
		        String immunizationQuestion = null;
		        while (scanner.hasNextLine()) {
		        	String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any broken parts split after the first instance of :
		            
		            switch (key) {
			            case "File Creation Stamp":
		            		fileCreationTime = value;
		                case "Question 1 [Any Past Immunizations?]":
		                	immunizationQuestion = value;
		                    break;
		            }
		        }
		        Immunization immunization = new Immunization(fileCreationTime, immunizationQuestion);
		        if (!isDuplicateImmunization(immunization)) {
		        	addImmunization(immunization);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Immunization> uniqueImmunizations = new HashSet<>();
		for (Immunization immunization : immunizations) {
			uniqueImmunizations.add(immunization);
		}
		ArrayList<Immunization> uniqueImmunizationList = new ArrayList<>(uniqueImmunizations);
		return uniqueImmunizationList;
	}
	
	public ArrayList<Vitals> getVitals(String username) {
		int numberOfVitals = Database.getNumberOfFiles(username, "Vitals");
		int decrementVariable = numberOfVitals;
		
		String patientFolderPath = Database.getSubFolder("Vitals", "patient", username);		
		for (int i = 0; i < numberOfVitals; i++) {
			String patientInfoFileName = patientFolderPath + File.separator + username + "_" + decrementVariable + "Vitals.txt";   
			try (Scanner scanner = new Scanner(new File(patientInfoFileName))) {
				String date = null;
				String weight = null;
				String height = null;
				String temperature = null;
				String bloodPressure = null;
		        while (scanner.hasNextLine()) {
		        	String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any broken parts split after the first instance of :
		            
		            switch (key) {
		                case "Vitals Exam Date":
		                	date = value;
		                    break;
		                case "Patient Weight":
		                	weight = value;
		                    break;
		                case "Patient Height":
		                	height = value;
		                    break;
		                case "Patient Temperature":
		                	temperature = value;
		                    break;
		                case "Patient Blood Pressure":
		                	bloodPressure = value;
		                    break;
		            }
		        }
		        Vitals vitals = new Vitals(date, weight, height, temperature, bloodPressure);
		        if (!isDuplicateVitals(vitals)) {
		        	addVitals(vitals);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Vitals> uniqueVitals = new HashSet<>();
		for (Vitals vital : vitals) {
			uniqueVitals.add(vital);
		}
		ArrayList<Vitals> uniqueVitalsList = new ArrayList<>(uniqueVitals);
		return uniqueVitalsList;
	}
	
	public ArrayList<Message> getMessages(String listType, String contactType, String username, String contact) {
		String contactFolderPath = "";
		int numberOfMessages = 0;
		
		if (contactType.equals("patientd")) {
			contactFolderPath = Database.getContactFolderPath("patientd", username, contact, "sent");
			numberOfMessages = Database.getNumberOfMessages("patientd", username, contact, "sent");
		} else if (contactType.equals("patientn")) {
			contactFolderPath = Database.getContactFolderPath("patientn", username, contact, "sent");
			numberOfMessages = Database.getNumberOfMessages("patientn", username, contact, "sent");
		}
		
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
		        if (!isDuplicateMessage(listType, createdMessage)) {
		        	addMessage(listType, createdMessage);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Message> uniqueMessages = new HashSet<>();
		if (listType.equals("doctor")) {
			for (Message message : doctorMessages) {
				uniqueMessages.add(message);
			}
		} else if (listType.equals("nurse")) {
			for (Message message : nurseMessages) {
				uniqueMessages.add(message);
			}
		}
		ArrayList<Message> uniqueMessagesList = new ArrayList<>(uniqueMessages);
		return uniqueMessagesList;
	}


	
	public ArrayList<Message> getReceivedMessages(String listType, String senderType, String patientUsername, String senderUsername) {
		String contactFolderPath = "";
		int numberOfMessages = 0;
		
		if (senderType.equals("patientd")) {
			contactFolderPath = Database.getContactFolderPath("patientd", patientUsername, senderUsername, "received");
			numberOfMessages = Database.getNumberOfMessages("patientd", patientUsername, senderUsername, "received");
		} else if (senderType.equals("patientn")) {
			contactFolderPath = Database.getContactFolderPath("patientn", patientUsername, senderUsername, "received");
			numberOfMessages = Database.getNumberOfMessages("patientn", patientUsername, senderUsername, "received");
		}
		
//		"nurse", "patientn", patient.getUsername(), assignedN.getUsername()
		
		int decrementVariable = numberOfMessages;
		for (int i = 0; i < numberOfMessages; i++) {
			String contactFileName = contactFolderPath + File.separator + senderUsername + "To" + patientUsername + "_" + decrementVariable + "Message.txt" ; 
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
		        if (!isDuplicateReceivedMessage(listType, createdMessage)) {
		        	addReceivedMessage(listType, createdMessage);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Message> uniqueMessages = new HashSet<>();
		if (listType.equals("doctor")) {
			for (Message message : receivedDoctorMessages) {
				uniqueMessages.add(message);
			}
		} else if (listType.equals("nurse")) {
			for (Message message : receivedNurseMessages) {
				uniqueMessages.add(message);
			}
		}
		ArrayList<Message> uniqueMessagesList = new ArrayList<>(uniqueMessages);
		return uniqueMessagesList;
	}
	
	public ArrayList<Summary> getSummaries(String username) {
		int numberOfSummaries = Database.getNumberOfFiles(username, "Summaries");
		int decrementVariable = numberOfSummaries;
		
		String patientFolderPath = Database.getSubFolder("Summaries", "patient", username);	
		for (int i = 0; i < numberOfSummaries; i++) {
			String patientInfoFileName = patientFolderPath + File.separator + username + "_" + decrementVariable + "Summary.txt";   
			try (Scanner scanner = new Scanner(new File(patientInfoFileName))) {
				String date = null;
				String vitalWeight = null;
				String vitalHeight = null;
				String vitalTemp = null;
				String vitalBP = null;
				String physicalQuestion = null;
				String mentalQuestion = null;
				String immunizationQuestion = null;
					
		        while (scanner.hasNextLine()) {
		        	String line = scanner.nextLine();
		            String[] parts = line.split(":");
		            String key = parts[0].trim();
		            String value = parts[1].trim();
		            value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length)).trim(); // Joining any broken parts split after the first instance of :
		            
		            switch (key) {
		                case "Summary Date":
		                	date = value;
		                    break;
		                case "vitalWeight":
		                	vitalWeight = value;
		                    break;
		                case "vitalHeight":
		                	vitalHeight = value;
		                    break;
		                case "vitalTemp":
		                	vitalTemp = value;
		                    break;
		                case "vitalBP":
		                	vitalBP = value;
		                    break;
		                case "physicalQuestion":
		                	physicalQuestion = value;
		                    break;
		                case "mentalQuestion":
		                	mentalQuestion = value;
		                    break;
		                case "immunizationQuestion":
		                	immunizationQuestion = value;
		                    break;
		            }
		        }
		        
		        Vitals newVitals = new Vitals(date, vitalWeight, vitalHeight, vitalTemp, vitalBP);
				Questionnaire newQuestionnaire = new Questionnaire(date, physicalQuestion, mentalQuestion, immunizationQuestion);
				Summary summary = new Summary(date, newVitals, newQuestionnaire);
		        if (!isDuplicateSummary(summary)) {
		        	addSummary(summary);
		        }
		        decrementVariable = decrementVariable - 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		HashSet<Summary> uniqueSummaries = new HashSet<>();
		for (Summary usummary : summaries) {
			uniqueSummaries.add(usummary);
		}
		ArrayList<Summary> uniqueSummaryList = new ArrayList<>(uniqueSummaries);
		return uniqueSummaryList;
	}
	
	private boolean isDuplicateExam(Physical exam) {
	    for (Physical existingExam : physicalExams) {
	        if (existingExam.equals(exam)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private boolean isDuplicatePrescription(Prescription prescription) {
	    for (Prescription existingPrescription : prescriptions) {
	        if (existingPrescription.equals(prescription)) {
	            return true;
	        }
	    }
	    return false; 
	}
	
	private boolean isDuplicateQuestionnaire(Questionnaire questionnaire) {
	    for (Questionnaire existingQuestionnaire : questionnaires) {
	        if (existingQuestionnaire.equals(questionnaire)) {
	            return true;
	        }
	    }
	    return false; 
	}
	
	private boolean isDuplicateImmunization(Immunization immunization) {
	    for (Immunization existingImmunization : immunizations) {
	        if (existingImmunization.equals(immunization)) {
	            return true;
	        }
	    }
	    return false; 
	}
	
	private boolean isDuplicateVitals(Vitals vital) {
	    for (Vitals existingVitals : vitals) {
	        if (existingVitals.equals(vital)) {
	            return true;
	        }
	    }
	    return false; 
	}
	
	private boolean isDuplicateMessage(String listType, Message message) {
	    if (listType.equals("doctor")) {
	    	for (Message existingMessage : doctorMessages) {
		        if (existingMessage.equals(message)) {
		            return true;
		        }
		    }
	    } else if (listType.equals("nurse")) {
	    	for (Message existingMessage : nurseMessages) {
		        if (existingMessage.equals(message)) {
		            return true;
		        }
		    }
	    }
	    return false;
	}
	
	
	private boolean isDuplicateReceivedMessage(String listType, Message message) {
	    if (listType.equals("doctor")) {
	    	for (Message existingMessage : receivedDoctorMessages) {
		        if (existingMessage.equals(message)) {
		            return true;
		        }
		    }
	    } else if (listType.equals("nurse")) {
	    	for (Message existingMessage : receivedNurseMessages) {
		        if (existingMessage.equals(message)) {
		            return true;
		        }
		    }
	    }
	    return false;
	}
	private boolean isDuplicateSummary(Summary summary) {
	    for (Summary existingSummary : summaries) {
	        if (existingSummary.equals(summary)) {
	            return true;
	        }
	    }
	    return false; 
	}
	
	public void addPhysicalExam(Physical exam) {
		physicalExams.add(exam);
    }
	
	public void addPrescription(Prescription prescription) {
		prescriptions.add(prescription);
    }
	
	public void addQuestionnaire(Questionnaire questionnaire) {
		questionnaires.add(questionnaire);
    }
	
	public void addImmunization(Immunization immunization) {
		immunizations.add(immunization);
    }
	
	public void addVitals(Vitals vital) {
		vitals.add(vital);
    }
	
	public void addMessage(String listType, Message message) {
		if (listType.equals("doctor")) {
			doctorMessages.add(message);
		} else if (listType.equals("nurse")) {
			nurseMessages.add(message);
		}
	}
	
	public void addReceivedMessage(String listType, Message message) {
		if (listType.equals("doctor")) {
			receivedDoctorMessages.add(message);
		} else if (listType.equals("nurse")) {
			receivedNurseMessages.add(message);
		}
	}
	
	public void addSummary(Summary summary) {
		summaries.add(summary);
	}
	
}