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
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.*;

public class cse360NurseView extends Application {

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

		// event handler for if the prescriptions button is clicked 
		questionsTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					questionsTab.setStyle("");
					vitalsTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
				}
				// colors the prescriptions button so it shows that it is clicked
				questionsTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// label and text area to enter the prescription for the patient
				Label question1Label = new Label("Question 1:");
				question1Label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea q1Field = new TextArea();
				q1Field.setMaxWidth(700);
				q1Field.setPrefHeight(100);
				
				// label for the patient info part of the screen
				Label question2Label = new Label("Question 2:");
				question2Label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea q2Field = new TextArea();
				q2Field.setMaxWidth(700);
				q2Field.setPrefHeight(100);
				
				Label question3Label = new Label("Question 3:");
				question3Label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
				
				TextArea q3Field = new TextArea();
				q3Field.setMaxWidth(700);
				q3Field.setPrefHeight(100);

				// send button which sends the prescription to the pharmacy if all fields are filled
				Button save = new Button("SAVE");
				save.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				save.setPrefWidth(175);
				save.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				save.setTranslateX(275);
				save.setTranslateY(30);
				// event handler for the send prescription button
				// checks if all the fields needed are filled and if so it will send the prescription
				save.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		//ADD
		        		// if the field is empty, display error, if not send
		        		int x=1;
		        	}
		        });
				
				Button clear = new Button("CLEAR");
				clear.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				clear.setPrefWidth(175);
				clear.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				clear.setTranslateX(275);
				clear.setTranslateY(30);
				// event handler for the send prescription button
				// checks if all the fields needed are filled and if so it will send the prescription
				clear.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		q1Field.clear();
		        		q2Field.clear();
		        		q3Field.clear();
		        	}
		        });
				
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
		
		// event handler for if the physical button is clicked
		vitalsTab.setOnAction(new EventHandler<>() {
			public void handle(ActionEvent event) {
				// will reset all the colors and clear the right vbox so it can be changed
				if (!right.getChildren().isEmpty()) {
					right.getChildren().clear();
					questionsTab.setStyle("");
					vitalsTab.setStyle("");
					historyTab.setStyle("");
					messagesTab.setStyle("");
				}
				// colors the physical button so it shows that it is clicked
				vitalsTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates 2 hboxes, one for the labels and one for the text fields to display information in the first 2 rows
				HBox vitalsLabels = new HBox();
				HBox vitalsFields = new HBox();
				HBox vitalsButtons = new HBox();
				
				// creates all the labels and their respective fields to fill in the info for the exam so that it can be added to the first hbox
				Label dateLabel = new Label("Date:        ");
				dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				TextField dateField = new TextField();
				dateField.setTranslateY(-10);
				dateField.setMaxWidth(85);
				
				Label weightLabel = new Label("Weight (lb):");
				weightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				weightLabel.setTranslateX(20);
				TextField weightField = new TextField();
				weightField.setTranslateY(-10);
				weightField.setMaxWidth(90);
				
				Label heightLabel = new Label("Height (in):");
				heightLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				heightLabel.setTranslateX(32);
				TextField heightField = new TextField();
				heightField.setTranslateY(-10);
				heightField.setMaxWidth(90);
				
				Label tempLabel = new Label("Body Temperature (F):");
				tempLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				tempLabel.setTranslateX(47);
				TextField tempField = new TextField();
				tempField.setTranslateY(-10);
				tempField.setMaxWidth(150);
				
				Label bpLabel = new Label("Blood Pressure (mmHg):");
				bpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				bpLabel.setTranslateX(49);
				TextField bpField = new TextField();
				bpField.setTranslateY(-10);
				bpField.setMaxWidth(150);
				
//				TableView inputVitals = new TableView<>();
//				inputVitals.setEditable(true);
//				TableColumn<String,String> dateEntry = new TableColumn<>("Date");
//				TableColumn<String,String> weightEntry = new TableColumn<>("Weight (lb)");
//				TableColumn<String,String> heightEntry = new TableColumn<>("Height (in)");
//				TableColumn<String,String> bodyTempEntry = new TableColumn<>("Body Temperature (F)");
//				TableColumn<String,String> bpEntry = new TableColumn<>("Blood Pressure (mmHg)");
//				inputVitals.setMaxWidth(700);
//				dateEntry.setPrefWidth(134);
//				weightEntry.setPrefWidth(133);
//				heightEntry.setPrefWidth(133);
//				bodyTempEntry.setPrefWidth(150);
//				bpEntry.setPrefWidth(150);
				
//				inputVitals.getColumns().addAll(dateEntry,weightEntry,heightEntry,bodyTempEntry,bpEntry);
//				inputVitals.setEditable(true);
				
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

		        	}
		        });
				
				// creates the save button to save the information from the physical exam
				Button save = new Button("SAVE");
				save.setPrefWidth(175);
				save.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				save.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				save.setTranslateY(-20);
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
				vitalsLabels.setTranslateX(-30);
				vitalsFields.setTranslateX(-30);
				
				// adds everything to the right vbox and displays it
				right.getChildren().addAll(vitalsLabels,vitalsFields, buttons);
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
				}
				// colors the history button so it shows that it is clicked
				historyTab.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates a table and all of its columns
				TableView<String> histTable = new TableView<>();
				TableColumn<String, String> dateCol = new TableColumn<>("Date");
				TableColumn<String, String> weightCol = new TableColumn<>("Weight (lb)");
				TableColumn<String, String> heightCol = new TableColumn<>("Height (in)");
				TableColumn<String, String> tempCol = new TableColumn<>("Body Temperature (F)");
				TableColumn<String, String> bpCol = new TableColumn<>("Blood Pressure (mmHg");

				// adds every column to the table and sets their widths
				histTable.getColumns().addAll(dateCol, weightCol, heightCol, tempCol, bpCol);
				histTable.setMaxWidth(700);
				dateCol.setPrefWidth(100);
				weightCol.setPrefWidth(100);
				heightCol.setPrefWidth(100);
				tempCol.setPrefWidth(150);
				bpCol.setPrefWidth(150);
				
				// adds everything to the right vbox and displays it
				right.getChildren().addAll(histTable);	
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
		docName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		// adds the back button to the left vbox
		Button back = new Button("BACK");
		back.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		back.setPrefWidth(175);
		// event handler for the back button
        back.setOnAction(new EventHandler<>() {
        	public void handle(ActionEvent event) {
        		// goes back to the patient search screen if a new patient wants to be selected or they want to logout
//        		PatientSearch searchScreen = new PatientSearch();
//        		searchScreen.start(primaryStage);
        		int x = 1;
        	}
        });
        // adds everything to the left vbox and sets the background color
		left.getChildren().addAll(patientName, questionsTab, vitalsTab, historyTab, messagesTab, docName, back);
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

