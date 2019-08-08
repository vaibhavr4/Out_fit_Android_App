package com.vaibhav.out_fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class FriendRequestProfile extends AppCompatActivity {
    FirebaseFirestore db;
    UserModel userModel;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_request_profile);

        final String friend_id =getIntent().getStringExtra("friend_id");

        final TextView FullName = (TextView) findViewById(R.id.friendname_prof);
        final TextView Email = (TextView) findViewById(R.id.friendemail_prof);
        final TextView Mobile = (TextView) findViewById(R.id.friendmobile_prof);
        final TextView Location = (TextView) findViewById(R.id.friendlocation_prof);

        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(friend_id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userModel = documentSnapshot.toObject(UserModel.class);
                        FullName.setText(userModel.getName());
                        Email.setText(userModel.getEmail());
                        Mobile.setText(userModel.getPhone());
                        Location.setText(userModel.getLocation());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Profile not invalid");
                    }
                });


    }
}
