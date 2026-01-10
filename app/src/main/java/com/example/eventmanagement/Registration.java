package com.example.eventmanagement;

public class Registration {

    private String fullName;
    private String email;
    private String contactNo;

    public Registration() {
        // Required for Firebase
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }
}
