package com.mycompany.studentgrademanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardController {

    @FXML
    private Label totalStudentsLabel;
    @FXML
    private Label averageGradeLabel;
    @FXML
    private Label highestGradeLabel;
    @FXML
    private Label appName;
    @FXML
    private Label studentName;
    @FXML
    private Button addStudentBtn;
    @FXML
    private Button addGradesBtn;
    @FXML
    private Button viewStudentsBtn;
    @FXML
    private Button viewReportsBtn;
    @FXML
    private Button toggleModeBtn;

    private boolean isDarkMode = false;

    @FXML
    public void initialize() {

        loadRealStatistics();
    }
    // Fetch and calculate real statistics from binary files for the dashboard cards
    private void loadRealStatistics() {
        File studentFile = new File("students.dat");
        File gradeFile = new File("grades.dat");

        ArrayList<Student> studentList = new ArrayList<>();
        ArrayList<Grade> gradeList = new ArrayList<>();
        
        // Read student records
        if (studentFile.exists() && studentFile.length() > 0) {
            try (FileInputStream fis = new FileInputStream(studentFile); ObjectInputStream ois = new ObjectInputStream(fis)) {
                studentList = (ArrayList<Student>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error reading students in Dashboard: " + e.getMessage());
            }
        }
        
        // Read grade records
        if (gradeFile.exists() && gradeFile.length() > 0) {
            try (FileInputStream fis = new FileInputStream(gradeFile); ObjectInputStream ois = new ObjectInputStream(fis)) {
                gradeList = (ArrayList<Grade>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Error reading grades in Dashboard: " + e.getMessage());
            }
        }
        
        // Update total students counter label
        int totalStudents = (studentList != null) ? studentList.size() : 0;
        totalStudentsLabel.setText(String.valueOf(totalStudents));
        
        // Calculate averages and highest grades if grades exist
        if (gradeList != null && !gradeList.isEmpty()) {
            double sum = 0;
            double highest = Double.MIN_VALUE;
            int validGradesCount = 0;

            for (Grade g : gradeList) {
                try {

                    // Ensure the grade object and its value are not null
                    if (g != null && g.getStudentGrade() != null) {
                        double gradeVal = g.getStudentGrade();
                        sum += gradeVal;
                        validGradesCount++;

                        if (gradeVal > highest) {
                            highest = gradeVal;
                        }
                    }
                } catch (NumberFormatException e) {
                    // skip invalid numbers
                }

            }

            if (validGradesCount > 0) {
                double average = sum / validGradesCount;
                averageGradeLabel.setText(String.format(java.util.Locale.US, "%.1f%%", average));
                highestGradeLabel.setText(String.format(java.util.Locale.US, "%.1f", highest));
            } else {
                averageGradeLabel.setText("0");
                highestGradeLabel.setText("0");
            }
        } else {

            averageGradeLabel.setText("0%");
            highestGradeLabel.setText("0");
        }
    }

    // Toggle between light mode and dark mode themes dynamically
    @FXML
    public void onToggleModeClicked() {
        isDarkMode = !isDarkMode;
        StudentGradeManager.toggleProgramTheme(isDarkMode);
        if (isDarkMode) {
            toggleModeBtn.setText("☀ Light Mode");
        } else {
            toggleModeBtn.setText("🌙 Dark Mode");
        }
    }

    @FXML
    public void onAddStudentClicked() {
        try {
            StudentGradeManager.setRoot("AddStudentUI");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onAddGradesClicked() {
        try {
            StudentGradeManager.setRoot("AddGradeUI");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onViewStudentsClicked() {
        try {
            StudentGradeManager.setRoot("ViewStudents");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onViewReportsClicked() {
        try {
            StudentGradeManager.setRoot("ViewReports");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
