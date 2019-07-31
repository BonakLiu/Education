package com.example.education.objects;

public class Course {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public Course(String tid, String tname, String tcredits) {
        id = tid;
        name = tname;
        credits = tcredits;
    }
    private String id;
    private String name;
    private String credits;
}
