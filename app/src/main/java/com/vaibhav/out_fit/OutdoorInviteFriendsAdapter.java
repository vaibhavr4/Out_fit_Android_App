package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

import utils.OutdoorInviteFriendsModel;

public class OutdoorInviteFriendsAdapter extends BaseAdapter {

    Context context;
    ArrayList<OutdoorInviteFriendsModel> friendsItems = new ArrayList<>();
    DatabaseReference databaseReference;
    FirebaseFirestore db;
    ArrayList<OutdoorInviteFriendsModel> eventInvitedFriends = new ArrayList<>();


    public OutdoorInviteFriendsAdapter(Context context, ArrayList<OutdoorInviteFriendsModel> friendsItems) {
        this.context = context;
        this.friendsItems = friendsItems;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public int getCount() {
        return friendsItems.size();
    }

    @Override
    public OutdoorInviteFriendsModel getItem(int i) {
        // TODO Auto-generated method stub
        return friendsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = View.inflate(context, R.layout.outdoor_invite_friends_adapter_item, null);
        }

        TextView textView = view.findViewById(R.id.FriendsNameAdapterItem);
        textView.setText(friendsItems.get(i).getReceiverName());

        final View finalView = view;
        final Button invite = view.findViewById(R.id.eventInvite);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = finalView.getContext();
                eventInvitedFriends.add(getItem(i));

                invite.setEnabled(false);
                invite.setBackgroundColor(finalView.getResources().getColor(R.color.grey));
            }
        });

        Intent intent = new Intent("InvitedFriends");
//        intent.putExtra("friendList",eventInvitedFriends);
        Bundle args = new Bundle();
        args.putSerializable("friendList",(Serializable)eventInvitedFriends);
        intent.putExtra("BUNDLE",args);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        return view;
    }

}
