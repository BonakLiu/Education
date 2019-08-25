package com.example.education.objects;

public class Grade {
    private String courseName;
    private String grade;
    private String gpa;

    public Grade(String courseName, String grade, String gpa) {
        this.courseName = courseName;
        this.grade = grade;
        this.gpa = gpa;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }
}
