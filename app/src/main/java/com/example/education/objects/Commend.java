package com.example.education.objects;

public class Commend {
    private String coursename;
    private String mark;
    private String commend;

    public Commend(String coursename, String mark, String commend) {
        this.coursename = coursename;
        this.mark = mark;
        this.commend = commend;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCommend() {
        return commend;
    }

    public void setCommend(String commend) {
        this.commend = commend;
    }
}
