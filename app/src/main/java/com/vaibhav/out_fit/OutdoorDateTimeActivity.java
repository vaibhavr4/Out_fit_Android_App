package com.vaibhav.out_fit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import utils.FriendListModel;
import utils.FriendsInviteBlockModel;
import utils.OutdoorEventModel;
import utils.OutdoorInviteFriendsModel;
import utils.UserEventModel;
import utils.UserModel;

public class OutdoorDateTimeActivity extends AppCompatActivity {

    TimePickerDialog picker;
    EditText eText;
    ImageButton closeButton;

    DatePickerDialog picker2;
    EditText eText2;
    Button btnGet2;

    String sport;
    FirebaseFirestore db;
    ListView listView;
    String currUserName;
    String currentUserId;
    String eventId;

    OutdoorInviteFriendsModel outdoorInviteFriendsModel;
    FriendListModel friendListModel = new FriendListModel();
    ArrayList<OutdoorInviteFriendsModel> friends = new ArrayList<>();
    ArrayList<OutdoorInviteFriendsModel> friendsInvited = new ArrayList<>();

    UserEventModel userEventModel = new UserEventModel();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_outdoor_date_time);
        final String location = getIntent().getStringExtra("PLAYGROUND");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("InvitedFriends"));

        sport = getIntent().getStringExtra("SPORT");
        eText=(EditText) findViewById(R.id.TimeText);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(OutdoorDateTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        eText2=(EditText) findViewById(R.id.DateText);
        eText2.setInputType(InputType.TYPE_NULL);
        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker2 = new DatePickerDialog(OutdoorDateTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker2.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                picker2.show();
            }
        });



        listView = findViewById(R.id.displayFriendsList);

        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                listView.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        if (friends.isEmpty()) {
            populateList(new MyCallback2() {
                @Override
                public void OnCallback(ArrayList<OutdoorInviteFriendsModel> list) {
                    OutdoorInviteFriendsAdapter myAdapter = new OutdoorInviteFriendsAdapter(getApplicationContext(),list);
                    listView.setAdapter(myAdapter);
                }
            });
        }
//        populateList();


        final EditText eventDesc = (EditText) findViewById(R.id.eventDesc);
        btnGet2=(Button)findViewById(R.id.confirmEvent);
        btnGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String eventDescription = eventDesc.getText().toString();
                String eventDate = eText2.getText().toString();
                String eventLocation = location;
                String eventTime = eText.getText().toString();

                if(eventDate.length()==0 || eventDescription.length()==0 || eventLocation.length()==0
                || eventTime.length()==0){
                    Toast.makeText(OutdoorDateTimeActivity.this, "Enter All Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    final OutdoorEventModel outdoorEventModel = new OutdoorEventModel(currentUserId,currUserName,
                            eventDescription,eventDate,eventTime,eventLocation,sport,new ArrayList<String>());


                    db.collection("events")
                            .add(outdoorEventModel)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    eventId = documentReference.getId();
                                    for(OutdoorInviteFriendsModel friendInvite:friendsInvited)
                                    {
                                        friendInvite.setEventId(eventId);
                                        final DatabaseReference databaseChildRef = databaseReference.child("EventRequests");
                                        final DatabaseReference databaseChildOfChildRef = databaseChildRef.child(friendInvite.getReceiverId());
                                        databaseChildOfChildRef.push().setValue(friendInvite);
                                    }

                                    final DocumentReference documentRef = db.collection("user_events")
                                            .document(currentUserId);
                                    documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful())
                                            {
                                                DocumentSnapshot documentSnapshot = task.getResult();
                                                if(documentSnapshot.exists())
                                                {
                                                    userEventModel = documentSnapshot.toObject(UserEventModel.class);
                                                    userEventModel.getUserEvent().add(eventId);
                                                }
                                                else
                                                {
                                                    ArrayList<String> temp = new ArrayList();
                                                    temp.add(eventId);
                                                    userEventModel.setUserEvent(temp);
                                                }
                                                documentRef.set(userEventModel, SetOptions.merge());
                                            }
                                            CharSequence text = "Event created Successfully";
                                            int duration = Toast.LENGTH_LONG;
                                            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
                                    });

                                }
                            });



//                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if(task.isSuccessful())
//                        {
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            if(documentSnapshot.exists()) {
//                                outdoorEventList = documentSnapshot.toObject(ArrayList<OutdoorEventModel>);
//                            }
//                        }
//                    }
//                })




                    Intent intent = new Intent(OutdoorDateTimeActivity.this,OutdoorEventActivity.class);
//                startActivity(intent);
                    Bundle args = new Bundle();

                    args.putString("DESC",eventDescription);
                    args.putString("DATE",eventDate);
                    args.putString("PLAYGROUND", eventLocation);
                    args.putString("TIME", eventTime);
                    args.putString("SPORT", sport);
                    args.putString("CREATOR",currUserName);
                    args.putParcelableArrayList("friendList",friendsInvited);
                    intent.putExtra("BUNDLE",args);

                    startActivity(intent);
                }

            }
        });
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Bundle args = intent.getBundleExtra("BUNDLE");
            friendsInvited = (ArrayList<OutdoorInviteFriendsModel>)args.getSerializable("friendList");
        }
    };


    private void populateList(final MyCallback2 callback){
        Log.d("friend_list","STart:"+sport.toString());
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        currentUserId = currentFirebaseUser.getUid();
        final DocumentReference currUser = db.collection("users").document(currentUserId);
        currUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserModel userModel = documentSnapshot.toObject(UserModel.class);
                currUserName = userModel.getName();
                final DocumentReference sportList = db.collection("friend_list").document(currentUserId);
                sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        FriendListModel friendListModel = documentSnapshot.toObject(FriendListModel.class);
//                Log.d("friend_list",documentSnapshot.getData().toString());
                        ArrayList<String> userIds = new ArrayList();

                        if(friendListModel==null)
                        {
//                    friendList = new ArrayList<>();
                            callback.OnCallback(friends);
                        }
                        else
                        {
                            userIds = friendListModel.getFriendList().get(sport.toString());
                            if(userIds==null)
                                callback.OnCallback(friends);
                            else if(!userIds.isEmpty()) {
                                Log.d("CheckSize",String.valueOf(userIds.size()));
                                for (String id : userIds) {
                                    final String receiverId = id;
                                    Log.d("friend_list", "One id:" + id.toString());
                                    final DocumentReference userDocRef = db.collection("users").document(id);
                                    userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            UserModel userModel = documentSnapshot.toObject(UserModel.class);
                                            Log.d("friend_list", "UserModel" + documentSnapshot.getData().toString());
                                            Log.d("friend_list", "UserModel" + userModel.getName().toString());
                                            outdoorInviteFriendsModel = new OutdoorInviteFriendsModel(currentUserId,currUserName,receiverId,userModel.getName(),sport);
                                            friends.add(outdoorInviteFriendsModel);
//                                friendList.add(userModel.getName().toString());
                                            callback.OnCallback(friends);
                                        }
                                    });
                                }
                                Log.d("friend_list", friends.toString());
                            }
                            else
                            {
                                callback.OnCallback(friends);
                            }
                        }


                    }
                });
            }
        });


    }


    public void Invite(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Invited for the game";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();
    }
}

interface MyCallback2{
    void OnCallback(ArrayList<OutdoorInviteFriendsModel> list);
}

