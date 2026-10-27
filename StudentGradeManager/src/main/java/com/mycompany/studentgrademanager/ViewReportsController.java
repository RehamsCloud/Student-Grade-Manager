package com.mycompany.studentgrademanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ViewReportsController {

    // UI Components linked to FXML
    @FXML
    private Label totalStudentsLabel;

    @FXML
    private Label classAverageLabel;

    @FXML
    private Label highestGradeLabel;

    @FXML
    private Label lowestGradeLabel;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button addInfoButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button backButton;

    // Runs automatically when the screen loads
    public void initialize() {
        calculateStatistics();
    }

    // Calculate and display all statistical metrics for the reports screen
    private void calculateStatistics() {
        File file = new File("students.dat");
        File gradeFile = new File("grades.dat");

        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<Grade> gradeList = new ArrayList<>();

        // Read student data from binary file
        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

                studentList = (ArrayList<Student>) ois.readObject();

            } catch (Exception e) {
                System.out.println("Error reading student data for reports: " + e.getMessage());
            }
        }

        // Read grade data from binary file
        if (gradeFile.exists() && gradeFile.length() > 0) {
            try (FileInputStream fis = new FileInputStream(gradeFile); ObjectInputStream ois = new ObjectInputStream(fis)) {

                gradeList = (ArrayList<Grade>) ois.readObject();

            } catch (Exception e) {
                System.out.println("Error reading grade data for statistics: " + e.getMessage());
            }
        }
        
        // Set total number of students on the UI label
        int totalStudents = studentList.size();
        totalStudentsLabel.setText(String.format(java.util.Locale.US, "%d", totalStudents));

        // Handle case if no grades are available yet
        if (gradeList == null || gradeList.isEmpty()) {
            totalStudentsLabel.setText("0");
            classAverageLabel.setText("0.0%");
            highestGradeLabel.setText("0.0");
            lowestGradeLabel.setText("0.0");
            return;
        }
        
        double sum = 0;
        
        // Initialize highest and lowest with the first grade record
        double highest = gradeList.get(0).getStudentGrade();
        double lowest = gradeList.get(0).getStudentGrade();

        // Loop through all grade records to compute sum, highest, and lowest values
        for (Grade gradeRecord : gradeList) {
        double currentGrade = gradeRecord.getStudentGrade(); // Get the grade value from the Grade object
        
            sum += currentGrade; // Add the grade to the total sum for the average calculation later

            // Check for the highest grade
            if (currentGrade > highest) {
                highest = currentGrade;
            }

            // Check for the lowest grade
            if (currentGrade < lowest) {
                lowest = currentGrade;
            }
        }
        
        // Calculate final class average
        double classAverage = sum / totalStudents;
        
        // Display formatted results on report cards
        highestGradeLabel.setText(String.format(java.util.Locale.US, "%.1f", highest));
        lowestGradeLabel.setText(String.format(java.util.Locale.US, "%.1f", lowest));
        classAverageLabel.setText(String.format(java.util.Locale.US, "%.1f%%", classAverage));
    }

    // Navigation methods to change screens
    @FXML
    void handleDashboard(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Dashboard");
        } catch (IOException e) {
            System.out.println("Error: The FXML file for this screen could not be found!");
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddInfo(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("AddStudentUI");
        } catch (IOException e) {
            System.out.println("Error: The FXML file for this screen could not be found!");
            e.printStackTrace();
        }
    }

    @FXML
    void handleHome(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Login");
        } catch (IOException e) {
            System.out.println("Error: The FXML file for this screen could not be found!");
            e.printStackTrace();
        }
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Dashboard");
        } catch (IOException e) {
            System.out.println("Error: The FXML file for this screen could not be found!");
            e.printStackTrace();
        }
    }
}