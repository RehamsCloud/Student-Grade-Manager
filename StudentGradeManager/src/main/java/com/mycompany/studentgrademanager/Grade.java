package com.mycompany.studentgrademanager;

import java.io.Serializable;

public class Grade implements Serializable {

    private String studentId;
    private String subject;
    private Double studentGrade;

    public Grade(String studentId, String subject, Double studentGrade) {
        setStudentId(studentId);
        setSubject(subject);
        setStudentGrade(studentGrade);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public Double getStudentGrade() {
        return studentGrade;
    }

    // Validate student ID length (must be 8 or 9 digits)
    public void setStudentId(String studentId) {
        if (studentId == null || (studentId.length() != 8 && studentId.length() != 9)) {
            throw new IllegalArgumentException("ID number has to contain 8-9 numbers");
        }
        this.studentId = studentId;
    }

    // Validate subject field is not empty
    public void setSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Fill the subject field please");
        }
        this.subject = subject;
    }

    // Validate grade value is within valid academic range (0.0 to 100.0)
    public void setStudentGrade(Double studentGrade) {
        if (studentGrade == null) {
            throw new IllegalArgumentException("Fill the grade field please");
        }
        // Check academic boundaries
        if (studentGrade < 0.0 || studentGrade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0");
        }
        this.studentGrade = studentGrade;
    }
}
