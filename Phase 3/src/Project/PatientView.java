// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Patient View page for phase 3

package Project;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PatientView extends Application {
	private Patient patient;
	private String assignedDoctor;
	private String assignedNurse;
	
	private static LocalDateTime time;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
	
	public PatientView(Patient patient) {
		this.patient = patient;
		this.assignedDoctor = Database.associateDoctorSearch(patient.getUsername());
		this.assignedNurse = Database.associateNurseSearch(patient.getUsername());
	}
	
	public void start(Stage primaryStage) {
		final Doctor assignedD;
		final Nurse assignedN;
		
		if (!assignedDoctor.equals("ABC")) {
			assignedD = Database.doctorSearch(assignedDoctor);
		} else {
			assignedD = null;
		}
		if (!assignedNurse.equals("ABC")) {
			assignedN = Database.nurseSearch(assignedNurse);
		} else {
			assignedN = null;
		}
		
		// the root to hold everything
		BorderPane root = new BorderPane();
		
		// creates the rightSide and left vboxes for the display
		VBox rightSide = new VBox();
		VBox leftSide = new VBox();
		
		// swap this out from the database field - shows the person who is logged in 
		Label patientName = new Label(patient.getFullName());
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
		Button back = new Button("LOGOUT");
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
        });
		
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
        		boolean[] isEditMode = {false};
        		Button edit = new Button("Edit Profile");
        		edit.setPrefWidth(175);
				edit.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				edit.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a;");
				edit.setTranslateX(248);
				edit.setTranslateY(110);
        		        		
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
                String[] values = {patient.getFirstName(), patient.getLastName(), patient.getDOB(), patient.getPhoneNumber(), patient.getEmail(), patient.getInsurance(), patient.getPharmacy(), "Unassigned"}; // initial values from phase 1 design
                
                if (assignedD != null) {
                	values[7] = "Dr. " + assignedD.getLastName();
                }
                
                // so when updating the values of personal information from the database --> access value like values[0] for first name set to whatever you read in, etc. for rest
                
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
                phoneField.setEditable(false);
                phoneField.setTranslateX(150);
                grid.add(phoneField, 1, 3);
                
                // textfield for the email that sets up the style, size, grid position and makes it editable
                TextField emailField = new TextField(values[4]);
                emailField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                emailField.setEditable(false);
                emailField.setTranslateX(150);
                grid.add(emailField, 1, 4);
                
                // textfield for the insurance field that sets up the style, size, grid position and makes it editable
                TextField insuranceField = new TextField(values[5]);
                insuranceField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                insuranceField.setEditable(false);
                insuranceField.setTranslateX(150);
                grid.add(insuranceField, 1, 5);
                
                // textfield for the pharmacy field that sets up the style, size, grid position and makes it editable
                TextField pharmacyField = new TextField(values[6]);
                pharmacyField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                pharmacyField.setEditable(false);
                pharmacyField.setTranslateX(150);
                grid.add(pharmacyField, 1, 6);
                
                // textfield for the doctor field that sets up the style, size, grid position and makes it uneditable
                TextField doctorField = new TextField(values[7]);
                doctorField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                doctorField.setEditable(false);
                doctorField.setTranslateX(150);
                grid.add(doctorField, 1, 7);
                
				
                // ADD - updating the database with the new personal information values user has specified
                edit.setOnAction(new EventHandler<>() {
                	public void handle(ActionEvent event) {
                		if (!isEditMode[0]) {
                             phoneField.setEditable(true);
                             emailField.setEditable(true);
                             insuranceField.setEditable(true);
                             pharmacyField.setEditable(true);
                            edit.setText("Save");
                        } else {
                            edit.setText("Edit");
                            phoneField.setEditable(false);
                            emailField.setEditable(false);
                            insuranceField.setEditable(false);
                            pharmacyField.setEditable(false);
                        }
                        isEditMode[0] = !isEditMode[0];                		
                        HashMap<String, String> updatedValues = Database.updateTable(patient.getUsername(), phoneField.getText(), emailField.getText(), insuranceField.getText(), pharmacyField.getText());

                        if (updatedValues.containsKey("PhoneNumber")) {
                            phoneField.setText(updatedValues.get("PhoneNumber"));
                            patient.setPhoneNumber(updatedValues.get("PhoneNumber"));
                        }
                        if (updatedValues.containsKey("Email")) {
                        	emailField.setText(updatedValues.get("Email"));
                        	patient.setEmail(updatedValues.get("Email"));
                        }
                        if (updatedValues.containsKey("InsuranceProvider")) {
                        	insuranceField.setText(updatedValues.get("InsuranceProvider"));
                        	patient.setInsurance(updatedValues.get("InsuranceProvider"));
                        }
                        if (updatedValues.containsKey("PreferredPharmacy")) {
                        	pharmacyField.setText(updatedValues.get("PreferredPharmacy"));
                        	patient.setPharmacy(updatedValues.get("PreferredPharmacy"));
                        }
                	}
                });
                
                // adding all the elements to the rightside vbox to display
				rightSide.getChildren().addAll(topLabel, grid, edit);

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
				TableView<Summary> summaryTable = new TableView<Summary>();
				TableColumn<Summary, String> dateCol = new TableColumn<Summary, String>("Date");
				TableColumn<Summary, String> summaryCol = new TableColumn<Summary, String>("Appointment Summary");
				
				dateCol.setCellValueFactory(new PropertyValueFactory<Summary, String>("date"));
				summaryCol.setCellValueFactory(new PropertyValueFactory<Summary, String>("summary"));

				// adds every column to the table and sets their widths
				summaryTable.getColumns().addAll(dateCol, summaryCol);
				summaryTable.setMaxWidth(800);
				dateCol.setPrefWidth(150);
				summaryCol.setPrefWidth(450);
				summaryTable.setTranslateX(-30);
				
				summaryTable.getItems().clear();
				summaryTable.refresh();
				ArrayList<Summary> summaries = patient.getSummaries(patient.getUsername());
				if (summaries != null) {
					for (Summary summary : summaries) {
						String date = summary.getDate();
						Vitals vitals = summary.getVital();
						Questionnaire questionnaire = summary.getQuestionnaire();
						summaryTable.getItems().add(new Summary(date, vitals, questionnaire));
					}
				}
				// adding table and label to the right vbox for displaying
				rightSide.getChildren().addAll(summaryTable);
				rightSide.setSpacing(20);
        	}
        });
		
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
				Label recipientName = new Label("Please select a recipient.");
				
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
				
				HBox radioButtons = new HBox();
				String[] sendToWho = {""};
			
				RadioButton nurseRB = new RadioButton("Send to Nurse");
				nurseRB.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		sendToWho[0] = "Nurse";
		        	}
		        });
				
				RadioButton doctorRB = new RadioButton("Send to Doctor");
				doctorRB.setOnAction(new EventHandler<>() {
		        	public void handle(ActionEvent event) {
		        		sendToWho[0] = "Doctor";
		        	}
		        });
				
				// adds the buttons to a toggle group to ensure only 1 can be selected at a time
				ToggleGroup togGroup = new ToggleGroup();
		        nurseRB.setToggleGroup(togGroup);
		        doctorRB.setToggleGroup(togGroup);
		        
		        // adds the buttons to the hbox
				radioButtons.getChildren().addAll(nurseRB, doctorRB);
				radioButtons.setSpacing(120);		
				radioButtons.setTranslateX(135);
				radioButtons.setTranslateY(10);
				
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
		        		nurseRB.setSelected(false);
		        		doctorRB.setSelected(false);
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
						
						if (!sendToWho[0].isEmpty()) {
							if (sendToWho[0].equals("Doctor") && !assignedDoctor.equals("ABC")) {
								String result = Database.createMessageFile("Can't create", "patientd", patient.getUsername(), assignedD.getUsername(), 
										timeString, subjField.getText(), message.getText());
								System.out.println("result: " + result);
								if (!result.equals("Don't create") || !result.equals("Created")) {
									Database.createMessageFile(result, "patientd", patient.getUsername(), assignedD.getUsername(), 
											timeString, subjField.getText(), message.getText());
								}
							} else if (sendToWho[0].equals("Nurse") && !assignedNurse.equals("ABC")) {
								String result = Database.createMessageFile("Can't create", "patientn", patient.getUsername(), assignedN.getUsername(), 
										timeString, subjField.getText(), message.getText());
								System.out.println("result: " + result);
								if (!result.equals("Don't create") || !result.equals("Created")) {
									Database.createMessageFile(result, "patientn", patient.getUsername(), assignedN.getUsername(), 
											timeString, subjField.getText(), message.getText());
								}
							} else {
								if (assignedDoctor.equals("ABC") && assignedNurse.equals("ABC")) {
									Database.showAlert("None Assigned");
								} else if (assignedDoctor.equals("ABC") && !assignedNurse.equals("ABC")) {
									Database.showAlert("No Doctor Yet");
								} else if (!assignedDoctor.equals("ABC") && assignedNurse.equals("ABC")) {
									Database.showAlert("No Nurse Yet");
								}
							}
						}
		        	}
		        });
				// adds the buttons to the hbox
				buttons.getChildren().addAll(clear, send);
				buttons.setSpacing(50);
				buttons.setTranslateX(100);
				buttons.setTranslateY(30);
				
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
				subjectColumn.setPrefWidth(100);
				messageColumn.setPrefWidth(400);
				messageTable.setTranslateY(70);
				
				messageTable.getItems().clear();
				messageTable.refresh();
				
				ArrayList<Message> messages = new ArrayList<Message>();
				if (assignedD != null) {
					messages = patient.getMessages("doctor", "patientd", patient.getUsername(), assignedD.getUsername());
				}
				if (messages != null) {
					for (Message localMessage : messages) {
						String messageTime = localMessage.getDateTime();
						String messageRecipient = localMessage.getRecipient();
						String messageSubject = localMessage.getSubject();
						String messageBody = localMessage.getMessageBody();						
						messageTable.getItems().add(new Message(messageTime, messageRecipient, messageSubject, messageBody));
					}
				}
				
				TableView<Message> receivedMessageTable = new TableView<Message>();
				TableColumn<Message, String> receivedDateColumn = new TableColumn<Message, String>("Date");
				TableColumn<Message, String> receivedSubjectColumn = new TableColumn<Message, String>("Subject");
				TableColumn<Message, String> receivedMessageColumn = new TableColumn<Message, String>("Message Body");

				receivedDateColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("dateTime"));
				receivedSubjectColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("subject"));
				receivedMessageColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("messageBody"));

				// adds every column to the table and sets their widths
				receivedMessageTable.getColumns().addAll(receivedDateColumn, receivedSubjectColumn, receivedMessageColumn);
				
				receivedMessageTable.setMaxWidth(800);
				receivedDateColumn.setPrefWidth(70);
				receivedSubjectColumn.setPrefWidth(100);
				receivedMessageColumn.setPrefWidth(400);
				receivedMessageTable.setTranslateY(70);
				
				receivedMessageTable.getItems().clear();
				receivedMessageTable.refresh();
				
				ArrayList<Message> receivedMessages = new ArrayList<Message>();
				if (assignedD != null) {
					receivedMessages = patient.getReceivedMessages("doctor", "patientd", patient.getUsername(), assignedD.getUsername());
				}
				if (receivedMessages != null) {
					for (Message localMessage : receivedMessages) {
						String messageTime = localMessage.getDateTime();
						String messageRecipient = localMessage.getRecipient();
						String messageSubject = localMessage.getSubject();
						String messageBody = localMessage.getMessageBody();						
						receivedMessageTable.getItems().add(new Message(messageTime, messageRecipient, messageSubject, messageBody));
					}
				}

				TableView<Message> messageTable1 = new TableView<Message>();
				TableColumn<Message, String> dateColumn1 = new TableColumn<Message, String>("Date");
				TableColumn<Message, String> subjectColumn1 = new TableColumn<Message, String>("Subject");
				TableColumn<Message, String> messageColumn1 = new TableColumn<Message, String>("Message Body");

				dateColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("dateTime"));
				subjectColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("subject"));
				messageColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("messageBody"));

				// adds every column to the table and sets their widths
				messageTable1.getColumns().addAll(dateColumn1, subjectColumn1, messageColumn1);
				
				messageTable1.setMaxWidth(800);
				dateColumn1.setPrefWidth(70);
				subjectColumn1.setPrefWidth(100);
				messageColumn1.setPrefWidth(400);
				messageTable1.setTranslateY(70);
				
				messageTable1.getItems().clear();
				messageTable1.refresh();
				
				ArrayList<Message> messages1 = new ArrayList<Message>();
				if (assignedN != null) {
					messages1 = patient.getMessages("nurse", "patientn", patient.getUsername(), assignedN.getUsername());
				}				
				if (messages1 != null) {
					for (Message localMessage : messages1) {
						String messageTime = localMessage.getDateTime();
						String messageRecipient = localMessage.getRecipient();
						String messageSubject = localMessage.getSubject();
						String messageBody = localMessage.getMessageBody();						
						messageTable1.getItems().add(new Message(messageTime, messageRecipient, messageSubject, messageBody));
					}
				}
				
				TableView<Message> receivedMessageTable1 = new TableView<Message>();
				TableColumn<Message, String> receivedDateColumn1 = new TableColumn<Message, String>("Date");
				TableColumn<Message, String> receivedSubjectColumn1 = new TableColumn<Message, String>("Subject");
				TableColumn<Message, String> receivedMessageColumn1 = new TableColumn<Message, String>("Message Body");

				receivedDateColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("dateTime"));
				receivedSubjectColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("subject"));
				receivedMessageColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("messageBody"));

				// adds every column to the table and sets their widths
				receivedMessageTable1.getColumns().addAll(receivedDateColumn1, receivedSubjectColumn1, receivedMessageColumn1);
				
				receivedMessageTable1.setMaxWidth(800);
				receivedDateColumn1.setPrefWidth(70);
				receivedSubjectColumn1.setPrefWidth(100);
				receivedMessageColumn1.setPrefWidth(400);
				receivedMessageTable1.setTranslateY(70);
				
				receivedMessageTable1.getItems().clear();
				receivedMessageTable1.refresh();
				
				ArrayList<Message> receivedMessages1 = new ArrayList<Message>();
				if (assignedN != null) {
					receivedMessages1 = patient.getReceivedMessages("nurse", "patientn", patient.getUsername(), assignedN.getUsername());
				}				
				if (receivedMessages1 != null) {
					for (Message localMessage : receivedMessages1) {
						String messageTime = localMessage.getDateTime();
						String messageRecipient = localMessage.getRecipient();
						String messageSubject = localMessage.getSubject();
						String messageBody = localMessage.getMessageBody();						
						receivedMessageTable1.getItems().add(new Message(messageTime, messageRecipient, messageSubject, messageBody));
					}
				}
		
				rightSide.getChildren().addAll(recipientBox, subjectBox, message, radioButtons, buttons, messageTable, 
												messageTable1, receivedMessageTable, receivedMessageTable1);	
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