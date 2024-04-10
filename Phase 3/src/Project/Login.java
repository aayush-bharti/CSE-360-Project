// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Main landing page for 360 Project

package Project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login extends Application {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clinic");

        // the root to hold everything
        BorderPane root = new BorderPane();

        // first will go through and create all the pieces
        // creating the label for login
        Label login = new Label("LOGIN");
        login.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // creating the label that goes over the role selection
        Label portal = new Label("PORTAL");
        portal.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // creating the username field
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(200);

        // creating the password field
        TextField password = new TextField();
        password.setPromptText("Password");
        password.setMaxWidth(200);

        // creating the sign-in button
        Button signIn = new Button("SIGN IN");
        signIn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        signIn.setPrefWidth(200);

        // creating the line between sign in and sign up buttons from our design
        Separator separator = new Separator();
        separator.setPrefWidth(200);

        // creating the sign-up button
        Button signUp = new Button("SIGN UP");
        signUp.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        signUp.setPrefWidth(200);
        
        Label spacer = new Label("");
        spacer.setPrefWidth(180);

        // creating the toggle group to contain the three radio buttons for the roles
        ToggleGroup roles = new ToggleGroup();
        
        // patient radio button to be pressed if a patient wants to sign in
        RadioButton patient = new RadioButton("PATIENT");
        patient.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        patient.setPrefWidth(100);
        patient.setPrefHeight(45);
        patient.setToggleGroup(roles);
        
        // checking if patient selected then color that radiobutton to signify
        patient.selectedProperty().addListener((observable, oldValue, selected) -> 
        {
        	if (selected) 
            {
                patient.setStyle("-fx-background-color: #9AB4DF;");
            }
            
            else
            {
                patient.setStyle("-fx-background-color: #A8D1C3;");
            }
        });
        
        // nurse radio button to be pressed if a nurse wants to sign in
        RadioButton nurse = new RadioButton("NURSE");
        nurse.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nurse.setPrefWidth(100);
        nurse.setPrefHeight(45);
        nurse.setToggleGroup(roles);
        
        // checking if nurse selected then color that radiobutton to signify
        nurse.selectedProperty().addListener((observable, oldValue, selected) -> 
        {
            if (selected) 
            {
                nurse.setStyle("-fx-background-color: #9AB4DF;");
            }
            
            else
            {
                nurse.setStyle("-fx-background-color: #A8D1C3;");
            }
        });
        
        // doctor radiobutton to be pressed if a doctor wants to sign in
        RadioButton doctor = new RadioButton("DOCTOR");
        doctor.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doctor.setPrefWidth(100);
        doctor.setPrefHeight(45);
        doctor.setToggleGroup(roles);
        
        // checking if doctor selected then color that radiobutton to signify
        doctor.selectedProperty().addListener((observable, oldValue, selected) -> 
        {
            if (selected) 
            {
                doctor.setStyle("-fx-background-color: #9AB4DF;");
            }
            
            else
            {
                doctor.setStyle("-fx-background-color: #A8D1C3;");
            }
        });
        
        // creating the VBox for the left side and adding all the elements to it
        VBox leftSide = new VBox(10);
        leftSide.getChildren().addAll(login, username, password, signIn, separator, signUp);
        
        // setting padding and aligning it for aesthetics
        leftSide.setPadding(new Insets(20, 20, 20, 20));
        leftSide.setAlignment(Pos.CENTER);
        
        
        // provides some extra spacing to help with aesthetics
        VBox.setMargin(login, new Insets(0, 0, 20, 0));
        VBox.setMargin(username, new Insets(0, 0, 10, 0));
        VBox.setMargin(password, new Insets(0, 0, 20, 0)); 
        VBox.setMargin(signIn, new Insets(0, 0, 5, 0)); 
        VBox.setMargin(signUp, new Insets(5, 0, 0, 0));
        
        // setting spacing between elements
        leftSide.setSpacing(30);

        // creating the VBox for the right side and adding all the elements to it
        VBox rightSide = new VBox(10);
        rightSide.getChildren().addAll(portal, patient, nurse, doctor, spacer);
        
        // setting padding and aligning it for aesthetics
        rightSide.setPadding(new Insets(20, 20, 20, 20));
        rightSide.setAlignment(Pos.CENTER);
        
        // special spacing for some of the elements
        VBox.setMargin(portal, new Insets(0, 0, 30, 0)); 
        VBox.setMargin(patient, new Insets(0, 0, 15, 0)); 
        VBox.setMargin(nurse, new Insets(0, 0, 15, 0)); 
        
        // setting the color to the exact one from our design and setting spacing between elements
        rightSide.setStyle("-fx-background-color: #A8D1C3;");
        rightSide.setSpacing(30);
        
        // super specific spacing elements to make everything looks neat
        portal.setTranslateY(0);
        patient.setTranslateY(-15);
        nurse.setTranslateY(-8);
        username.setTranslateY(15);
        signIn.setTranslateY(20);
        signUp.setTranslateY(-20);
        
        // placing the two VBox's into the borderpane and setting margins for spacing
        root.setLeft(leftSide);
        root.setRight(rightSide);
        BorderPane.setMargin(leftSide, new Insets(0, 0, 0, 100));
        BorderPane.setMargin(rightSide, new Insets(0, 100, 0, 0));
        
        // if the sign in button is clicked, this logic will be carried out
        signIn.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		// if the doctor is selected trying to sign in carry out this logic
        		if (doctor.isSelected())
        		{
        			// checking if the username and the password are what any doctor would use to sign in to their view
        			if ((username.getText().equals("doc")) && (password.getText().equals("doc"))) // generic one cuz doctors dont need to make their own
        			{
        				// opening the patient search view
        				PatientSearch searchScreen = new PatientSearch();
            	        searchScreen.start(primaryStage);
        			}
        		}
        		
        		// if the patient is selected then we have to check credentials with database to be able to determine if we can go on to next page or not
        		else if (patient.isSelected())
        		{
        			// ADD - add in logic here about checking credentials with database to see if patient exists otherwise sign up
        			PatientView patView = new PatientView();
        			patView.start(primaryStage);
        		}
        		
        		// if nurse is selected carry out this logic
        		else
        		{
        			// checking if the username and the password are what any nurse would use to sign in to their view
        			if ((username.getText().equals("nurse")) && (password.getText().equals("nurse"))) // nurse one cuz nurses dont need to make their own
        			{
        				// opening the nurse search view
        				NurseView nurView = new NurseView();
            			nurView.start(primaryStage);
        			}
        		}
        	}
        }
        );
        
        // if the sign up button is clicked, this logic will be carried out
        signUp.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		// clearing the right side to maximize screen for signing up
        		rightSide.getChildren().clear();
        		root.setRight(null);
        		
        		// clear the left vbox and set the patient radiobutton to selected because only patients will sign up
        		leftSide.getChildren().clear();
        		patient.setStyle("-fx-background-color: #9AB4DF;");
        		patient.setSelected(true);
        		
        		// error label for later use in case patient does not fill out all fields
        		Label errorLabel = new Label();
        		errorLabel.setFont(Font.font("Arial", 16));
        		errorLabel.setStyle("-fx-text-fill: red;");
        		errorLabel.setTranslateX(160);
        		errorLabel.setTranslateY(-127);
        		
        		// carries out the logic for if they click sign up within the sign up screen
        		Button signUpNested = new Button("SIGN UP");
                signUpNested.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                signUpNested.setPrefWidth(200);
                signUpNested.setTranslateX(160);
                signUpNested.setTranslateY(-117);
                
                // button that takes the user back to the login page
                Button backToLogin = new Button("BACK TO LOGIN");
                backToLogin.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                backToLogin.setPrefWidth(200);
                backToLogin.setTranslateX(160);
                backToLogin.setTranslateY(-117);
        		
        		// textfield for the first name that sets up the style, size, grid position and makes it uneditable
                TextField firstNameField = new TextField();
                firstNameField.setPromptText("First Name");
                //firstNameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                firstNameField.setEditable(true);
                firstNameField.setTranslateX(50);
                firstNameField.setTranslateY(20);
                
                // textfield for the last name that sets up the style, size, grid position and makes it uneditable
                TextField lastNameField = new TextField();
                lastNameField.setPromptText("Last Name");
                //lastNameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                lastNameField.setEditable(true);
                lastNameField.setTranslateX(280);
                lastNameField.setTranslateY(-37);

                // textfield for the birth date that sets up the style, size, grid position and makes it uneditable
                TextField birthField = new TextField();
                birthField.setPromptText("Date of Birth");
                //birthField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                birthField.setEditable(true);
                birthField.setTranslateX(150);
                birthField.setTranslateY(-20);
                
                // textfield for the phone number that sets up the style, size, grid position and makes it editable
                TextField phoneField = new TextField();
                phoneField.setPromptText("Phone Number");
                //phoneField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                phoneField.setEditable(true);
                phoneField.setTranslateX(50);
                phoneField.setTranslateY(0);
                
                // textfield for the email that sets up the style, size, grid position and makes it editable
                TextField emailField = new TextField();
                emailField.setPromptText("Email");
                //emailField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                emailField.setEditable(true);
                emailField.setTranslateX(280);
                emailField.setTranslateY(-57);
                
                // textfield for the insurance field that sets up the style, size, grid position and makes it editable
                TextField usernameField = new TextField();
                usernameField.setPromptText("Username");
                //usernameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                usernameField.setEditable(true);
                usernameField.setTranslateX(50);
                usernameField.setTranslateY(-80);
                
                // textfield for the password field that sets up the style, size, grid position and makes it editable
                TextField passwordField = new TextField();
                passwordField.setPromptText("Password");
                //passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                passwordField.setEditable(true);
                passwordField.setTranslateX(280);
                passwordField.setTranslateY(-137);
                
             // textfield for the insurance field that sets up the style, size, grid position and makes it editable
                TextField insuranceField = new TextField();
                insuranceField.setPromptText("Insurance Provider");
                //passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                insuranceField.setEditable(true);
                insuranceField.setTranslateX(50);
                insuranceField.setTranslateY(-40);
                
             // textfield for the pharmacy field that sets up the style, size, grid position and makes it editable
                TextField pharmacyField = new TextField();
                pharmacyField.setPromptText("Pharmacy");
                //passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
                pharmacyField.setEditable(true);
                pharmacyField.setTranslateX(280);
                pharmacyField.setTranslateY(-97);

        		
                // adds all fields and button and label to the left vbox
                leftSide.getChildren().addAll(firstNameField, lastNameField, birthField, phoneField, emailField, insuranceField, pharmacyField, usernameField, passwordField, backToLogin, signUpNested, errorLabel);
                
                // if the patient clicks the back to login button conduct this
                backToLogin.setOnAction(new EventHandler<>()
                {
                	public void handle(ActionEvent event)
                	{
                		Login newlog = new Login();
                		newlog.start(primaryStage);
                	}
                }
                );
                
                // if the patient clicks sign up within the sign up screen, conduct this logic
                signUpNested.setOnAction(new EventHandler<>()
                {
                	public void handle(ActionEvent event)
                	{
                		// if there are any empty fields show the error message
                		if ((firstNameField.getText().isEmpty()) || (lastNameField.getText().isEmpty()) || (birthField.getText().isEmpty()) || (phoneField.getText().isEmpty()) || (emailField.getText().isEmpty()) || (usernameField.getText().isEmpty()) || (passwordField.getText().isEmpty()))
                		{
                			errorLabel.setText("Please fill out all fields.");
                		}
                		
                		// if all fields are filled, create new patient in database with the specified fields and take them to the patient view
                		else
                		{
                			// ADD
                			// create new patient in the database system with all those specified fields and credentials
                			
                			// take newly signed up patient to their view
                			PatientView patView = new PatientView();
                			patView.start(primaryStage);
                		}
                	}
                }
                );
        	}
        }
        );
        
        // making the window size and showing it to user upon running
        Scene scene = new Scene(root, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
