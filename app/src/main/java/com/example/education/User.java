package com.example.education;

class User {

    private static final User ourInstance = new User();

    static User getInstance() {
        return ourInstance;
    }

    private static String id = "";
    private static String name = "";
    private static String birthday = "";
    private static String telephone = "";
    private static String college = "";

    private User() {
    }
}
