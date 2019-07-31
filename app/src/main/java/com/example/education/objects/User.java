package com.example.education.objects;

public class User {

    private static final User ourInstance = new User();

    static User getInstance() {
        return ourInstance;
    }

    private static int userType = 3;//用户类型： 0学生 1老师 2管理员

    //共有属性
    private static int stage = 0;
    private static String id = "";//学生教师管理员的id
    private static String name = "";//名字
    private static String college = "";//学院
    private static String major = "";//专业
    //学生属性
    private static String grade = "";//年级
    private static String banji = "";//班别
    //教师属性
    private static String rank = "";//职称
    private static String tel = "";//电话
    //管理员属性

    public static int getUserType() {
        return userType;
    }

    public static void setUserType(int userType) {
        User.userType = userType;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getCollege() {
        return college;
    }

    public static void setCollege(String college) {
        User.college = college;
    }

    public static String getMajor() {
        return major;
    }

    public static void setMajor(String major) {
        User.major = major;
    }

    public static String getGrade() {
        return grade;
    }

    public static void setGrade(String grade) {
        User.grade = grade;
    }

    public static String getBanji() {
        return banji;
    }

    public static void setBanji(String banji) {
        User.banji = banji;
    }

    public static String getRank() {
        return rank;
    }

    public static void setRank(String rank) {
        User.rank = rank;
    }

    public static String getTel() {
        return tel;
    }

    public static void setTel(String tel) {
        User.tel = tel;
    }


}
