package utils;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.vaibhav.out_fit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendsRequestListAdapterItem extends BaseAdapter {

    //Receive the context from main activity
    Context context;
    private DatabaseReference dbChildReference;
    private FirebaseFirestore db;
    private ArrayList<DataSnapshot> dataSnapshotResultsFromDB;
    ImageView acceptFriend;
    ImageView rejectFriend;
    Map<String, ArrayList<String>> friendList1 = new HashMap<>();
    Map<String, ArrayList<String>> friendList2 = new HashMap<>();
    ArrayList<String> keysDataSnapshot = new ArrayList();


    public FriendsRequestListAdapterItem(Context context, DatabaseReference dbChildReference) {
        this.context = context;
        this.dbChildReference = dbChildReference;
        this.dbChildReference.addChildEventListener(dbChildListener);

        dataSnapshotResultsFromDB = new ArrayList();
        db = FirebaseFirestore.getInstance();
    }


    ChildEventListener dbChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            dataSnapshotResultsFromDB.add(dataSnapshot);
            keysDataSnapshot.add(dataSnapshot.getKey());
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            int index = keysDataSnapshot.indexOf(dataSnapshot.getKey());
           dataSnapshotResultsFromDB.remove(index);
           keysDataSnapshot.remove(index);
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    @Override
    public int getCount() {
        return dataSnapshotResultsFromDB.size();
    }

    @Override
    public FriendsInviteBlockModel getItem(int i) {
        DataSnapshot dataSnapshotItemFromList = dataSnapshotResultsFromDB.get(i);
        return dataSnapshotItemFromList.getValue(FriendsInviteBlockModel.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        final FriendsInviteBlockModel friendsInviteBlockModel = getItem(i);
        if(view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.friend_request_adapter_item, null);
            viewHolder.friendName = view.findViewById(R.id.friendName);
            viewHolder.acceptFriend = view.findViewById(R.id.acceptFriend);
            viewHolder.rejectFriend = view.findViewById(R.id.rejectFriend);
            viewHolder.friendSport = view.findViewById(R.id.friendSport);
            view.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.friendName.setText(friendsInviteBlockModel.getSenderName());
        viewHolder.friendSport.setText(friendsInviteBlockModel.getSport());

//-------------------accept friend--------------------------------------------------------------
        viewHolder.acceptFriend.setClickable(true);
        viewHolder.acceptFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for friend1
                ArrayList<String> tempList = new ArrayList();
                tempList.add(friendsInviteBlockModel.senderId);
                friendList1.put(friendsInviteBlockModel.sport,tempList);

                //for friend2
                ArrayList<String> tempList2 = new ArrayList();
                tempList2.add(friendsInviteBlockModel.receiverId);
                friendList2.put(friendsInviteBlockModel.sport,tempList2);

                final DocumentReference documentReference = db.collection("friend_list")
                        .document(friendsInviteBlockModel.receiverId);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists())
                            {

                                FriendListModel friendListModel = documentSnapshot.toObject(FriendListModel.class);
                                //Log.d("FirendMap",friendListModel.getFriendList().toString());
                                Log.d("FirendMap",documentSnapshot.getData().toString());
                                Map<String,ArrayList<String>> tempMap = friendListModel.getFriendList();

                                if(tempMap.containsKey(friendsInviteBlockModel.sport))
                                {
                                    ArrayList<String> friends = tempMap.get(friendsInviteBlockModel.sport);
                                    if(friends == null) {
                                        friends = new ArrayList<String>();
                                        friends.add(friendsInviteBlockModel.senderId);
                                        tempMap.put(friendsInviteBlockModel.sport, friends);
                                    } else {
                                        // add if item is not already in list
                                        if(!friends.contains(friendsInviteBlockModel.senderId))
                                            friends.add(friendsInviteBlockModel.senderId);
                                    }

                                }
                                else
                                {
                                    ArrayList<String> friends = new ArrayList();
                                    friends.add(friendsInviteBlockModel.senderId);
                                    tempMap.put(friendsInviteBlockModel.sport,friends);
                                    documentReference.set(friendListModel, SetOptions.merge());
                                }
                                friendListModel.setFriendList(tempMap);
                                documentReference.set(friendListModel, SetOptions.merge());

                            }
                            else
                            {
                                FriendListModel friendListModel1 = new FriendListModel();
                                friendListModel1.setFriendList(friendList1);
                                documentReference.set(friendListModel1, SetOptions.merge());
                            }
                        }
                    }
                });


// ----------------------------for friend 2 do the same ------------------------------------

        final DocumentReference documentReference2 = db.collection("friend_list")
                .document(friendsInviteBlockModel.senderId);
                documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists())
                    {

                        FriendListModel friendListModel = documentSnapshot.toObject(FriendListModel.class);
                        //Log.d("FirendMap",friendListModel.getFriendList().toString());
                        Log.d("FirendMap",documentSnapshot.getData().toString());
                        Map<String,ArrayList<String>> tempMap = friendListModel.getFriendList();

                        if(tempMap.containsKey(friendsInviteBlockModel.sport))
                        {
                            ArrayList<String> friends = tempMap.get(friendsInviteBlockModel.sport);
                            if(friends == null) {
                                friends = new ArrayList<String>();
                                friends.add(friendsInviteBlockModel.receiverId);
                                tempMap.put(friendsInviteBlockModel.sport, friends);
                            } else {
                                // add if item is not already in list
                                if(!friends.contains(friendsInviteBlockModel.receiverId))
                                    friends.add(friendsInviteBlockModel.receiverId);
                            }

                        }
                        else
                        {
                            ArrayList<String> friends = new ArrayList();
                            friends.add(friendsInviteBlockModel.receiverId);
                            tempMap.put(friendsInviteBlockModel.sport,friends);
                            documentReference2.set(friendListModel, SetOptions.merge());
                        }
                        friendListModel.setFriendList(tempMap);
                        documentReference2.set(friendListModel, SetOptions.merge());

                    }
                    else
                    {
                        FriendListModel friendListModel2 = new FriendListModel();
                        friendListModel2.setFriendList(friendList2);
                        documentReference2.set(friendListModel2, SetOptions.merge());
                    }
                }
            }
        });

        Query realTimeFriendQuery = dbChildReference.orderByChild("senderId").equalTo(friendsInviteBlockModel.senderId);
        realTimeFriendQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
                    if(friendSnapshot.child("sport").getValue(String.class)==friendsInviteBlockModel.sport)
                        friendSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RealtimeDB", "onCancelled", databaseError.toException());
            }
        });
    }

});

//-------------------reject friend------------------------------------------------------------

        viewHolder.rejectFriend.setClickable(true);
        viewHolder.rejectFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query realTimeFriendQuery = dbChildReference.orderByChild("senderId").equalTo(friendsInviteBlockModel.senderId);
                realTimeFriendQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot friendSnapshot: dataSnapshot.getChildren()) {
                            if(friendSnapshot.child("sport").getValue(String.class)==friendsInviteBlockModel.sport)
                                friendSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("RealtimeDB", "onCancelled", databaseError.toException());
                    }
                });
            }
        });

 //-----------------------------------------------------------------------------------------------
        return view;
    }

    private static class ViewHolder{
        TextView friendName;
        TextView friendSport;
        ImageView acceptFriend;
        ImageView rejectFriend;
    }



}
