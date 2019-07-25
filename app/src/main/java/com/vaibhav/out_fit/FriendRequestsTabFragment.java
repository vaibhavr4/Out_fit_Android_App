package com.vaibhav.out_fit;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import utils.FriendsListAdapterItem;


public class FriendRequestsTabFragment extends Fragment {

    ListView friendRequestsListView;
    ListView eventRequestsListView;
    ArrayList<String> friendRequestItems = new ArrayList<>();
    ArrayList<String> eventRequestItems = new ArrayList<>();
    //ArrayList<NewsFeedAdapterItem> newsFeed = new ArrayList<>();
    //ArrayList<NewsFeedAdapterItem> eventsFeed = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        friendRequestsListView = view.findViewById(R.id.friendRequestList);
        eventRequestsListView = view.findViewById(R.id.eventRequestList);

        populateList();

        FriendsListAdapterItem friendRequestAdapter = new FriendsListAdapterItem(getActivity(),friendRequestItems);

        FriendsListAdapterItem eventRequestAdapter = new FriendsListAdapterItem(getActivity(),eventRequestItems);

        friendRequestsListView.setAdapter(friendRequestAdapter);
        eventRequestsListView.setAdapter(eventRequestAdapter);

        //Set click listeners
        friendRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        friendRequestItems.add("Goku");
        friendRequestItems.add("Vegeta");
        friendRequestItems.add("Picollo");
        friendRequestItems.add("Frieza");
        friendRequestItems.add("Beerus");
        friendRequestItems.add("Gohan");

        eventRequestItems.add("Play Cricket- Scheduled for 5PM");
        eventRequestItems.add("Play Basketball- Scheduled for 6PM");
        eventRequestItems.add("Play Football- Scheduled for 8PM");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_requests_layout, container, false);
    }

}
