package com.example.education.objects;

public class Section {
    private String classroom;
    private String courseid;
    private String coursename;
    private String day;
    private String enddate;
    private String sectionid;
    private String startdate;
    private String yuliang;
    private String tid;
    private String timeslot;
    public String getYuliang() {
        return yuliang;
    }

    public void setYuliang(String yuliang) {
        this.yuliang = yuliang;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }



    public Section(String classroom, String courseid, String coursename, String day, String enddate, String sectionid,
                   String startdate, String tid, String timeslot,String yuliang) {
        this.classroom = classroom;
        this.courseid = courseid;
        this.coursename = coursename;
        this.day = day;
        this.enddate = enddate;
        this.sectionid = sectionid;
        this.startdate = startdate;
        this.tid = tid;
        this.timeslot = timeslot;
        this.yuliang = yuliang;
    }
}
