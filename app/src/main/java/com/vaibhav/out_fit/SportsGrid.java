package com.vaibhav.out_fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utils.UserSportsModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SportsGrid extends AppCompatActivity {

    private GridView gridView;
    private View btnGo;
    private ArrayList<String> selectedStrings;
    private static final String[] sports = Utils.sports;
    FirebaseFirestore db;
    UserSportsModel userSportsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_grid);

        gridView = (GridView) findViewById(R.id.grid);
        btnGo = findViewById(R.id.button);

        selectedStrings = new ArrayList<>();

        final SportsGridViewAdapter adapter = new SportsGridViewAdapter(sports, this,new ArrayList());
        gridView.setAdapter(adapter);
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
                db = FirebaseFirestore.getInstance();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                final String currentUserId = currentFirebaseUser.getUid();
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
                Intent intent = new Intent(SportsGrid.this, MaterialTabActivity.class);
                intent.putStringArrayListExtra("SELECTED_SPORTS", selectedStrings);
                startActivity(intent);
            }
        });
    }
}
