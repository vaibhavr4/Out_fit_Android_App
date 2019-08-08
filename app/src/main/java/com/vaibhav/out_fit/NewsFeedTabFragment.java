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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

        TextView noEvents = (TextView) view.findViewById(R.id.NoEvents);
        noEvents.setText("No Events Scheduled right now!");
        eventsListView.setEmptyView(noEvents);

        if(newsItems.isEmpty()){
            populateList(new MyCallbackNewsFeed() {
                @Override
                public void OnCallback(ArrayList<String> list) {
                    getNews(list);
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
                    newsListView.setAdapter(newsAdapter);
                }
            });
        }
        else {
            newsItems.clear();
            populateList(new MyCallbackNewsFeed() {
                @Override
                public void OnCallback(ArrayList<String> list) {
                    getNews(list);
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
                    newsListView.setAdapter(newsAdapter);
                }
            });
        }



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

    }

    private void getNews(ArrayList<String> sportsArray) {

        for (int i = 0; i < sportsArray.size(); i++) {
            switch (sportsArray.get(i)) {
                case "Cricket":
                    newsItems.add("After a fantastic World Cup, Jason Roy has now been picked for the one-off Test against Ireland");
                    newsItems.add("Somerset fast-bowler Lewis Gregory has also been called up for the first time");
                    break;
                case "Soccer":
                    newsItems.add("Neil Lennon says Arsenal are yet to up their bid for Celtic defender Kieran Tierney.");
                    newsItems.add("Wayne Rooney is leaving his $2.7 million a year salary in the US to play soccer in England’s second tier");
                    break;
                case "Baseball":
                    newsItems.add("Dodgers walk it off for 10th time to sweep Cards");
                    newsItems.add("Cubs feel they lucked out by signing Lucroy");
                    break;
                case "Running":
                    newsItems.add("At 43 years old, Bernard Lagat of the United States is making his marathon debut in New York.");
                    newsItems.add("Vienna Named the Venue for Eliud Kipchoge’s Sub-2:00 Marathon Attempt");
                    break;
                case "Cycling":
                    newsItems.add("Chris Froome: Egan Bernal's life is about to change forever\n" +
                            "Four-time Tour de France winner says 2019 winner 'is going to go on to do amazing things in our sport'");
                    newsItems.add("Tour de Pologne: Ackermann struggles in uphill sprint but keeps yellow\n" +
                            "Majka finishes 6th on stage 5 and is 15th overall as mountains wait");
                    break;
                case "Jogging":
                    newsItems.add("Tom Hanks crashes wedding when he goes for a jog through Central Park");
                    newsItems.add("GOING RUNNING CAN BOOST YOUR BRAINPOWER, SAY SCIENTISTS");
                    break;
                case "Tennis":
                    newsItems.add("Novak Djokovic says his epic Wimbledon final victory over Roger Federer was " +
                            "his most \"mentally demanding\" match - and he even had to tell himself the partisan crowd was cheering for him.");
                    newsItems.add("Rogers Cup: Rafael Nadal beats Dan Evans in Montreal");
                    newsItems.add("DOWN A BREAK IN EACH SET, SERENA WILLIAMS ROARS BACK IN TORONTO RETURN");
                    break;
                case "Badminton":
                    newsItems.add("Badminton World Championships: PV Sindhu, Saina Nehwal To Face Hard Battles In Quarters");
                    newsItems.add("Carolina Marin Pulls Out Of World Championships Due To Knee Injury");
                    break;
                case "Basketball":
                    newsItems.add("Donovan “Spida” Mitchell turning into USA Basketball’s Captain America");
                    newsItems.add("Duke basketball ‘super team’ talks drive next two recruiting classes");
                    break;
                case "Volleyball":
                    newsItems.add("LUKES PACES SAN DIEGO IN 3-0 UPSET OF NO. 11 USC, ON TO FIRST SWEET 16 SINCE 2013");
                    newsItems.add("STANFORD WINS 28TH IN A ROW WITH SECOND ROUND SWEEP OF LMU");
                    break;
                case "Rugby":
                    newsItems.add("South Africa rotate front row for Rugby Championship tie with Argentina");
                    newsItems.add("Rugby World Cup: England wing Jack Nowell an injury doubt");
                    break;
                default:
                    break;
            }

        }
    }

    private void populateList(final MyCallbackNewsFeed callback) {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        final String currentUserId = currentFirebaseUser.getUid();
        final DocumentReference UserSportListfromDb = db.collection("user_sports").document(currentUserId);

        UserSportListfromDb.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    UserSportsModel sportsList = documentSnapshot.toObject(UserSportsModel.class);
                    ArrayList<String> sportsArray = sportsList.getSports();
                    callback.OnCallback(sportsArray);
                }
            }
        });



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

interface MyCallbackNewsFeed{
    void OnCallback(ArrayList<String> list);
}