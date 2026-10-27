/*
 * Student - model class
 */
package com.mycompany.studentgrademanager;

import java.io.Serializable;

public class Student implements Serializable { //Serializable -> this class can be converted into a byte stream

    private String studentName;
    private String studentId;     //it's not int to validate it more 

    public Student(String studentName, String studentId) {
        setStudentName(studentName);
        setStudentId(studentId);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    // Validate student name is not null or empty before setting
    public void setStudentName(String studentName) {

        //validate before inserting
        if (studentName == null || studentName.isEmpty()) {
            throw new IllegalArgumentException("Fill the name field please");
        }
        this.studentName = studentName;
    }

    // Validate student ID length (must be exactly 8 or 9 digits)
    public void setStudentId(String studentId) {

        //validate before inserting
        if (studentId == null || (studentId.length() != 8 && studentId.length() != 9)) {
            throw new IllegalArgumentException("ID number has to contain 8-9 numbers");
        }
        this.studentId = studentId;

    }
}
