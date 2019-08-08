package com.vaibhav.out_fit;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utils.EventRequestListAdapterItem;
import utils.FriendsInviteBlockModel;
import utils.FriendsRequestListAdapterItem;
import utils.OutdoorEventModel;
import utils.OutdoorInviteFriendsModel;


public class FriendRequestsTabFragment extends Fragment {

    ListView friendRequestsListView;
    ListView eventRequestsListView;
    ArrayList<EventRequestListAdapterItem> eventRequestItems = new ArrayList<>();
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
        TextView emptyEvent = view.findViewById(R.id.NoEventRequest);
        emptyEvent.setText("No event requests right now!!");
        emptyEvent.setGravity(RelativeLayout.CENTER_HORIZONTAL);

        eventRequestsListView.setEmptyView(emptyEvent);

        TextView emptyFriends = view.findViewById(R.id.NoFriendRequest);
        emptyFriends.setText("No friend requests right now!!");
        emptyFriends.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        friendRequestsListView.setEmptyView(emptyFriends);


//        populateList();

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

        eventRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OutdoorInviteFriendsModel outdoorObj = (OutdoorInviteFriendsModel)adapterView.getItemAtPosition(i);
                final Intent intent = new Intent(getActivity(), EventRequestDetails.class);
                populateEvents(new EventRequestsCallback() {
                    @Override
                    public void OnCallback(String event, String creator, String time, String date, String location, String sport) {
                        intent.putExtra("Description",event);
                        intent.putExtra("Creator",creator);
                        intent.putExtra("Time",time);
                        intent.putExtra("Date",date);
                        intent.putExtra("Location",location);
                        intent.putExtra("Sport",sport);
                       startActivity(intent);
                    }
                },outdoorObj.getEventId());
            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_requests_layout, container, false);
    }

    private void populateEvents(final EventRequestsCallback callback, String eventId){
        db = FirebaseFirestore.getInstance();
        final DocumentReference eventList = db.collection("events").document(eventId);
        eventList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    OutdoorEventModel outdoorEventModel = documentSnapshot.toObject(OutdoorEventModel.class);
                    if(outdoorEventModel!=null)
                    {
                        callback.OnCallback(outdoorEventModel.getEventDescription(),
                                outdoorEventModel.getCreatorName(), outdoorEventModel.getTime(),
                                outdoorEventModel.getDate(),outdoorEventModel.getLocation(),
                                outdoorEventModel.getSport());
                    }

                }

            }
        });

    }

}

interface EventRequestsCallback{
    void OnCallback(String event, String creator, String time, String date, String location, String sport);
}
