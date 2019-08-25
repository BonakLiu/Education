package com.example.education.objects;

public class Takes {
    private String tid;
    private String sectionid;
    private String tname;
    private String sname;

    public Takes(String tid, String sectionid, String tname, String sname) {
        this.tid = tid;
        this.sectionid = sectionid;
        this.tname = tname;
        this.sname = sname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
