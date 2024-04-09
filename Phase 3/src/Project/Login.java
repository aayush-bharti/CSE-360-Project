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

        // creating the button for identifying if it's a patient signing in/up
        ToggleGroup roles = new ToggleGroup();
        RadioButton patient = new RadioButton("PATIENT");
        patient.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        patient.setPrefWidth(100);
        patient.setPrefHeight(45);
        patient.setToggleGroup(roles);
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
        
        RadioButton nurse = new RadioButton("NURSE");
        nurse.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nurse.setPrefWidth(100);
        nurse.setPrefHeight(45);
        nurse.setToggleGroup(roles);
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
        
        RadioButton doctor = new RadioButton("DOCTOR");
        doctor.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doctor.setPrefWidth(100);
        doctor.setPrefHeight(45);
        doctor.setToggleGroup(roles);
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
        
        // second, will create the VBoxs, put the elements in them and fix all the spacing and alignment
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
        
        signIn.setOnAction(new EventHandler<>()
        {
        	public void handle(ActionEvent event)
        	{
        		if (doctor.isSelected())
        		{
        			PatientSearch searchScreen = new PatientSearch();
        	        searchScreen.start(primaryStage);
        		}
        		
        		else if (patient.isSelected())
        		{
        			PatientView patView = new PatientView();
        			patView.start(primaryStage);
        		}
        		
        		else
        		{
        			NurseView nurView = new NurseView();
        			nurView.start(primaryStage);
        		}
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
