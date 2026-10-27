package com.mycompany.studentgrademanager;

import java.io.*;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.fxml.FXML;

public class AddGradeController {

    @FXML
    private Button Dashboard;

    @FXML
    private Button Enter;

    @FXML
    private Button Home;

    @FXML
    private TextField StudentGrade;

    @FXML
    private ComboBox<String> StudentID;

    @FXML
    private TextField Subject;

    @FXML
    private Label errorLabel;

    private ArrayList<Grade> gradeList = new ArrayList<>();
    private Grade grade;

    // Load existing student IDs into the ComboBox when screen starts
    @FXML
    public void initialize() {
        loadStudentIDsIntoComboBox();
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Dashboard");
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

    // Validate inputs and save the new grade into the binary file
    @FXML
    private void saveGrade() {
        try {
            String id = StudentID.getValue();
            String subject = Subject.getText();
            String rawGrade = StudentGrade.getText().trim();
            double parsedGrade = Double.parseDouble(rawGrade);
            Double gradeValue = Double.valueOf(rawGrade);;

            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("Please select a Student ID");
            }

            if (subject == null || subject.isEmpty()) {
                throw new IllegalArgumentException("Please enter the subject name");
            }

            if (rawGrade.isEmpty()) {
                throw new IllegalArgumentException("Fill the grade field please");
            }

            grade = new Grade(id, subject, gradeValue);
            saveToBinaryFile(grade);

            // Clear fields after successful save
            Subject.clear();
            StudentGrade.clear();
            StudentID.getSelectionModel().clearSelection();

        } catch (IllegalArgumentException e) {
            showTemporaryError(e.getMessage());
        }
    }

    // Show temporary error message using timeline animation for 2 seconds
    private void showTemporaryError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(2),
                    event -> errorLabel.setText("")
            ));
            timeline.play();
        } else {
            System.out.println("Error: " + message);
        }
    }

    // Read and save grades permanently to grades.dat binary file
    private void saveToBinaryFile(Grade newGrade) {
        File file = new File("grades.dat");

        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

                gradeList = (ArrayList<Grade>) ois.readObject();

            } catch (Exception e) {
                System.out.println("Error reading permanent grades data: " + e.getMessage());
            }
        }

        gradeList.add(newGrade);

        try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(gradeList);
            System.out.println("Grade saved permanently to grades.dat");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Populate student IDs from students.dat into the ComboBox
    private void loadStudentIDsIntoComboBox() {
        File file = new File("students.dat");
        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

                ArrayList<Student> students = (ArrayList<Student>) ois.readObject();
                for (Student s : students) {
                    StudentID.getItems().add(s.getStudentId());
                }
            } catch (Exception e) {
                System.out.println("Error loading Student IDs: " + e.getMessage());
            }
        }
    }
}
