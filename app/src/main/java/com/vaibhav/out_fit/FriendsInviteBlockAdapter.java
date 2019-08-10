package com.vaibhav.out_fit;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import utils.FriendsInviteBlockModel;

import java.util.ArrayList;

public class FriendsInviteBlockAdapter extends BaseAdapter {

    Context context;
    ArrayList<FriendsInviteBlockModel> friendsItems;
    DatabaseReference databaseReference;
    FirebaseFirestore db;

    public FriendsInviteBlockAdapter(Context context, ArrayList<FriendsInviteBlockModel> friendsItems) {
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
    public Object getItem(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int i) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = View.inflate(context, R.layout.friends_invite_block_adapter_item, null);
        }

        TextView textView = view.findViewById(R.id.nameAdapterItem);
        textView.setText(friendsItems.get(i).getReceiverName());
        final View finalView = view;
        final Button invite = view.findViewById(R.id.invitationButton);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = finalView.getContext();
                CharSequence text = "Invited "+friendsItems.get(i).getReceiverName();
                int duration = Toast.LENGTH_SHORT;

//-------------------------------Update realtime database with friend id as child reference---------------//
                db.collection("users").document(friendsItems.get(i).getSenderId())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                final String senderName = documentSnapshot.getString("name");
                                friendsItems.get(i).setSenderName(senderName);
                                final DatabaseReference databaseChildRef = databaseReference.child("FriendRequests");
                                final DatabaseReference databaseChildOfChildRef = databaseChildRef.child(friendsItems.get(i).getReceiverId());
                                FriendsInviteBlockModel friendsInviteBlockModel = friendsItems.get(i);
                                databaseChildOfChildRef.push().setValue(friendsInviteBlockModel);

                            }
                        });


//----------------------------------------------------------------------------------------------//

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                toast.show();
                invite.setEnabled(false);
                invite.setBackgroundColor(finalView.getResources().getColor(R.color.grey));

            }
        });


        return view;
    }

}
