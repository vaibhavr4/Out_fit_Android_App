package com.vaibhav.out_fit;

import android.content.Intent;
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


import utils.MyEventsAdapter;
import utils.MyEventsAdapterItem;
import utils.OutdoorEventModel;
import utils.UserEventModel;
import utils.UserSportsModel;

public class NewsFeedTabFragment extends Fragment {

    FirebaseFirestore db;
    ListView newsListView;
    ListView eventsListView;
    ArrayList<String> newsItems = new ArrayList<>();
    ArrayList<MyEventsAdapterItem> eventsItems = new ArrayList<>();


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
                    MyEventsAdapter eventsAdapter =new MyEventsAdapter(getContext(),eventsItems);
                    eventsListView.setAdapter(eventsAdapter);
                }
            });
        }
        else
        {
                    MyEventsAdapter eventsAdapter =new MyEventsAdapter(getContext(),eventsItems);
                    eventsListView.setAdapter(eventsAdapter);
        }

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyEventsAdapterItem myEventsAdapterItem = (MyEventsAdapterItem) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),MyEventViewActivity.class);
                intent.putExtra("EventId",myEventsAdapterItem.getEvents());
                startActivity(intent);
            }
        });


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

    }

    private void populateEvents(final MyEventsFeedCallback callback){
        Log.d("NewsMyEvents","HERE");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        final String currentUserId = currentFirebaseUser.getUid();
        final DocumentReference sportList = db.collection("user_events").document(currentUserId);
        Log.d("NewsMyEvents",sportList.toString());
        sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    UserEventModel userEventModel = documentSnapshot.toObject(UserEventModel.class);
                    for (String event : userEventModel.getUserEvent()) {
                        Log.d("NewsMyEvents","Event:"+event.toString());
                        MyEventsAdapterItem myEventsAdapterItem = new MyEventsAdapterItem();
                        myEventsAdapterItem.setEvents(event);
                        eventsItems.add(myEventsAdapterItem);

                    }
                    Log.d("NewsMyEvents","Event:"+eventsItems.toString());
                    callback.OnCallback(userEventModel.getUserEvent());

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