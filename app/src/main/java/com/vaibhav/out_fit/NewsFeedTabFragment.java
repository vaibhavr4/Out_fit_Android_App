package com.vaibhav.out_fit;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utils.NewsFeedAdapterItem;
import utils.OutdoorEventModel;
import utils.UserEventModel;
import utils.UserSportsModel;

public class NewsFeedTabFragment extends Fragment {

    FirebaseFirestore db;
    ListView newsListView;
    ListView eventsListView;
    ArrayList<String> newsItems = new ArrayList<>();
    ArrayList<String> eventsItems = new ArrayList<>();
    ArrayList<NewsFeedAdapterItem> newsFeed = new ArrayList<>();
    ArrayList<NewsFeedAdapterItem> eventsFeed = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newsListView = view.findViewById(R.id.newsList);
        eventsListView = view.findViewById(R.id.eventsList);

        populateList();

        ArrayAdapter newsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsItems)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };


//---------------------------------------------My Events-----------------------------------------------------
        if(eventsItems.isEmpty())
        {
            populateEvents(new MyEventsFeedCallback() {
                @Override
                public void OnCallback(ArrayList<String> list) {
                    ArrayAdapter eventsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list)
                    {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.WHITE);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };
                    eventsListView.setAdapter(eventsAdapter);
                }
            });
        }
        else
        {
            eventsItems.clear();
            populateEvents(new MyEventsFeedCallback() {
                @Override
                public void OnCallback(ArrayList<String> list) {
                    ArrayAdapter eventsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list)
                    {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.WHITE);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };
                    eventsListView.setAdapter(eventsAdapter);
                }
            });
        }





        newsListView.setAdapter(newsAdapter);




    }

    private void populateList() {
        newsItems.add("After a fantastic World Cup, Jason Roy has now been picked for the one-off Test against Ireland");
        newsItems.add("Novak Djokovic says his epic Wimbledon final victory over Roger Federer was " +
                "his most \"mentally demanding\" match - and he even had to tell himself the partisan crowd was cheering for him.");
        newsItems.add("Neil Lennon says Arsenal are yet to up their bid for Celtic defender Kieran Tierney.");
        newsItems.add("Somerset fast-bowler Lewis Gregory has also been called up for the first time");
        newsItems.add("After a fantastic World Cup, Jason Roy has now been picked for the one-off Test against Ireland");
        newsItems.add("Neil Lennon says Arsenal are yet to up their bid for Celtic defender Kieran Tierney.");

//        eventsItems.add("Events: Play Cricket- Scheduled for 5PM today");
//        eventsItems.add("Events: Play Basketball- Scheduled for 6PM today");
//        eventsItems.add("Events: Play Cricket- Scheduled for 6PM Friday");
    }

    private void populateEvents(final MyEventsFeedCallback callback){
        Log.d("UserEvents","HERE");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        final String currentUserId = currentFirebaseUser.getUid();
        final DocumentReference sportList = db.collection("user_events").document(currentUserId);
        Log.d("UserEvents",sportList.toString());
        sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    UserEventModel userEventModel = documentSnapshot.toObject(UserEventModel.class);
                    for (String event : userEventModel.getUserEvent()) {
                        Log.d("UserEvents","Event:"+event.toString());
                        final DocumentReference eventList = db.collection("events").document(event);
                        eventList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    OutdoorEventModel outdoorEventModel = documentSnapshot.toObject(OutdoorEventModel.class);
                                    Log.d("UserEvents","OutdoorEVentModel:"+outdoorEventModel.toString());
                                    eventsItems.add(outdoorEventModel.getEventDescription().toString());
                                }
                                callback.OnCallback(eventsItems);
                            }
                        });

                    }

                }

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_newsfeed_layout, container, false);
    }
}

interface MyEventsFeedCallback{
    void OnCallback(ArrayList<String> list);
}