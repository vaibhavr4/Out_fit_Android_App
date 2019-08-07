package com.vaibhav.out_fit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import utils.UserModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Utils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    public static final String phoneregEx ="(0/91)?[1-9][0-9]{9}";
    public static final String passwordRegEx = "(.{6,16})";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String[] sports = new String[]{
            "Cricket", "Soccer", "Baseball", "Running", "Cycling", "Jogging", "Tennis",
            "Badminton", "Basketball", "Volleyball","Rugby"};

    // Firestore parameters
    FirebaseFirestore db;
    UserModel userModel;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String currentUserId = currentFirebaseUser.getUid();




    //Methods to access common data from firestore


}
