package com.mycompany.studentgrademanager;

import java.io.*;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AddStudentController {

    @FXML //declare the UI components 
    private Button AddGradeButton;

    @FXML
    private Button DashboardButton;

    @FXML
    private Button returnHomeButton;

    @FXML
    private Button enter;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField studentIDText;

    @FXML
    private TextField studentNameText;

    private ArrayList<Student> studentList = new ArrayList<>(); //to save info in binary file
    private Student student; // this attributs is used to save info after click ENTER 

    @FXML
    private void goToDashboard(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Dashboard");
        } catch (IOException e) {
            System.out.println("Error changing screen: " + e.getMessage());
        }
    }

    @FXML
    private void goToAddGrades(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("AddGradeUI");
        } catch (IOException e) {
            System.out.println("Error changing screen: " + e.getMessage());
        }
    }

    @FXML
    private void goToHome(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Login");
        } catch (IOException e) {
            System.out.println("Error changing screen: " + e.getMessage());
        }
    }

    // Validate student inputs and save them when ENTER button is clicked
    @FXML
    private void saveStudent() { // Enter button
        try {
            String name = studentNameText.getText();
            String id = studentIDText.getText();

            // Create new student object (triggers validation in model class)
            student = new Student(name, id);

            // Save student data to binary file 
            saveToBinaryFile(student);

            // Clear text fields for new entries  
            studentNameText.clear();
            studentIDText.clear();

        } catch (IllegalArgumentException e) {
            // Show validation error message temporarily
            showTemporaryError(e.getMessage());
        }
    }

    /* no @FXML notation because it's a normal method that no need to show it at the UI
       this method shows temporary error message */
    private void showTemporaryError(String message) {
        // show error message
        errorLabel.setText(message);

        // wait for 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event -> errorLabel.setText("") // delete the message after 2 seconds
        ));

        // start timer
        timeline.play();
    }
    
    @FXML
    // Serialize and save student records permanently into students.dat
    private void saveToBinaryFile(Student newStudent) {
    File file = new File("students.dat");
    if (file.exists() && file.length() > 0) { //check if the file exists
        try { //read the file
            FileInputStream fis = new FileInputStream(file);
            //ois can't handle with files directly so we have to use fis
            ObjectInputStream ois = new ObjectInputStream(fis); 
            
            // Read existing students list from file
            studentList = (ArrayList<Student>) ois.readObject();
            
            ois.close(); // close file
        } catch (Exception e) {
            System.out.println("Error reading permanent data: " + e.getMessage());
        }
    }

    // Add the new student to the list
    studentList.add(newStudent);

    try { //write into file
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        // Write updated list back to file
        oos.writeObject(studentList);
        
        oos.close(); // close oos file
        System.out.println("Student saved permanently");

    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
    }
}