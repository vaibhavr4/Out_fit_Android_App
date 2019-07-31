package com.vaibhav.out_fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.UserSportsModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SportsInterestGridActivity extends AppCompatActivity {

    private GridView gridView;
    private View btnGo;
    private ArrayList<String> selectedStrings;
    private static final String[] sports = Utils.sports;
    FirebaseFirestore db;
    UserSportsModel userSportsModel;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String currentUserId = currentFirebaseUser.getUid();

    ArrayList<String> currentSportsInterest = new ArrayList();
    List<String> sportsArrayList = Arrays.asList(sports);
    Activity activity;
    SportsGridViewAdapter adapter;
    ArrayList<Integer> highlightPositions = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_grid);

        activity = this;
        gridView = (GridView) findViewById(R.id.grid);
        btnGo = findViewById(R.id.button);

        selectedStrings = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        //get current sports interest to highlight
        db.collection("user_sports")
                .document(currentUserId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userSportsModel = documentSnapshot.toObject(UserSportsModel.class);
                        currentSportsInterest = userSportsModel.getSports();
                            //pass position to adapter for already interested sports
                            for(String sport:currentSportsInterest)
                            {
                                int position = sportsArrayList.indexOf(sport);
                                highlightPositions.add(position);
                            }
                            Log.d("highlightedPos",Integer.toString(highlightPositions.size()));
                        adapter = new SportsGridViewAdapter(sports,activity ,highlightPositions);
                        gridView.setAdapter(adapter);
                        selectedStrings = currentSportsInterest;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Query invalid");
                    }
                });


        Log.d("highlightedPos",Integer.toString(highlightPositions.size()));


        //on grid item click highlight- select or deselect
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((SportsGridItemView) v).display(false);
                    selectedStrings.remove((String) parent.getItemAtPosition(position));
                } else {
                    adapter.selectedPositions.add(position);
                    ((SportsGridItemView) v).display(true);
                    selectedStrings.add((String) parent.getItemAtPosition(position));
                }
            }
        });

        //set listener for Button event
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update the user's sports interest to database
                db = FirebaseFirestore.getInstance();

                userSportsModel = new UserSportsModel(selectedStrings);
                db.collection("user_sports")
                        .document(currentUserId)
                        .set(userSportsModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "user sports snapshot added ID:"+currentUserId);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "user sports snapshot failed" + currentUserId);

                            }
                        });

                Toast.makeText(activity, "Interest Updated Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SportsInterestGridActivity.this, MaterialTabActivity.class);
                intent.putStringArrayListExtra("SELECTED_SPORTS", selectedStrings);
                startActivity(intent);
            }
        });
    }
}
