// Author : CSE 360 Tuesday Team 10
// Class : CSE360 - SPRING 2024 - Professor Carter
// Assignment : Project
// Description : Main landing page for 360 Project

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class PatientSearch extends Application {
	
	private Patient patient;
	private Nurse nurse;
	private Doctor doctor;
	
	public PatientSearch(Nurse nurse) {
		this.nurse = nurse;
	}
	
	public PatientSearch(Doctor doctor) {
		this.doctor = doctor;
	}
	
    public void start(Stage primaryStage) {
    	// the root to hold everything
    	BorderPane root = new BorderPane();
    	
    	// title for the page
		Label title = new Label("PATIENT SEARCH");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		// creates a hbox to hold the label and textfield where the doctor will enter the patient name
        HBox inputBox = new HBox();
        
        // creates label and textfield that go in the hbox and adds it to the hbox
        Label nameLabel = new Label("ENTER PATIENT USERNAME:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        TextField inputField = new TextField();
        inputField.setMinWidth(300);
        inputField.setTranslateX(50);
        
        inputBox.getChildren().addAll(nameLabel, inputField);
        
        // creates a hbox for the buttons
        HBox buttons = new HBox(); 
        
        // logout button which goes back to the login page
        Button logout = new Button("Logout");
        logout.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a");
        logout.setPrefWidth(175);
        logout.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        logout.setOnAction(new EventHandler<>() {
        	public void handle(ActionEvent event) {
        		Login mainScreen = new Login();
        		mainScreen.start(primaryStage);
        	}
        });

        // confirm button which takes the doctor to the doctor view if the patient exists
        Button confirm = new Button("Confirm Patient");
        confirm.setStyle("-fx-text-fill: #ffffff; -fx-background-color: #00005a");
        confirm.setPrefWidth(175);
        confirm.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        confirm.setOnAction(new EventHandler<>() {
        	public void handle(ActionEvent event) {
        		String username = inputField.getText();
                if (username.isEmpty()) {
                	Database.showAlert("Left Blank");
                } else {
                	patient = Database.patientSearch(username);
                	if (patient != null) {
                		if (doctor != null) {
                			try {
                				Class.forName("com.mysql.cj.jdbc.Driver");
                				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
                				Statement statement = connection.createStatement();
                				statement.executeUpdate("UPDATE PatientTable SET AssociateDoctor = '" + doctor.getUsername() + 
                										"' WHERE Username = '" + username + "'");	
                				connection.close();
                			} catch (Exception exception) {
                				System.out.println("Inside !");
                				Database.showAlert("No User");
                			}
            				DoctorView doctorView = new DoctorView(doctor, patient);
    						doctorView.start(primaryStage);
            			} else if (nurse != null) {
            				try {
                				Class.forName("com.mysql.cj.jdbc.Driver");
                				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project", "ntyagi", "tuesdayteam10");
                				Statement statement = connection.createStatement();
                				statement.executeUpdate("UPDATE PatientTable SET AssociateNurse = '" + nurse.getUsername() + 
                										"' WHERE Username = '" + username + "'");	
                				connection.close();
                			} catch (Exception exception) {
                				System.out.println("Inside #");
                				Database.showAlert("No User");
                			}
            				NurseView nurseView = new NurseView(nurse, patient);
    						nurseView.start(primaryStage);
    					}
                	} else {
                		Database.showAlert("No User");
                	}
                }
				
        	}
        });
        
        
        
        // adds the buttons to the hbox
        buttons.getChildren().addAll(logout, confirm);
        buttons.setSpacing(40);

        // placing everything in the borderpane
        root.setTop(title);
        root.setBottom(buttons);
        root.setCenter(inputBox);
        
        BorderPane.setMargin(title, new Insets(50, 0, 0, 325));
        BorderPane.setMargin(inputBox, new Insets(100, 100, 50, 50));
        BorderPane.setMargin(buttons, new Insets(0, 0, 200, 200));
       
        // makes window size and shows it
        Scene scene = new Scene(root, 800, 550);
        primaryStage.setTitle("Patient Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    public static void main(String[] args) {
        launch(args);
    }

}