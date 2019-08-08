package com.vaibhav.out_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utils.MyEventsAdapterItem;
import utils.OutdoorEventModel;
import utils.UserEventModel;
import utils.UserModel;

public class MyEventViewActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> friendsComing = new ArrayList<>();
    FirebaseFirestore db;
    String eventId;
    OutdoorEventModel tempModel = new OutdoorEventModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_view);

        eventId = getIntent().getStringExtra("EventId");

        final TextView sport = findViewById(R.id.my_event_displaySport);
        final TextView location = findViewById(R.id.my_event_displayLocation);
        final TextView date = findViewById(R.id.my_event_displayDate);
        final TextView time = findViewById(R.id.my_event_displayTime);
        final TextView sportDesc = findViewById(R.id.my_event_SportDesc);
        final TextView senderName = findViewById(R.id.my_event_eventCreator);
        listView = findViewById(R.id.my_event_eventFriendList);

        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                listView.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });


        populateEvents(new MyEventsViewCallback() {
                           @Override
                           public void OnCallback(OutdoorEventModel eventModel) {
                               tempModel = eventModel;
                               sport.setText(eventModel.getSport());
                               location.setText(eventModel.getLocation());
                               date.setText(eventModel.getDate());
                               time.setText(eventModel.getTime());
                               sportDesc.setText(eventModel.getEventDescription());
                               senderName.setText(eventModel.getCreatorName());

                               populateFriends(new MyEventsFriendsCallback() {
                                   @Override
                                   public void OnCallback(ArrayList<String> friends) {
                                       Log.d("FriendsCOming","Return: "+friends.toString());
                                       ArrayAdapter <String> friendListAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, friends);
                                       listView.setAdapter(friendListAdapter);
                                   }
                               });


                           }
                       });

    }


    private void populateEvents(final MyEventsViewCallback callback){
        Log.d("UserEvents","HERE");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        final String currentUserId = currentFirebaseUser.getUid();
        final DocumentReference sportList = db.collection("events").document(eventId);
        Log.d("UserEvents",sportList.toString());
        sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    OutdoorEventModel eventModel = documentSnapshot.toObject(OutdoorEventModel.class);
                    if(eventModel!=null)
                    {
                        callback.OnCallback(eventModel);
                    }

                }

            }
        });

    }

    private void populateFriends(final MyEventsFriendsCallback callback){
        Log.d("UserEvents","HERE");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        for(String userId:tempModel.getFriendsJoined()) {
            final DocumentReference sportList = db.collection("users").document(userId);
            sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        UserModel userModel = documentSnapshot.toObject(UserModel.class);
                        Log.d("FriendsCOming",userModel.getName());
                        friendsComing.add(userModel.getName());
                        callback.OnCallback(friendsComing);
                    }

                }
            });

        }


    }

    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(MyEventViewActivity.this, MaterialTabActivity.class);
        startActivity(intent);
    }


}
interface MyEventsViewCallback{
    void OnCallback(OutdoorEventModel eventModel);
}

interface MyEventsFriendsCallback{
    void OnCallback(ArrayList<String> eventModel);
}
