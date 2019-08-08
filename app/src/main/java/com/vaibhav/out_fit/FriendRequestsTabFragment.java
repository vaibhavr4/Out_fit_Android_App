package com.vaibhav.out_fit;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utils.EventRequestListAdapterItem;
import utils.FriendsInviteBlockModel;
import utils.FriendsRequestListAdapterItem;


public class FriendRequestsTabFragment extends Fragment {

    ListView friendRequestsListView;
    ListView eventRequestsListView;
    ArrayList<String> eventRequestItems = new ArrayList<>();
    DatabaseReference databaseReference;
    FirebaseFirestore db;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String currentUserId = currentFirebaseUser.getUid();



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference databaseChildRef = databaseReference.child("FriendRequests").child(currentUserId);
        final DatabaseReference databaseEventChildRef = databaseReference.child("EventRequests").child(currentUserId);

        friendRequestsListView = view.findViewById(R.id.friendRequestList);

        eventRequestsListView = view.findViewById(R.id.eventRequestList);

        populateList();

        FriendsRequestListAdapterItem friendRequestAdapter = new FriendsRequestListAdapterItem(getActivity(),databaseChildRef);

        EventRequestListAdapterItem eventRequestAdapter = new EventRequestListAdapterItem(getActivity(),databaseEventChildRef);

        friendRequestsListView.setAdapter(friendRequestAdapter);
        eventRequestsListView.setAdapter(eventRequestAdapter);

        //Set click listeners
        friendRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), FriendRequestProfile.class);
                FriendsInviteBlockModel friendsInviteBlockModel = (FriendsInviteBlockModel)adapterView.getItemAtPosition(i);
                intent.putExtra("friend_id", friendsInviteBlockModel.getSenderId());
                startActivity(intent);
            }
        });


    }

    private void populateList() {
//

        eventRequestItems.add("Play Cricket- Scheduled for 5PM");
        eventRequestItems.add("Play Basketball- Scheduled for 6PM");
        eventRequestItems.add("Play Football- Scheduled for 8PM");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_requests_layout, container, false);
    }

}
