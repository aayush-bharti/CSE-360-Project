// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Doctor View page for phase 3

package Project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;

public class DoctorView extends Application {
	private Doctor doctor;
	private Patient patient;
	
	static LocalDateTime time;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
	
	public DoctorView(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public DoctorView(Doctor doctor, Patient patient) {
		this.doctor = doctor;
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
		
		Button prescriptionsTab = new Button("PRESCRIPTIONS");
		prescriptionsTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		prescriptionsTab.setPrefWidth(175);
		
		Button physicalTab = new Button("PHYSICAL");
		physicalTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		physicalTab.setPrefWidth(175);
		
		Button historyTab = new Button("HISTORY"); // note
		historyTab.setPrefWidth(175);
		historyTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		Button messagesTab = new Button("MESSAGES");
		messagesTab.setPrefWidth(175);
		messagesTab.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		// event handler for if the prescriptions button is clicked 
		prescriptionsTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					prescriptionsTab.setStyle("");
					physicalTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the prescriptions button so it shows that it is clicked
				prescriptionsTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// label and text area to enter the prescription for the patient
				Label prescripLabel = new Label("Enter Prescriptions:");
				prescripLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea prescripField = new TextArea();
				prescripField.setMaxWidth(700);
				prescripField.setPrefHeight(300);
				
				// label for the patient info part of the screen
				Label patientInfo = new Label("Patient Info:");
				patientInfo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				patientInfo.setTranslateY(5);

				// creates 2 hboxes, one for the labels and one for the text fields to display information
				HBox labels = new HBox();
				HBox fields = new HBox();
				
				// creates all the labels and their respective fields so that it can be added to the hboxes
				Label number = new Label("Phone Number:");
				number.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField numField = new TextField();
				numField.setText(patient.getPhoneNumber());
				numField.setEditable(false);
				numField.setTranslateY(-10);
				
				Label email = new Label("Email:");
				email.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				email.setTranslateX(-15);
				TextField emailField = new TextField();
				emailField.setText(patient.getEmail());
				emailField.setEditable(false);
				emailField.setTranslateY(-10);
				
				Label insurance = new Label("Insurance Provider:");
				insurance.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				insurance.setTranslateX(32);
				TextField insurField = new TextField();
				insurField.setText(patient.getInsurance());
				insurField.setEditable(false);
				insurField.setTranslateY(-10);
				
				Label pharmacy = new Label("Preferred Pharmacy:");
				pharmacy.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				pharmacy.setTranslateX(-10);
				TextField pharmacyField = new TextField();
				pharmacyField.setText(patient.getPharmacy());
				pharmacyField.setEditable(false);
				pharmacyField.setTranslateY(-10);
				
				// adds the labels and their respective fields to the hboxes
				labels.getChildren().addAll(number, email, insurance, pharmacy);
				labels.setSpacing(90);
				
				fields.getChildren().addAll(numField, emailField, insurField, pharmacyField);
				fields.setSpacing(20);
				
				
				// send button which sends the prescription to the pharmacy if all fields are filled
				Button send = new Button("SEND TO PHARMACY");
				send.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				send.setPrefWidth(175);
				send.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				send.setTranslateX(275);
				send.setTranslateY(30);
				// event handler for the send prescription button
				// checks if all the fields needed are filled and if so it will send the prescription
				send.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		Database.createPharmacyFile(patient.getUsername(), prescripField.getText(), numField.getText(), 
													emailField.getText(), insurField.getText(), pharmacyField.getText());
		        		
		        	}
		        });
				
				// adds everything to the right vbox so it can be displayed
				right.getChildren().addAll(prescripLabel, prescripField, patientInfo, labels, fields, send);	
				right.setSpacing(20);
			}
		});
		
		// event handler for if the physical button is clicked
		physicalTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					prescriptionsTab.setStyle("");
					physicalTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the physical button so it shows that it is clicked
				physicalTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates 2 hboxes, one for the labels and one for the text fields to display information in the first 2 rows
				HBox labels1 = new HBox();
				HBox fields1 = new HBox();
				
				// creates 2 hboxes, one for the labels and one for the text fields to display information in the second 2 rows				
				HBox labels2 = new HBox();
				HBox fields2 = new HBox();
				
				// creates all the labels and their respective fields to fill in the info for the exam so that it can be added to the first hbox
				Label first = new Label("First Name:");
				first.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField firstField = new TextField();
				firstField.setText(patient.getFirstName());
				firstField.setEditable(false);
				firstField.setTranslateY(-10);
				
				Label last = new Label("Last Name:");
				last.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				last.setTranslateX(12);
				TextField lastField = new TextField();
				lastField.setText(patient.getLastName());
				lastField.setEditable(false);
				lastField.setTranslateY(-10);
				
				Label dob = new Label("Date of Birth:");
				dob.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				dob.setTranslateX(25);
				TextField dobField = new TextField();
				dobField.setText(patient.getDOB());
				dobField.setEditable(false);
				dobField.setTranslateY(-10);
				
				Label exam = new Label("Date of Exam:");
				exam.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				exam.setTranslateX(25);
				TextField examField = new TextField();
				examField.setTranslateY(-10);
				
				// adds the labels and their respective fields to the hboxes
				labels1.getChildren().addAll(first, last, dob, exam);
				labels1.setSpacing(90);
				
				fields1.getChildren().addAll(firstField, lastField, dobField, examField);
				fields1.setSpacing(20);
				
				
				// creates all the labels and their respective fields to fill in the info for the exam so that it can be added to the second hbox
				Label temp = new Label("Temperature:");
				temp.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField tempField = new TextField();
				tempField.setTranslateY(-10);
				
				Label heart = new Label("Heart Rate:");
				heart.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField heartField = new TextField();
				heartField.setTranslateY(-10);
				
				Label bp = new Label("Blood Pressure:");
				bp.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				bp.setTranslateX(15);
				TextField bpField = new TextField();
				bpField.setTranslateY(-10);
				
				Label id = new Label("Patient ID:");
				id.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				id.setTranslateX(-5);
				TextField idField = new TextField();
				idField.setTranslateY(-10);
				
				// adds the labels and their respective fields to the hboxes
				labels2.getChildren().addAll(temp, heart, bp, id);
				labels2.setSpacing(90);
				
				fields2.getChildren().addAll(tempField, heartField, bpField, idField);
				fields2.setSpacing(20);

				
				// creates an hbox for the questions that need to be answered in the physical
				HBox questions = new HBox();
				
				// creates a vbox for the first column
				VBox features = new VBox();
				
				// creates labels that are in the first column
				Label featuresLabel = new Label("Features");
				featuresLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				
				Label appearance = new Label("General Appearance");
				appearance.setFont(Font.font("Arial", FontWeight.BOLD, 15));
				
				Label face = new Label("Ear, Nose, Throat");
				face.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				
				Label chest = new Label("Lungs and Chest");
				chest.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				
				Label vascular = new Label("Vascular");
				vascular.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				
				// adds the labels to the vbox 
				features.getChildren().addAll(featuresLabel, appearance, face, chest, vascular);
				features.setAlignment(Pos.CENTER);
				features.setSpacing(30);
				
				// creates a vbox for the second column which contains the answers to the questions
				VBox results = new VBox();
				
				// creates a label for the title
				Label resultsLabel = new Label("Results");
				resultsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				resultsLabel.setTranslateY(1);
				
				// creates radio buttons and an hbox for the radio buttons for the first row
				HBox rb1 = new HBox();
				String[] genAppearanceResults = {""};
				RadioButton yes1 = new RadioButton("Normal");
				yes1.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		genAppearanceResults[0] = "Normal";
		        	}
		        });
				RadioButton no1 = new RadioButton("Abnormal");
				no1.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		genAppearanceResults[0] = "Abnormal";
		        	}
		        });
				RadioButton notExamined1 = new RadioButton("Unexamined");
				notExamined1.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		genAppearanceResults[0] = "Unexamined";
		        	}
		        });
				
				// adds the buttons to a toggle group to ensure only 1 can be selected at a time
				ToggleGroup g1 = new ToggleGroup();
		        yes1.setToggleGroup(g1);
		        no1.setToggleGroup(g1);
		        notExamined1.setToggleGroup(g1);
		        // adds the buttons to the hbox
				rb1.getChildren().addAll(yes1, no1, notExamined1);
				rb1.setSpacing(20);
				
				// creates radio buttons and an hbox for the radio buttons for the second row
				HBox rb2 = new HBox();
				String[] earResults = {""};
				RadioButton yes2 = new RadioButton("Normal");
				yes2.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		earResults[0] = "Normal";
		        	}
		        });
				RadioButton no2 = new RadioButton("Abnormal");
				no2.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		earResults[0] = "Abnormal";
		        	}
		        });
				RadioButton notExamined2 = new RadioButton("Unexamined");
				notExamined2.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		earResults[0] = "Unexamined";
		        	}
		        });
				
				// adds the buttons to a toggle group to ensure only 1 can be selected at a time
				ToggleGroup g2 = new ToggleGroup();
				String[] lungResults = {""};
		        yes2.setToggleGroup(g2);
		        no2.setToggleGroup(g2);
		        notExamined2.setToggleGroup(g2);
		        // adds the buttons to the hbox
				rb2.getChildren().addAll(yes2, no2, notExamined2);
				rb2.setSpacing(20);
				
				// creates radio buttons and an hbox for the radio buttons for the third row
				HBox rb3 = new HBox();
				RadioButton yes3 = new RadioButton("Normal");
				yes3.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		lungResults[0] = "Normal";
		        	}
		        });
				RadioButton no3 = new RadioButton("Abnormal");
				no3.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		lungResults[0] = "Abnormal";
		        	}
		        });
				RadioButton notExamined3 = new RadioButton("Unexamined");
				notExamined3.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		lungResults[0] = "Unexamined";
		        	}
		        });
				
				// adds the buttons to a toggle group to ensure only 1 can be selected at a time
				ToggleGroup g3 = new ToggleGroup();
		        yes3.setToggleGroup(g3);
		        no3.setToggleGroup(g3);
		        notExamined3.setToggleGroup(g3);
		        // adds the buttons to the hbox
				rb3.getChildren().addAll(yes3, no3, notExamined3);
				rb3.setSpacing(20);
				
				// creates radio buttons and an hbox for the radio buttons for the fourth row
				HBox rb4 = new HBox();
				String[] vascularResults = {""};
				RadioButton yes4 = new RadioButton("Normal");
				yes4.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		vascularResults[0] = "Normal";
		        	}
		        });

				RadioButton no4 = new RadioButton("Abnormal");
				no4.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		vascularResults[0] = "Abnormal";
		        	}
		        });
				RadioButton notExamined4 = new RadioButton("Unexamined");
				notExamined4.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		vascularResults[0] = "Unexamined";
		        	}
		        });
				
				// adds the buttons to a toggle group to ensure only 1 can be selected at a time
				ToggleGroup g4 = new ToggleGroup();
		        yes4.setToggleGroup(g4);
		        no4.setToggleGroup(g4);
		        notExamined4.setToggleGroup(g4);
		        // adds the buttons to the hbox
				rb4.getChildren().addAll(yes4, no4, notExamined4);
				rb4.setSpacing(20);
				
				// adds the column name label and all the radio button hboxes to the vbox
				results.getChildren().addAll(resultsLabel, rb1, rb2, rb3, rb4);
				results.setAlignment(Pos.CENTER);
				results.setSpacing(32);
				
				// creates a vbox for the last column
				VBox comments = new VBox();
				
				// creates a label and all the text fields under it for each row
				Label commentsLabel = new Label("Comments");
				commentsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
				commentsLabel.setTranslateY(-6);
				
				TextField comment1 = new TextField();
				comment1.setTranslateY(-10);
				
				TextField comment2 = new TextField();
				comment2.setTranslateY(-14);
				
				TextField comment3 = new TextField();
				comment3.setTranslateY(-21);
				
				TextField comment4 = new TextField();
				comment4.setTranslateY(-28);
				
				// adds the column name label and text fields to the vbox
				comments.getChildren().addAll(commentsLabel, comment1, comment2, comment3, comment4);
				comments.setAlignment(Pos.CENTER);
				comments.setSpacing(30);
				comments.setTranslateY(20);
				
				// adds every column vbox into the hbox
				questions.getChildren().addAll(features, results, comments);
				questions.setSpacing(60);
				questions.setTranslateX(-30);
				questions.setAlignment(Pos.CENTER);
				
				// creates an hbox for the clear and save buttons
				HBox buttons = new HBox();
				
				// creates the clear button
				Button clear = new Button("CLEAR");
				clear.setPrefWidth(175);
				clear.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				clear.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				// clears all editable textfields and the radio buttons
				clear.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		dobField.clear();
		        		examField.clear();
		        		tempField.clear();
		        		heartField.clear();
		        		bpField.clear();
		        		idField.clear();
		        		
		        		// calls the clearRadioButton function to clear
		        		clearRadioButton(yes1, no1, notExamined1);
		        		clearRadioButton(yes2, no2, notExamined2);
		        		clearRadioButton(yes3, no3, notExamined3);
		        		clearRadioButton(yes4, no4, notExamined4);
		        		// calls the clearComment function to clear 
		        		clearComment(comment1, comment2, comment3, comment4);
		        	}
		        });
				
				// creates the save button to save the information from the physical exam
				Button save = new Button("SAVE");
				save.setPrefWidth(175);
				save.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				save.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				// checks if all the fields are inputed and saves the information
				save.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		Database.createPhysicalFile(firstField.getText(), lastField.getText(), dobField.getText(), 
		        				examField.getText(), tempField.getText(), heartField.getText(), bpField.getText(), 
		        				idField.getText(), genAppearanceResults[0], earResults[0], lungResults[0], 
								vascularResults[0], comment1.getText(), comment2.getText(), comment3.getText(), 
								comment4.getText());
		        	}
		        });
				
				// adds both buttons to the hbox 
				buttons.getChildren().addAll(clear, save);
				buttons.setSpacing(50);
				buttons.setTranslateX(150);
				buttons.setTranslateY(30);
				
				// adds everything to the right vbox and displays it
				right.getChildren().addAll(labels1, fields1, labels2, fields2, questions, buttons);
				right.setSpacing(20);
			}
		});
		
		// event handler for if the history button is clicked
		historyTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					prescriptionsTab.setStyle("");
					physicalTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
					root.setCenter(right);
				}
				// colors the history button so it shows that it is clicked
				historyTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// ADD VALUES TO TABLE
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
					prescriptionsTab.setStyle("");
					physicalTab.setStyle("");
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
								
				// creates an hbox for the clear button
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
;		        	}
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
		        		Database.createMessageFile("doctor", doctor.getUsername(), patient.getUsername(), 
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
				ArrayList<Message> messages = doctor.getMessages(doctor.getUsername(), patient.getUsername());
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
		Label docName = new Label("Dr. " + doctor.getFullName());
		docName.setPrefWidth(175);
		docName.setStyle("-fx-text-fill: #00005a;");
		docName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		// adds the back button to the left vbox
		Button back = new Button("BACK");
		back.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		back.setPrefWidth(175);
		// event handler for the back button
        back.setOnAction(new EventHandler<>() {
        	public void handle(ActionEvent event) {
        		// goes back to the patient search screen if a new patient wants to be selected or they want to logout
        		PatientSearch searchScreen = new PatientSearch(doctor);
        		searchScreen.start(primaryStage);
        	}
        });
        // adds everything to the left vbox and sets the background color
		left.getChildren().addAll(patientName, prescriptionsTab, physicalTab, historyTab, messagesTab, docName, back);
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
		primaryStage.setTitle("Doctor View");
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

	public static void main(String[] args) {
        launch(args);
    }
}
