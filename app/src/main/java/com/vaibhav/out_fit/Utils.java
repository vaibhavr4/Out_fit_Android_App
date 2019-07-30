package com.vaibhav.out_fit;

public class Utils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    public static final String phoneregEx ="(0/91)?[7-9][0-9]{9}";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String[] sports = new String[]{
            "Cricket", "Soccer", "Baseball", "Running", "Cycling", "Jogging", "Tennis",
            "Badminton", "Basketball", "Volleyball","Rugby"};
}
