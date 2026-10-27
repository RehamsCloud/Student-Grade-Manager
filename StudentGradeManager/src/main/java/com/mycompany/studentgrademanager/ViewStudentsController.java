package com.mycompany.studentgrademanager;

import java.io.*;
import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewStudentsController {

    @FXML
    private TableColumn<Student, Double> averageColumn;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Student, String> gradeColumn;

    @FXML
    private TableColumn<Student, String> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button addInfoButton;

    @FXML
    private Button homeButton;

    private ArrayList<Grade> savedGrades = new ArrayList<>();
    // Initialize table columns and load saved data when screen opens
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        
        // Calculate student grade average dynamically based on saved grades
        gradeColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            double average = calculateAverageForStudent(student.getStudentId(), savedGrades);
            return javafx.beans.binding.Bindings.createStringBinding(() -> String.valueOf(average));
        });
        averageColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            double average = calculateAverageForStudent(student.getStudentId(), savedGrades);
            return new SimpleDoubleProperty(average).asObject();
        });

        loadStudentData();
    }
    // Load students and grades from binary files into the table
    private void loadStudentData() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        ArrayList<Student> savedStudents = readStudents();
        savedGrades = readGrades();

        studentList.addAll(savedStudents);
        studentsTable.setItems(studentList);
    }

    private ArrayList<Student> readStudents() {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File("students.dat");

        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

                students = (ArrayList<Student>) ois.readObject();

            } catch (Exception e) {
                System.out.println("Error loading students: " + e.getMessage());
            }
        }
        return students;
    }

    private ArrayList<Grade> readGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        File file = new File("grades.dat");

        if (file.exists() && file.length() > 0) {
            try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {

                grades = (ArrayList<Grade>) ois.readObject();

            } catch (Exception e) {
                System.out.println("Error loading grades: " + e.getMessage());
            }
        }
        return grades;
    }
    // Helper method to calculate GPA/average for a specific student ID
    private double calculateAverageForStudent(String studentId, ArrayList<Grade> grades) {
        double total = 0;
        int count = 0;

        for (Grade grade : grades) {
            if (grade.getStudentId().equals(studentId)) {
                // Ensure the grade is not null before checking or adding it
                if (grade.getStudentGrade() != null) {
                    total += grade.getStudentGrade();
                    count++;
                }
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return total / count;
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDashboard(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddInfo(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("AddStudentUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleHome(ActionEvent event) {
        try {
            StudentGradeManager.setRoot("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
