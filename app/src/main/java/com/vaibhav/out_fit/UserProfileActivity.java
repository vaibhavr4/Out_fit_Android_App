package com.vaibhav.out_fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import utils.UserModel;
import utils.UserSportsModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserProfileActivity extends AppCompatActivity {

    FirebaseFirestore db;
    UserModel userModel;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String currentUserId = currentFirebaseUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final EditText FullName = (EditText) findViewById(R.id.fullName_prof);
        final EditText Email = (EditText) findViewById(R.id.userEmailId_prof);
        final EditText Mobile = (EditText) findViewById(R.id.mobileNumber_prof);
        final EditText Location = (EditText) findViewById(R.id.location_prof);
        final EditText Password = (EditText) findViewById(R.id.password_prof);

        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userModel = documentSnapshot.toObject(UserModel.class);
                        FullName.setText(userModel.getName());
                        Email.setText(userModel.getEmail());
                        Mobile.setText(userModel.getPhone());
                        Location.setText(userModel.getLocation());
                        Password.setText(userModel.getPassword());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Profile not invalid");
                    }
                });

        final Button updateProfile = (Button) findViewById(R.id.updateProfile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setName(FullName.getText().toString());
                userModel.setEmail(Email.getText().toString());
                userModel.setPhone(Mobile.getText().toString());
                userModel.setLocation(Location.getText().toString());
                userModel.setPassword(Password.getText().toString());



                db.collection("users").document(currentUserId)
                        .set(userModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                currentFirebaseUser.updatePassword(Password.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Updated Successfully",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(UserProfileActivity.this, MaterialTabActivity.class);
                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Unable to Update. Try again later",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to Update. Try again later",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}
