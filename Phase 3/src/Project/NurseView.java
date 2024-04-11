// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Nurse View page for phase 3

package Project;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NurseView extends Application {
	private Nurse nurse;
	private Patient patient;
	
	static LocalDateTime time;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
	
	boolean createdVitals;
	boolean createdQuestionnaire;
	Vitals vitalsObj = null;
	Questionnaire questionnaireObj = null;
	
	public NurseView(Nurse nurse) {
		this.nurse = nurse;
	}
	
	public NurseView(Nurse nurse, Patient patient) {
		this.nurse = nurse;
		this.patient = patient;
	}

	public void start(Stage primaryStage) {
		// the root to hold everything
		BorderPane root = new BorderPane();
		
		// creates the right and left vboxes for the display
		VBox right = new VBox();
		VBox left = new VBox();

		// sets up the left vbox by making all the labels and buttons in it
		Label patientName = new Label(patient.getFullName());
		patientName.setStyle("-fx-text-fill: #00005a;");
		patientName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		
		Button questionsTab = new Button("QUESTIONS");
		questionsTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		questionsTab.setPrefWidth(175);
		
		Button vitalsTab = new Button("VITALS");
		vitalsTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vitalsTab.setPrefWidth(175);
		
		Button historyTab = new Button("HISTORY");
		historyTab.setPrefWidth(175);
		historyTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		Button messagesTab = new Button("MESSAGES");
		messagesTab.setPrefWidth(175);
		messagesTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		Button back = new Button("BACK");
		back.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		back.setPrefWidth(175);
		// event handler for the back button
		back.setOnAction(new EventHandler<>() {
	        	public void handle(ActionEvent event)
	        	{
	        		Login log = new Login();
	        		log.start(primaryStage);
	        	}
        	}
        	);

		// questions is clicked eventhandler
		questionsTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					questionsTab.setStyle("");
					vitalsTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the questions button so it shows that it is clicked
				questionsTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// label and text area to enter first question for the patient
				Label question1Label = new Label("Any Physical Health Concerns?");
				question1Label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea q1Field = new TextArea();
				q1Field.setMaxWidth(700);
				q1Field.setPrefHeight(100);
				
				// label for the 2nd question and field
				Label question2Label = new Label("Any Mental Health Concerns?");
				question2Label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea q2Field = new TextArea();
				q2Field.setMaxWidth(700);
				q2Field.setPrefHeight(100);
				
				// label for the 3rd question and field
				Label question3Label = new Label("Any Past Immunizations?");
				question3Label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea q3Field = new TextArea();
				q3Field.setMaxWidth(700);
				q3Field.setPrefHeight(100);

				// save button to preserve answers
				Button save = new Button("SAVE");
				save.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				save.setPrefWidth(175);
				save.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				save.setTranslateX(275);
				save.setTranslateY(30);

				// checks if all the fields needed are filled and if so it will save answers
				save.setOnAction(new EventHandler<>() {
			        	public void handle(ActionEvent event) {
			        		Database.createQuestionnaireFile(patient.getUsername(), q1Field.getText(), 
			        											q2Field.getText(), q3Field.getText());
			        		questionnaireObj = new Questionnaire(patient.getUsername(), q1Field.getText(), 
			        												q2Field.getText(), q3Field.getText());
			        		
			        		if (vitalsObj != null && questionnaireObj != null) {
			        			Database.createSummaryFile(patient.getUsername(), questionnaireObj.getDateTime(), vitalsObj.getWeight(), 
			        										vitalsObj.getHeight(), vitalsObj.getTemperature(), vitalsObj.getBloodPressure(),
			        										questionnaireObj.getPhysicalQuestion(), questionnaireObj.getMentalQuestion(),
			        										questionnaireObj.getImmunizationQuestion());
			        			vitalsObj = null;
			        			questionnaireObj = null;
			        		}
			        	}
			        });
				
				Button clear = new Button("CLEAR");
				clear.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				clear.setPrefWidth(175);
				clear.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				clear.setTranslateX(275);
				clear.setTranslateY(30);
				//clear button exception handler
				clear.setOnAction(new EventHandler<>() {
			        	public void handle(ActionEvent event) {
			        		q1Field.clear();
			        		q2Field.clear();
			        		q3Field.clear();
			        	}
			        });
				
				//hbox for clear and save
				HBox questionButtons = new HBox();	
				questionButtons.getChildren().addAll(clear,save);
				questionButtons.setSpacing(50);
				questionButtons.setTranslateX(-80);
				questionButtons.setTranslateY(20);
				
				// adds everything to the right vbox so it can be displayed
				right.getChildren().addAll(question1Label, q1Field, question2Label, q2Field, question3Label, q3Field, questionButtons);	
				right.setSpacing(15);
			}
		});
		
		// event handler for if the vitals button is clicked
		vitalsTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox for next steps
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					questionsTab.setStyle("");
					vitalsTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the vitals button so it shows that it is clicked
				vitalsTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates hboxes for the labels and  for the text fields to display information 
				HBox vitalsLabels = new HBox();
				HBox vitalsFields = new HBox();
				
				// creates all the labels and their respective fields to fill in the info for the exam so that it can be added to the hboxes
				//date label and field
				Label dateLabel = new Label("Date:        ");
				dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField dateField = new TextField();
				dateField.setTranslateY(-10);
				dateField.setMaxWidth(85);
				
				//weight label and field
				Label weightLabel = new Label("Weight (lb):");
				weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				weightLabel.setTranslateX(20);
				TextField weightField = new TextField();
				weightField.setTranslateY(-10);
				weightField.setMaxWidth(90);
				
				//height label and field
				Label heightLabel = new Label("Height (in):");
				heightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				heightLabel.setTranslateX(32);
				TextField heightField = new TextField();
				heightField.setTranslateY(-10);
				heightField.setMaxWidth(90);
				
				//body temp label and field
				Label tempLabel = new Label("Body Temperature (F):");
				tempLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				tempLabel.setTranslateX(47);
				TextField tempField = new TextField();
				tempField.setTranslateY(-10);
				tempField.setMaxWidth(150);
				
				//blood pressure label and field
				Label bpLabel = new Label("Blood Pressure (mmHg):");
				bpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				bpLabel.setTranslateX(49);
				TextField bpField = new TextField();
				bpField.setTranslateY(-10);
				bpField.setMaxWidth(150);
				
				// adds the labels and their respective fields to the hboxes
				vitalsLabels.getChildren().addAll(dateLabel, weightLabel, heightLabel, tempLabel,bpLabel);
				vitalsLabels.setSpacing(30);
				vitalsFields.getChildren().addAll(dateField, weightField, heightField, tempField,bpField);
				vitalsFields.setSpacing(30);
	
				// creates an hbox for the clear and save buttons
				HBox buttons = new HBox();
				
				// creates the clear button
				Button clear = new Button("CLEAR");
				clear.setPrefWidth(175);
				clear.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				clear.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				clear.setTranslateY(-20);
				// clears all editable textfields and the radio buttons
				clear.setOnAction(new EventHandler<>() {
			        	public void handle(ActionEvent event) {
			        		dateField.clear();
			        		weightField.clear();
			        		heightField.clear();
			        		tempField.clear();
			        		bpField.clear();
			        	}
		       	 	});
				
				// creates the save button to save the information from the vital exam
				Button save = new Button("SAVE");
				save.setPrefWidth(175);
				save.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				save.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				save.setTranslateY(-20);
				// checks if all the fields are inputed and saves the information
				save.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		Database.createVitalsFile(patient.getUsername(), dateField.getText(), weightField.getText(), 
		        									heightField.getText(), tempField.getText(), bpField.getText());
		        		
		        		vitalsObj = new Vitals(dateField.getText(), weightField.getText(), heightField.getText(), 
		        								tempField.getText(), bpField.getText());
		        		if (vitalsObj != null && questionnaireObj != null) {
		        			Database.createSummaryFile(patient.getUsername(), vitalsObj.getDate(), vitalsObj.getWeight(), 
		        										vitalsObj.getHeight(), vitalsObj.getTemperature(), vitalsObj.getBloodPressure(),
		        										questionnaireObj.getPhysicalQuestion(), questionnaireObj.getMentalQuestion(),
		        										questionnaireObj.getImmunizationQuestion());
		        			vitalsObj = null;
		        			questionnaireObj = null;
		        		}
		        	}
	       	 	});
				
				// adds both buttons to the hbox 
				buttons.getChildren().addAll(clear, save);
				buttons.setSpacing(50);
				buttons.setTranslateX(150);
				buttons.setTranslateY(30);
				vitalsLabels.setTranslateX(-30);
				vitalsFields.setTranslateX(-30);
				
				// creates a table and all of its columns
				
				TableView<Vitals> vitalsTable = new TableView<Vitals>();
				TableColumn<Vitals, String> vdateCol = new TableColumn<Vitals, String>("Date");
				TableColumn<Vitals, String> vweightCol = new TableColumn<Vitals, String>("Weight (lb)");
				TableColumn<Vitals, String> vheightCol = new TableColumn<Vitals, String>("Height (in)");
				TableColumn<Vitals, String> vtempCol = new TableColumn<Vitals, String>("Body Temperature (F)");
				TableColumn<Vitals, String> vbpCol = new TableColumn<Vitals, String>("Blood Pressure (mmHg");	
				
				vdateCol.setCellValueFactory(new PropertyValueFactory<Vitals, String>("date"));
				vweightCol.setCellValueFactory(new PropertyValueFactory<Vitals, String>("weight"));
				vheightCol.setCellValueFactory(new PropertyValueFactory<Vitals, String>("height"));
				vtempCol.setCellValueFactory(new PropertyValueFactory<Vitals, String>("temperature"));
				vbpCol.setCellValueFactory(new PropertyValueFactory<Vitals, String>("bloodPressure"));

				// adds every column to the table and sets their widths
				vitalsTable.getColumns().addAll(vdateCol, vweightCol, vheightCol, vtempCol, vbpCol);
				vitalsTable.setMaxWidth(800);
				vdateCol.setPrefWidth(150);
				vweightCol.setPrefWidth(150);
				vheightCol.setPrefWidth(150);
				vtempCol.setPrefWidth(150);
				vbpCol.setPrefWidth(150);
				vitalsTable.setTranslateX(-30);
				
				vitalsTable.getItems().clear();
				vitalsTable.refresh();
				
				ArrayList<Vitals> vitals = patient.getVitals(patient.getUsername());
				if (vitals != null) {
					for (Vitals vital : vitals) {
						String vitalsDate = vital.getDate();
						String weight = vital.getWeight();
						String height = vital.getHeight();
						String temperature = vital.getTemperature();
						String bloodPressure = vital.getBloodPressure();

						System.out.println(vitalsDate);
						System.out.println(weight);
						System.out.println(height);
						System.out.println(temperature);
						System.out.println(bloodPressure);
						System.out.println("===============");
					}
					for (Vitals vital : vitals) {
						String vitalsDate = vital.getDate();
						String weight = vital.getWeight();
						String height = vital.getHeight();
						String temperature = vital.getTemperature();
						String bloodPressure = vital.getBloodPressure();
						vitalsTable.getItems().add(new Vitals(vitalsDate, weight, height, temperature, bloodPressure));
					}
				}
				
				// adds everything to the right vbox and displays it
				right.getChildren().addAll(vitalsLabels,vitalsFields, buttons, vitalsTable);
				right.setSpacing(20);
			}
		});
		
		// event handler for if the history button is clicked
		historyTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					questionsTab.setStyle("");
					vitalsTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the history button so it shows that it is clicked
				historyTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates a table and all of its columns
				TableView<Physical> physicalTable = new TableView<Physical>();
				TableColumn<Physical, String> dateCol = new TableColumn<Physical, String>("Date");
				TableColumn<Physical, Integer> tempCol = new TableColumn<Physical, Integer>("Body Temperature(F)");
				TableColumn<Physical, String> heartRateCol = new TableColumn<Physical, String>("Heart Rate (bpm)");
				TableColumn<Physical, String> bpCol = new TableColumn<Physical, String>("Blood Pressure(mmHg)");				
				TableColumn<Physical, String> genResultCol = new TableColumn<Physical, String>("General Appearance");
				TableColumn<Physical, String> entResultCol = new TableColumn<Physical, String>("Ear, Nose, and Throat");
				TableColumn<Physical, String> lungResultCol = new TableColumn<Physical, String>("Chest and Lungs");
				TableColumn<Physical, String> vascularResultCol = new TableColumn<Physical, String>("Vascular");				
				
				dateCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("examDate"));
				tempCol.setCellValueFactory(new PropertyValueFactory<Physical, Integer>("temperature"));
				heartRateCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("heartRate"));
				bpCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("bloodPressure"));
				genResultCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("appearanceResult"));
				entResultCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("entResult"));
				lungResultCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("lungResult"));
				vascularResultCol.setCellValueFactory(new PropertyValueFactory<Physical, String>("vascularResult"));

				// adds every column to the table and sets their widths
				physicalTable.getColumns().addAll(dateCol, tempCol, heartRateCol, bpCol, genResultCol, entResultCol, lungResultCol, vascularResultCol);
				physicalTable.setMaxWidth(800);
				dateCol.setPrefWidth(60);
				tempCol.setPrefWidth(70);
				heartRateCol.setPrefWidth(70);
				bpCol.setPrefWidth(150);
				genResultCol.setPrefWidth(120);
				entResultCol.setPrefWidth(130);
				lungResultCol.setPrefWidth(100);
				vascularResultCol.setPrefWidth(100);
				physicalTable.setTranslateX(-30);
				
				physicalTable.getItems().clear();
				physicalTable.refresh();
				ArrayList<Physical> physicalExams = patient.getPhysicalExams(patient.getUsername());
				if (physicalExams != null) {
					for (Physical exam : physicalExams) {
						String examDate = exam.getExamDate();
						String temperature = Integer.toString(exam.getTemperature());
						String heartRate = exam.getHeartRate();
						String bloodPressure = exam.getBloodPressure();
						String appearanceResult = exam.getAppearanceResult();
						String entResult = exam.getEntResult();
						String lungResult = exam.getLungResult();
						String vascularResult = exam.getVascularResult();
						System.out.println(examDate);
						System.out.println(temperature);
						System.out.println(heartRate);
						System.out.println(bloodPressure);
						System.out.println(appearanceResult);
						System.out.println(entResult);
						System.out.println(lungResult);
						System.out.println(vascularResult);
						System.out.println("===============");
					}
					for (Physical exam : physicalExams) {
						String examDate = exam.getExamDate();
						int temperature = exam.getTemperature();
						String heartRate = exam.getHeartRate();
						String bloodPressure = exam.getBloodPressure();
						String appearanceResult = exam.getAppearanceResult();
						String entResult = exam.getEntResult();
						String lungResult = exam.getLungResult();
						String vascularResult = exam.getVascularResult();
						physicalTable.getItems().add(new Physical(examDate, temperature, heartRate, bloodPressure, appearanceResult, entResult, lungResult, vascularResult));
					}
				}
				
				TableView<Questionnaire> questionnaireTable = new TableView<Questionnaire>();
				TableColumn<Questionnaire, String> dateColumn = new TableColumn<Questionnaire, String>("Date");
				TableColumn<Questionnaire, String> physicalCol = new TableColumn<Questionnaire, String>("Physical Question Response");
				TableColumn<Questionnaire, String> mentalCol = new TableColumn<Questionnaire, String>("Mental Question Response");
				TableColumn<Questionnaire, String> immunizationCol = new TableColumn<Questionnaire, String>("Immunization Question Response");						
				
				dateColumn.setCellValueFactory(new PropertyValueFactory<Questionnaire, String>("dateTime"));
				physicalCol.setCellValueFactory(new PropertyValueFactory<Questionnaire, String>("physicalQuestion"));
				mentalCol.setCellValueFactory(new PropertyValueFactory<Questionnaire, String>("mentalQuestion"));
				immunizationCol.setCellValueFactory(new PropertyValueFactory<Questionnaire, String>("immunizationQuestion"));

				// adds every column to the table and sets their widths
				questionnaireTable.getColumns().addAll(dateColumn, physicalCol, mentalCol, immunizationCol);
				questionnaireTable.setMaxWidth(800);
				dateColumn.setPrefWidth(150);
				physicalCol.setPrefWidth(150);
				mentalCol.setPrefWidth(150);
				immunizationCol.setPrefWidth(150);
				questionnaireTable.setTranslateX(-30);
				
				questionnaireTable.getItems().clear();
				questionnaireTable.refresh();
				ArrayList<Questionnaire> questionnaires = patient.getQuestionnaires(patient.getUsername());
				if (questionnaires != null) {
					for (Questionnaire questionnaire : questionnaires) {
						String questionnaireTime = questionnaire.getDateTime();
						String physicalQuestion = questionnaire.getPhysicalQuestion();
						String mentalQuestion = questionnaire.getMentalQuestion();
						String immunizationQuestion = questionnaire.getImmunizationQuestion();
						System.out.println(questionnaireTime);
						System.out.println(physicalQuestion);
						System.out.println(mentalQuestion);
						System.out.println(immunizationQuestion);
						System.out.println("=======================");
					}

					for (Questionnaire questionnaire : questionnaires) {
						String questionnaireTime = questionnaire.getDateTime();
						String physicalQuestion = questionnaire.getPhysicalQuestion();
						String mentalQuestion = questionnaire.getMentalQuestion();
						String immunizationQuestion = questionnaire.getImmunizationQuestion();
						questionnaireTable.getItems().add(new Questionnaire(questionnaireTime, physicalQuestion, mentalQuestion, immunizationQuestion));
					}
				}

				TableView<Prescription> prescriptionTable = new TableView<Prescription>();
				TableColumn<Prescription, String> prescDateCol = new TableColumn<Prescription, String>("Date");
				TableColumn<Prescription, String> prescriptionCol = new TableColumn<Prescription, String>("Prescriptions");	
				TableColumn<Prescription, String> phoneNumberCol = new TableColumn<Prescription, String>("Phone Number");	
				TableColumn<Prescription, String> emailCol = new TableColumn<Prescription, String>("Email");	
				TableColumn<Prescription, String> insuranceCol = new TableColumn<Prescription, String>("Insurance Provider");
				TableColumn<Prescription, String> pharmacyCol = new TableColumn<Prescription, String>("Preferred Pharmacy");	
				
				prescDateCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("dateTime"));
				prescriptionCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("prescriptionBody"));
				phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("phoneNumber"));
				emailCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("email"));
				insuranceCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("insuranceProvider"));
				pharmacyCol.setCellValueFactory(new PropertyValueFactory<Prescription, String>("preferredPharmacy"));

				// adds every column to the table and sets their widths
				prescriptionTable.getColumns().addAll(prescDateCol, prescriptionCol, phoneNumberCol, emailCol, insuranceCol, pharmacyCol);
				prescriptionTable.setMaxWidth(800);
				prescDateCol.setPrefWidth(100);
				prescriptionCol.setPrefWidth(150);
				phoneNumberCol.setPrefWidth(100);
				emailCol.setPrefWidth(150);
				insuranceCol.setPrefWidth(150);
				pharmacyCol.setPrefWidth(150);
				prescriptionTable.setTranslateX(-30);
				
				prescriptionTable.getItems().clear();
				prescriptionTable.refresh();
				ArrayList<Prescription> prescriptions = patient.getPrescriptions(patient.getUsername());
				if (prescriptions != null) {
					for (Prescription prescription : prescriptions) {
						String prescriptionTime = prescription.getDateTime();
						String prescriptionBody = prescription.getPrescriptionBody();
						String phoneNumber = prescription.getPhoneNumber();
						String email = prescription.getEmail();
						String insuranceProvider = prescription.getInsuranceProvider();
						String preferredPharmacy = prescription.getPreferredPharmacy();
						System.out.println(prescriptionTime);
						System.out.println(prescriptionBody);
						System.out.println(phoneNumber);
						System.out.println(email);
						System.out.println(insuranceProvider);
						System.out.println(preferredPharmacy);
						System.out.println("===============");
					}
					for (Prescription prescription : prescriptions) {
						String prescriptionTime = prescription.getDateTime();
						String prescriptionBody = prescription.getPrescriptionBody();
						String phoneNumber = prescription.getPhoneNumber();
						String email = prescription.getEmail();
						String insuranceProvider = prescription.getInsuranceProvider();
						String preferredPharmacy = prescription.getPreferredPharmacy();
						prescriptionTable.getItems().add(new Prescription(prescriptionTime, prescriptionBody, phoneNumber, email, insuranceProvider, preferredPharmacy));
					}
				}

				TableView<Immunization> immunizationTable = new TableView<Immunization>();
				TableColumn<Immunization, String> immunizationDateCol = new TableColumn<Immunization, String>("Date");
				TableColumn<Immunization, String> immunizationColumn = new TableColumn<Immunization, String>("Immunizations");						
				
				immunizationDateCol.setCellValueFactory(new PropertyValueFactory<Immunization, String>("dateTime"));
				immunizationColumn.setCellValueFactory(new PropertyValueFactory<Immunization, String>("immunizationQuestion"));

				// adds every column to the table and sets their widths
				immunizationTable.getColumns().addAll(immunizationDateCol, immunizationColumn);
				immunizationTable.setMaxWidth(800);
				immunizationDateCol.setPrefWidth(200);
				immunizationColumn.setPrefWidth(600);
				immunizationTable.setTranslateX(-30);
				
				immunizationTable.getItems().clear();
				immunizationTable.refresh();
				ArrayList<Immunization> immunizations = patient.getImmunizations(patient.getUsername());
				if (immunizations != null) {
					for (Immunization immunization : immunizations) {
						String immunizationTime = immunization.getDateTime();
						String immunizationQuestion = immunization.getImmunizationQuestion();
						System.out.println(immunizationTime);
						System.out.println(immunizationQuestion);
						System.out.println("==================");
					}
					for (Immunization immunization : immunizations) {
						String immunizationTime = immunization.getDateTime();
						String immunizationQuestion = immunization.getImmunizationQuestion();
						immunizationTable.getItems().add(new Immunization(immunizationTime, immunizationQuestion));
					}
				}
				
				// adds everything to the right vbox and displays it
				right.getChildren().addAll(physicalTable, questionnaireTable, prescriptionTable, immunizationTable);	
				right.setSpacing(20);
				
				VBox temp = new VBox();
				temp.getChildren().addAll(right);
				
				temp.setTranslateX(35);
				temp.setTranslateY(25);
				
				// creates a scroll pane so that the screen its scrollable
				ScrollPane scroll = new ScrollPane(temp);
				scroll.setFitToWidth(true); 
				scroll.setPrefViewportWidth(300);
				scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
				scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
				root.setCenter(scroll);
			}
		});
		
		// event handler for if the messages button is clicked
		messagesTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					questionsTab.setStyle("");
					vitalsTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the messages button so it shows that it is clicked
				messagesTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates an hbox for the recipient and recipient name labels
				HBox recipientBox = new HBox();
				Label recipient = new Label("RECIPIENT:");	
				Label recipientName = new Label(patient.getFullName());
				recipient.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				recipientName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				recipientBox.getChildren().addAll(recipient, recipientName);
				recipientBox.setSpacing(10);
				
				// creates an Hbox for the subject label and textfield
				HBox subjectBox = new HBox();
				Label subject = new Label("SUBJECT:");
				subject.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField subjField = new TextField();
				subjField.setTranslateY(-4);
				subjField.setMinWidth(500);
				subjectBox.getChildren().addAll(subject, subjField);
				subjectBox.setSpacing(10);
				
				// creates the text area for the message
				TextArea message = new TextArea("");
				message.setMaxWidth(700);
				message.setPrefHeight(300);
				
				// creates an hbox for the clear and send buttons
				HBox buttons = new HBox();
				
				// creates a button to clear the subject and message fields
				Button clear = new Button("CLEAR");
				clear.setPrefWidth(175);
				clear.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				clear.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				// event handler for the clear button
				clear.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		subjField.clear();
		        		message.clear();
		        	}
	        	});
				
				// creates a send button to send the message
				Button send = new Button("SEND");
				send.setPrefWidth(175);
				send.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				send.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				send.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		time = LocalDateTime.now();
						String timeString = time.format(formatter);
		        		Database.createMessageFile("nurse", nurse.getUsername(), patient.getUsername(), 
		        									timeString, subjField.getText(), message.getText());
		        	}
		        });
				// adds the buttons to the hbox
				buttons.getChildren().addAll(clear, send);
				buttons.setSpacing(50);
				buttons.setTranslateX(100);
				buttons.setTranslateY(30);
				
				// creates a table to view past messages with subject and date as columns
				TableView<Message> messageTable = new TableView<Message>();
				TableColumn<Message, String> dateColumn = new TableColumn<Message, String>("Date");
				TableColumn<Message, String> subjectColumn = new TableColumn<Message, String>("Subject");
				TableColumn<Message, String> messageColumn = new TableColumn<Message, String>("Message Body");

				
				dateColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("dateTime"));
				subjectColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("subject"));
				messageColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("messageBody"));

				// adds every column to the table and sets their widths
				messageTable.getColumns().addAll(dateColumn, subjectColumn, messageColumn);
				
				messageTable.setMaxWidth(800);
				dateColumn.setPrefWidth(70);
				subjectColumn.setPrefWidth(180);
				messageColumn.setPrefWidth(400);
				messageTable.setTranslateY(150);
				
				messageTable.getItems().clear();
				messageTable.refresh();
				ArrayList<Message> messages = nurse.getMessages(nurse.getUsername(), patient.getUsername());
				if (messages != null) {
					for (Message localMessage : messages) {
						String messageTime = localMessage.getDateTime();
						String messageRecipient = localMessage.getRecipient();
						String messageSubject = localMessage.getSubject();
						String messageBody = localMessage.getMessageBody();				
						System.out.println(messageTime);
						System.out.println(messageRecipient);
						System.out.println(messageSubject);
						System.out.println(messageBody);
						System.out.println("===============");
					}
					for (Message localMessage : messages) {
						String messageTime = localMessage.getDateTime();
						String messageRecipient = localMessage.getRecipient();
						String messageSubject = localMessage.getSubject();
						String messageBody = localMessage.getMessageBody();						
						messageTable.getItems().add(new Message(messageTime, messageRecipient, messageSubject, messageBody));
					}
				}

				// adds everything to the right vbox
				right.getChildren().addAll(recipientBox, subjectBox, message, buttons, messageTable);	
				right.setSpacing(10);
				
				// copies the right vbox into a temporary vbox to change its position 
				VBox temp = new VBox();
				temp.getChildren().addAll(right);
				
				temp.setTranslateX(100);
				temp.setTranslateY(20);
				
				// creates a scroll pane so that the screen its scrollable
				ScrollPane scroll = new ScrollPane(temp);
				root.setCenter(scroll);
			}
		});
		
		// adds the label in the left vbox
		Label nurseName = new Label("Nurse " + nurse.getFullName());
		nurseName.setPrefWidth(175);
		nurseName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        // adds everything to the left vbox and sets the background color
		left.getChildren().addAll(patientName, questionsTab, vitalsTab, historyTab, messagesTab, nurseName, back);
		left.setSpacing(70);
		left.setStyle("-fx-background-color: #A8D1C3;");
		left.setAlignment(Pos.CENTER);
		left.setPrefWidth(200);

		// setting the border pane with the left and right vboxes
		root.setLeft(left);
		root.setCenter(right);
		
		BorderPane.setMargin(left, new Insets(0, 0, 0, 0));
		BorderPane.setMargin(right, new Insets(50, 0, 0, 50));
		
		// making the window size and showing it to user upon running
		Scene scene = new Scene(root, 1000, 700);
		primaryStage.setTitle("Nurse View");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	// function to clear all of the radio buttons passed into it
	public void clearRadioButton(RadioButton r1, RadioButton r2, RadioButton r3) {
		r1.setSelected(false);
	    	r2.setSelected(false);
	    	r3.setSelected(false);
	}
	
	// function to clear all the comment textfields
	public void clearComment(TextField t1, TextField t2, TextField t3, TextField t4) {
		t1.clear();
		t2.clear();
		t3.clear();
		t4.clear();
	}
}