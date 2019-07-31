package com.vaibhav.out_fit;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import utils.UserSportsModel;

public class FriendsInviteBlockActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ListView listView;
    ArrayList<FriendsInviteBlockAdapterItem> friends = new ArrayList<FriendsInviteBlockAdapterItem>();
    String sport_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_invite_block);
        final TextView sport = findViewById(R.id.sportsNameFriends);
        sport_name = getIntent().getStringExtra("SPORT");
        sport.setText(sport_name);

        listView = findViewById(R.id.friendsListInviteBlock);
        if (friends.isEmpty()) {
            populateList(new MyCallbackFriends() {
                @Override
                public void OnCallback(ArrayList<FriendsInviteBlockAdapterItem> list) {
                    Log.d("USERS", "friends in oncreate " + friends);
                    FriendsInviteBlockAdapter myAdapter = new FriendsInviteBlockAdapter(sport.getContext(), friends);
                    listView.setAdapter(myAdapter);
                }
            });
        }

    }

    private void populateList(final MyCallbackFriends callback){

        db = FirebaseFirestore.getInstance();
        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final CollectionReference sportList = db.collection("user_sports");
        sportList.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //Log.d("USERS", queryDocumentSnapshots.getDocuments().toString());
                final List allDocs = queryDocumentSnapshots.getDocuments();
                db.collection("user_sports").document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserSportsModel curUser = documentSnapshot.toObject(UserSportsModel.class);

                        ListIterator iter = allDocs.listIterator();
                        while(iter.hasNext())
                        {
                            final QueryDocumentSnapshot doc = (QueryDocumentSnapshot) iter.next();
                            if (!doc.getId().equals(currentUserId))
                            {
                            //    Log.d("USERS", doc.getId());
                                UserSportsModel newUser = doc.toObject(UserSportsModel.class);
                            //    Log.d("USERS",doc.getData().values().toString());
                                Log.d("USERS", "loggedinuser "+ curUser.getSports().toString());
                                Log.d("USERS", "new user "+newUser.getSports().toString());
                                Log.d("USERS", "sportname - "+sport_name);
                                if (newUser.getSports().contains(sport_name))
                                {
                                    Log.d("USERS", "common sport");
                                    db.collection("users").document(doc.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            final String newUserName = documentSnapshot.getString("name");
                                            Log.d("USERS", documentSnapshot.getData().toString());
                                            Log.d("USERS", documentSnapshot.getString("name"));
                                            friends.add(new FriendsInviteBlockAdapterItem(newUserName, doc.getId(),sport_name));
                                            callback.OnCallback(friends);
                                        }
                                    });
                                }
                            }

                        }

                    }
                });

            }
        });


        callback.OnCallback(friends);
    }

//    public void InviteFriends(View view) {
//        Context context = getApplicationContext();
//        CharSequence text = "Invited";
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
//        toast.show();
//    }
//
//    public void Block(View view) {
//        Context context = getApplicationContext();
//        CharSequence text = "Blocked";
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
//        toast.show();
//    }
}

interface MyCallbackFriends{
    void OnCallback(ArrayList<FriendsInviteBlockAdapterItem> list);
}