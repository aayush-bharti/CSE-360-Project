// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Doctor View page for phase 3

package Project;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.*;

public class DoctorView extends Application {

	public void start(Stage primaryStage) {
		// the root to hold everything
		BorderPane root = new BorderPane();
		
		// creates the right and left vboxes for the display
		VBox right = new VBox();
		VBox left = new VBox();

		// sets up the left vbox by making all the labels and buttons in it
		Label patientName = new Label("INSERT Patient Name");
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
				numField.setEditable(false);
				numField.setTranslateY(-10);
				
				Label email = new Label("Email:");
				email.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				email.setTranslateX(-15);
				TextField emailField = new TextField();
				emailField.setEditable(false);
				emailField.setTranslateY(-10);
				
				Label insurance = new Label("Insurance Provider:");
				insurance.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				insurance.setTranslateX(32);
				TextField insurField = new TextField();
				insurField.setEditable(false);
				insurField.setTranslateY(-10);
				
				Label pharmacy = new Label("Preferred Pharmacy:");
				pharmacy.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				pharmacy.setTranslateX(-10);
				TextField pharmacyField = new TextField();
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
		        		//ADD
		        		// if the field is empty, display error, if not send
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
				firstField.setEditable(false);
				firstField.setTranslateY(-10);
				
				Label last = new Label("Last Name:");
				last.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				last.setTranslateX(12);
				TextField lastField = new TextField();
				lastField.setEditable(false);
				lastField.setTranslateY(-10);
				
				Label dob = new Label("Date of Birth:");
				dob.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				dob.setTranslateX(25);
				TextField dobField = new TextField();
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
				RadioButton yes1 = new RadioButton("Yes");
				RadioButton no1 = new RadioButton("No");
				RadioButton notExamined1 = new RadioButton("Not Examined");
				
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
				RadioButton yes2 = new RadioButton("Yes");
				RadioButton no2 = new RadioButton("No");
				RadioButton notExamined2 = new RadioButton("Not Examined");
				
				// adds the buttons to a toggle group to ensure only 1 can be selected at a time
				ToggleGroup g2 = new ToggleGroup();
		        yes2.setToggleGroup(g2);
		        no2.setToggleGroup(g2);
		        notExamined2.setToggleGroup(g2);
		        // adds the buttons to the hbox
				rb2.getChildren().addAll(yes2, no2, notExamined2);
				rb2.setSpacing(20);
				
				// creates radio buttons and an hbox for the radio buttons for the third row
				HBox rb3 = new HBox();
				RadioButton yes3 = new RadioButton("Yes");
				RadioButton no3 = new RadioButton("No");
				RadioButton notExamined3 = new RadioButton("Not Examined");
				
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
				RadioButton yes4 = new RadioButton("Yes");
				RadioButton no4 = new RadioButton("No");
				RadioButton notExamined4 = new RadioButton("Not Examined");
				
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
		        		//ADD
		        		// if all are not empty, save, if not error
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
				}
				// colors the history button so it shows that it is clicked
				historyTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// ADD VALUES TO TABLE
				// creates a table and all of its columns
				TableView<String> histTable = new TableView<>();
				TableColumn<String, String> dateCol = new TableColumn<>("Date");
				TableColumn<String, String> weightCol = new TableColumn<>("Weight(lb)");
				TableColumn<String, String> heightCol = new TableColumn<>("Height(in)");
				TableColumn<String, String> tempCol = new TableColumn<>("Body Temperature(F)");
				TableColumn<String, String> bpCol = new TableColumn<>("Blood Pressure(mmHg)");
				TableColumn<String, String> healthCol = new TableColumn<>("Health Issues");
				TableColumn<String, String> medCol = new TableColumn<>("Medications Prescribed");
				
				
				// adds every column to the table and sets their widths
				histTable.getColumns().addAll(dateCol, weightCol, heightCol, tempCol, bpCol, healthCol, medCol);
				histTable.setMaxWidth(800);
				dateCol.setPrefWidth(60);
				weightCol.setPrefWidth(70);
				heightCol.setPrefWidth(70);
				tempCol.setPrefWidth(150);
				bpCol.setPrefWidth(150);
				healthCol.setPrefWidth(100);
				medCol.setPrefWidth(150);
				histTable.setTranslateX(-20);
				
				// ADD VALUES TO IMMUNIZATION FIELD
				// creates a hbox that will display the immunization records
				HBox immunizationBox = new HBox();
				Label immunization = new Label("IMMUNIZATIONS:");
				immunization.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField immunizationField = new TextField();
				immunizationField.setTranslateY(-5);
				immunizationField.setMinWidth(400);
				immunizationBox.getChildren().addAll(immunization, immunizationField);
				immunizationBox.setSpacing(20);
				
				
				// adds everything to the right vbox and displays it
				right.getChildren().addAll(histTable, immunizationBox);	
				right.setSpacing(20);
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
				}
				// colors the messages button so it shows that it is clicked
				messagesTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates an hbox for the recipient and recipient name labels
				HBox recipientBox = new HBox();
				Label recipient = new Label("RECIPIENT:");	
				Label recipientName = new Label("INSERT PATIENT NAME");
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
		        		//ADD
		        		// if field is not empty, send, otherwise error
		        	}
		        });
				// adds the buttons to the hbox
				buttons.getChildren().addAll(clear, send);
				buttons.setSpacing(50);
				buttons.setTranslateX(100);
				buttons.setTranslateY(30);
				
				// creates a table to view past messages with subject and date as columns
				TableView<String> pastMessages = new TableView<>();
				TableColumn<String, String> subjCol = new TableColumn<>("PREVIOUS MESSAGES");
				TableColumn<String, String> dateCol = new TableColumn<>("DATE");
				// adds the columns to the table and sets width
				pastMessages.getColumns().addAll(subjCol, dateCol);
				subjCol.setPrefWidth(400);
				dateCol.setPrefWidth(100);
				pastMessages.setMaxWidth(600);
				pastMessages.setMinHeight(600);
				pastMessages.setTranslateY(80);
				
				// adds everything to the right vbox
				right.getChildren().addAll(recipientBox, subjectBox, message, buttons, pastMessages);	
				right.setSpacing(10);
				right.setTranslateX(100);
				right.setTranslateY(20);
				// creates a scroll pane so that the screen its scrollable
				ScrollPane scroll = new ScrollPane(right);
				root.setCenter(scroll);
			}
		});
		
		// adds the label in the left vbox
		Label docName = new Label("INSERT Doc ");
		docName.setPrefWidth(175);
		docName.setStyle("-fx-text-fill: #00005a;");
		docName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		// adds the back button to the left vbox
		Button back = new Button("BACK");
		back.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		back.setPrefWidth(175);
		// event handler for the back button
        back.setOnAction(new EventHandler<>() {
        	public void handle(ActionEvent event) {
        		// goes back to the patient search screen if a new patient wants to be selected or they want to logout
        		PatientSearch searchScreen = new PatientSearch();
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
}


