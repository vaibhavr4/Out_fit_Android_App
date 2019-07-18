package com.vaibhav.out_fit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import utils.NewsFeedAdapterItem;

public class NewsFeedTabFragment extends Fragment {

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
        ArrayAdapter eventsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eventsItems)
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
        eventsListView.setAdapter(eventsAdapter);

        //Set click listeners
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //trigger second activity - char info
//                Intent intent = new Intent(getActivity(),);
//
//                //pass char name to second activity
//                intent.putExtra("CharacterName",favCharacters.get(i).getCharName());
//                startActivity(intent);

            }
        });


    }

    private void populateList() {
        newsItems.add("After a fantastic World Cup, Jason Roy has now been picked for the one-off Test against Ireland");
        newsItems.add("Novak Djokovic says his epic Wimbledon final victory over Roger Federer was " +
                "his most \"mentally demanding\" match - and he even had to tell himself the partisan crowd was cheering for him.");
        newsItems.add("Neil Lennon says Arsenal are yet to up their bid for Celtic defender Kieran Tierney.");
        newsItems.add("Somerset fast-bowler Lewis Gregory has also been called up for the first time");
        newsItems.add("After a fantastic World Cup, Jason Roy has now been picked for the one-off Test against Ireland");
        newsItems.add("Neil Lennon says Arsenal are yet to up their bid for Celtic defender Kieran Tierney.");

        eventsItems.add("Events: Play Cricket- Scheduled for 5PM today");
        eventsItems.add("Events: Play Basketball- Scheduled for 6PM today");
        eventsItems.add("Events: Play Cricket- Scheduled for 6PM Friday");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newsfeed_layout, container, false);
    }
}
