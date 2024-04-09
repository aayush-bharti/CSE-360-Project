// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Patient View page for phase 3

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

public class PatientView extends Application {

	public void start(Stage primaryStage) {
		// the root to hold everything
		BorderPane root = new BorderPane();
		
		// creates the rightSide and left vboxes for the display
		VBox rightSide = new VBox();
		VBox leftSide = new VBox();
		
		// swap this out from the database field - shows the person who is logged in 
		Label patientName = new Label("INSERT Patient Name");
		patientName.setStyle("-fx-text-fill: #00005a;");
		patientName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		patientName.setTranslateY(-80);
		
		// button to open the personal information page
		Button personalInfoOpen = new Button("PERSONAL INFO");
		personalInfoOpen.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		personalInfoOpen.setPrefWidth(175);
		
		// button to open the appointments page
		Button appointmentOpen = new Button("APPOINTMENTS");
		appointmentOpen.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		appointmentOpen.setPrefWidth(175);
		
		// button to open the messaging system
		Button messagesOpen = new Button("MESSAGES");
		messagesOpen.setPrefWidth(175);
		messagesOpen.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		// back button to take back to the home login page
		Button back = new Button("BACK");
		back.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		back.setPrefWidth(175);
		back.setTranslateY(70);
		
		// if back button clicked, reopen login page 
		back.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		Login log = new Login();
        		log.start(primaryStage);
        	}
        }
        );
		
		// adding everything to the left vbox and setting its spacing and background color for aesthetics
		leftSide.getChildren().addAll(patientName, personalInfoOpen, appointmentOpen, messagesOpen, back);
		leftSide.setSpacing(70);
		leftSide.setStyle("-fx-background-color: #A8D1C3;");
		leftSide.setAlignment(Pos.CENTER);
		leftSide.setPrefWidth(200);
		
		// if personal info button clicked, open the personal information page
		personalInfoOpen.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		// clears all the existing things on the right vbox to display new things
        		if (!rightSide.getChildren().isEmpty()) {
					rightSide.getChildren().clear();
					personalInfoOpen.setStyle("");
					appointmentOpen.setStyle("");
					messagesOpen.setStyle("");
					root.setCenter(rightSide);
				}
        		
        		// sets the color of the active page to blue
        		personalInfoOpen.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
        		
        		// label to let the user know where we are in the program
        		Label topLabel = new Label("Personal Information");
        		topLabel.setStyle("-fx-text-fill: #00005a;");
        		topLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        		topLabel.setTranslateX(224);
        		topLabel.setTranslateY(20);
        		
        		// save button patient can click if they change any details
        		Button save = new Button("Save Changes");
        		save.setPrefWidth(175);
				save.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				save.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				save.setTranslateX(248);
				save.setTranslateY(110);
        		        		
				// gridpane with black border that basically holds everything involved - labels for the info and the fields in which they are shown
        		GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));
                grid.setStyle("-fx-border-color: black;");
                grid.setTranslateX(-25);
                grid.setTranslateY(75);

                // arrays that basically hold the values and we'll go through creating them and putting into a grid at a certain position
                String[] labels = {"FIRST NAME", "LAST NAME", "DATE OF BIRTH", "CONTACT INFORMATION - PHONE", "CONTACT INFORMATION - EMAIL", "INSURANCE INFORMATION", "PHARMACY INFORMATION", "DOCTOR NAME"};
                String[] values = {"xxxxxx", "xxxxxx", "XX/XX/XXXX", "(XXX) XXX-XXXX", "xxxxxx@xxxxx.xxx", "XXXXXXXXX", "XXXXXXXXX", "Dr. xxxx"}; // initial values from phase 1 design
                
                // so when updating the values of personal information from the database --> access value like values[0] for first name set to whatever you read in, etc. for rest
                // ADD - processing of the user from database to populate the values array indices
                
                // for loop that goes through and creates the labels with their specific names
                for (int i = 0; i < labels.length; i++)
                {
                	Label infoLabels = new Label(labels[i]);
                	infoLabels.setTranslateX(-150);
                	grid.add(infoLabels, 0, i);
                }
                
                // textfield for the first name that sets up the style, size, grid position and makes it uneditable
                TextField firstNameField = new TextField(values[0]);
                firstNameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                firstNameField.setEditable(false);
                firstNameField.setTranslateX(150);
                grid.add(firstNameField, 1, 0);
                
                // textfield for the last name that sets up the style, size, grid position and makes it uneditable
                TextField lastNameField = new TextField(values[1]);
                lastNameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                lastNameField.setEditable(false);
                lastNameField.setTranslateX(150);
                grid.add(lastNameField, 1, 1);
                
                // textfield for the birth date that sets up the style, size, grid position and makes it uneditable
                TextField birthField = new TextField(values[2]);
                birthField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                birthField.setEditable(false);
                birthField.setTranslateX(150);
                grid.add(birthField, 1, 2);
                
                // textfield for the phone number that sets up the style, size, grid position and makes it editable
                TextField phoneField = new TextField(values[3]);
                phoneField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                phoneField.setEditable(true);
                phoneField.setTranslateX(150);
                grid.add(phoneField, 1, 3);
                
                // textfield for the email that sets up the style, size, grid position and makes it editable
                TextField emailField = new TextField(values[4]);
                emailField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                emailField.setEditable(true);
                emailField.setTranslateX(150);
                grid.add(emailField, 1, 4);
                
                // textfield for the insurance field that sets up the style, size, grid position and makes it editable
                TextField insuranceField = new TextField(values[5]);
                insuranceField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                insuranceField.setEditable(true);
                insuranceField.setTranslateX(150);
                grid.add(insuranceField, 1, 5);
                
                // textfield for the pharmacy field that sets up the style, size, grid position and makes it editable
                TextField pharmacyField = new TextField(values[6]);
                pharmacyField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                pharmacyField.setEditable(true);
                pharmacyField.setTranslateX(150);
                grid.add(pharmacyField, 1, 6);
                
                // textfield for the doctor field that sets up the style, size, grid position and makes it uneditable
                TextField doctorField = new TextField(values[7]);
                doctorField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                doctorField.setEditable(false);
                doctorField.setTranslateX(150);
                grid.add(doctorField, 1, 7);
                
				
                // ADD - updating the database with the new personal information values user has specified
                save.setOnAction(new EventHandler<>()
                {
                	public void handle(ActionEvent event)
                	{
                		// data people check if the new value they inputted by doing .gettext is different from the original value read in from the database
                		// if different, update in database
                	}
                }
                );
                
                // adding all the elements to the rightside vbox to display
				rightSide.getChildren().addAll(topLabel, grid, save);

        	}
        }
        );
		
		// if appointments button is clicked, open this page
		appointmentOpen.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		// clears the right vbox for fresh new page
        		if (!rightSide.getChildren().isEmpty()) {
					rightSide.getChildren().clear();
					personalInfoOpen.setStyle("");
					appointmentOpen.setStyle("");
					messagesOpen.setStyle("");
					root.setCenter(rightSide);
				}
        		
        		// sets style of active page for user clarity and aesthetics
				appointmentOpen.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// ADD - populate this table with values from database
				TableView<String> allInfo = new TableView<>();
				TableColumn<String, String> date = new TableColumn<>("Date");
				TableColumn<String, String> summary = new TableColumn<>("Summary");
				
				// label to signify to user where we are in program
				Label topLabel = new Label("Appointments");
				topLabel.setStyle("-fx-text-fill: #00005a;");
				topLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
				topLabel.setTranslateX(245);
				
				// table spacing, sizing and adding of the two columns
				allInfo.setTranslateY(45);
				allInfo.getColumns().addAll(date, summary);
				allInfo.setMaxWidth(700);
				date.setPrefWidth(200);
				summary.setPrefWidth(500);
				
				// adding table and label to the right vbox for displaying
				rightSide.getChildren().addAll(topLabel, allInfo);
        	}
        }
        );
		
		// if messsages button is clicked, open this page
		messagesOpen.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		// clear out the right vbox for fresh page
        		if (!rightSide.getChildren().isEmpty()) {
					rightSide.getChildren().clear();
					personalInfoOpen.setStyle("");
					appointmentOpen.setStyle("");
					messagesOpen.setStyle("");
				}
        		
				// colors the messages button so it shows that it is clicked
				messagesOpen.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				
				// creates an hbox for the recipient and recipient name labels
				HBox recipientBox = new HBox();
				Label recipient = new Label("RECIPIENT:");	
				Label recipientName = new Label("INSERT DOCTOR NAME"); // data people put the doctor name here
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
				
				// adds everything to the rightSide vbox
				rightSide.getChildren().addAll(recipientBox, subjectBox, message, buttons, pastMessages);	
				rightSide.setSpacing(10);
				
				// making new temp vbox to add everything to right
				VBox newRight = new VBox();
				newRight.getChildren().addAll(rightSide);
				
				// setting spacing for aesthetics
				newRight.setTranslateX(100);
				newRight.setTranslateY(20);
				
				// creates a scroll pane so that the screen its scrollable
				ScrollPane scroll = new ScrollPane(newRight);
				root.setCenter(scroll); // scroll setting
				
			}
        }
        );
		
		// setting the border pane with the left and right vboxes
		root.setLeft(leftSide);
		root.setCenter(rightSide);
		
		// setting margin spacing using insets
		BorderPane.setMargin(leftSide, new Insets(0, 0, 0, 0));
		BorderPane.setMargin(rightSide, new Insets(50, 0, 0, 50));
				
		// making the window size and showing it to user upon running
		Scene scene = new Scene(root, 1000, 700);
		primaryStage.setTitle("Patient View");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
