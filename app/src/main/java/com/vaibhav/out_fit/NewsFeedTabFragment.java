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
                    newsItems.add("The Ashes (Aug 1 - Sept 16) ");
                    newsItems.add("India Tour of West Indies (Aug 3 - Sept 8)");
                    break;
                case "Soccer":
                    newsItems.add("Premier League - Liverpool V Norwich  (Aug 9)");
                    newsItems.add("Premier League - West Ham V Man City  (Aug 10)");
                    newsItems.add("Premier League - Newcastle V Arsenal  (Aug 11)");
                    break;
                case "Baseball":
                    newsItems.add("MLB - Yankees V Blue Jays (Aug 8)");
                    newsItems.add("MLB - Angels V Red Sox (Aug 9)");
                    break;
                case "Running":
                    newsItems.add("Aspen Backcountry Marathon (Aug 10)");
                    newsItems.add("Steamboat Stinger Trail Marathon (Aug 11)");
                    break;
                case "Cycling":
                    newsItems.add("Vuelta a Espana (Aug 24 - Sept 15)");
                    newsItems.add("Lexus Blackburn Bay Crits (Jan 1 - Jan 3)");
                    break;
                case "Jogging":
                    newsItems.add("Storm Area 5.1K (Sept 20)");
                    newsItems.add("Blacklight Run - Brockton (Aug 10)");
                    break;
                case "Tennis":
                    newsItems.add("US Open (Aug 26 - Sept 9)");
                    newsItems.add("Rakuten Japan Open Tennis Championship (Sept 30 - Oct 7)");
                    break;
                case "Badminton":
                    newsItems.add("IDBI Federal Life Insurance Hyderabad Open 2019 (Aug 6 - Aug 11)");
                    newsItems.add("Bulgarian Open Championship 2019 (Aug 12 - Aug 15)");
                    break;
                case "Basketball":
                    newsItems.add("Women's Pan American Games (Aug 6 - Aug 10)");
                    newsItems.add("FIBA World Cup for Men (Aug 31 - Sept 15)");
                    break;
                case "Volleyball":
                    newsItems.add("FIVB Volleyball Boys' U19 World Championship (Aug 21 - Aug 30)");
                    newsItems.add("FIVB Volleyball Girls' U18 World Championship (Aug 5 - Sept 14)");
                    break;
                case "Rugby":
                    newsItems.add("2019 World Rugby Pacific Nations Cup (27 July - 10 Aug)");
                    newsItems.add("Rugby Africa Women’s Cup 2019 (aug 9 - Aug 17)");
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