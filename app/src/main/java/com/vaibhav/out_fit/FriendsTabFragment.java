package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.FriendListModel;
import utils.UserModel;
import utils.UserSportsModel;

public class FriendsTabFragment extends Fragment {
    FirebaseFirestore db;

    ListView listView;
    Spinner friendsSportSelection;
    TextView sportNameCard;
    String sportsSelected;

    ArrayList<String> friendsSports = new ArrayList<String>();
    Map<String,ArrayList<String>> sportFriendList = new HashMap();
//    ArrayList<String> friendList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_friends_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sportNameCard = (TextView) view.findViewById(R.id.SportNameCard);

            friendsSportSelection = view.findViewById(R.id.friendsSportSelection);
            listView = view.findViewById(R.id.friendsList);
        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                listView.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        TextView noFriend = (TextView) view.findViewById(R.id.NoSportFriends);
        noFriend.setText("No Friends for this sport at this time!");
        listView.setEmptyView(noFriend);

            if (friendsSports.isEmpty()) {
                populateList(new MyCallback() {
                    @Override
                    public void OnCallback(ArrayList<String> list) {
                        ArrayAdapter<String> friendsSportAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, friendsSports);
                        friendsSportAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        friendsSportSelection.setAdapter(friendsSportAdapter);
                        friendsSportSelection.setBackgroundColor(getResources().getColor(R.color.white_greyish,null));

                        friendsSportSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                sportsSelected = friendsSportSelection.getItemAtPosition(i).toString();
                                sportNameCard.setText(sportsSelected);

                                if(sportFriendList.get(sportsSelected).isEmpty()) {
                                    populateList2(new MyCallback() {
                                        @Override
                                        public void OnCallback(ArrayList<String> list) {
                                            ArrayAdapter<String> friendListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sportFriendList.get(sportsSelected));
                                            listView.setAdapter(friendListAdapter);
                                        }
                                    });
                                }
                                else
                                {
                                    ArrayAdapter<String> friendListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sportFriendList.get(sportsSelected));
                                    listView.setAdapter(friendListAdapter);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                });


            }

            else
            {
                ArrayAdapter<String> friendsSportAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, friendsSports);
                friendsSportAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                friendsSportSelection.setAdapter(friendsSportAdapter);
                friendsSportSelection.setBackgroundColor(getResources().getColor(R.color.white_greyish,null));

                friendsSportSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sportsSelected = friendsSportSelection.getItemAtPosition(i).toString();
                        sportNameCard.setText(sportsSelected);
                            if(sportFriendList.get(sportsSelected).isEmpty()) {
                                populateList2(new MyCallback() {
                                    @Override
                                    public void OnCallback(ArrayList<String> list) {
                                        ArrayAdapter<String> friendListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
                                        listView.setAdapter(friendListAdapter);
                                    }
                                });
                            }
                            else
                            {
                                ArrayAdapter<String> friendListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sportFriendList.get(sportsSelected));
                                listView.setAdapter(friendListAdapter);
                            }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }



        Button button = view.findViewById(R.id.inviteButton0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,FriendsInviteBlockActivity.class);
                intent.putExtra("SPORT", sportsSelected);
                context.startActivity(intent);
            }
        });



        }

        private void populateList(final MyCallback callback){
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            db = FirebaseFirestore.getInstance();
            final String currentUserId = currentFirebaseUser.getUid();
            final DocumentReference sportList = db.collection("user_sports").document(currentUserId);
            sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserSportsModel userSportsModel = documentSnapshot.toObject(UserSportsModel.class);
                    for (String str: userSportsModel.getSports()) {
                        sportFriendList.put(str.trim(),new ArrayList<String>());
                        friendsSports.add(str.trim());
                    }
                    callback.OnCallback(friendsSports);
                }
            });

        }

        private void populateList2(final MyCallback callback){
            Log.d("friend_list","STart:"+sportsSelected.toString());
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        final String currentUserId = currentFirebaseUser.getUid();
        final DocumentReference sportList = db.collection("friend_list").document(currentUserId);
        sportList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                FriendListModel friendListModel = documentSnapshot.toObject(FriendListModel.class);
//                Log.d("friend_list",documentSnapshot.getData().toString());
                ArrayList<String> userIds = new ArrayList();

                if(friendListModel==null)
                {
//                    friendList = new ArrayList<>();
                    callback.OnCallback(sportFriendList.get(sportsSelected));
                }
                else
                {
                    userIds = friendListModel.getFriendList().get(sportsSelected.toString());
                    if(userIds==null)
                        callback.OnCallback(sportFriendList.get(sportsSelected));
                    else if(!userIds.isEmpty()) {
                        for (String id : userIds) {
                            Log.d("friend_list", "One id:" + id.toString());
                            final DocumentReference userDocRef = db.collection("users").document(id);
                            userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    UserModel userModel = documentSnapshot.toObject(UserModel.class);
                                    Log.d("friend_list", "UserModel" + documentSnapshot.getData().toString());
                                    Log.d("friend_list", "UserModel" + userModel.getName().toString());
                                    sportFriendList.get(sportsSelected).add(userModel.getName());
//                                friendList.add(userModel.getName().toString());
                                    callback.OnCallback(sportFriendList.get(sportsSelected));
                                }
                            });
                        }
                        Log.d("friend_list", sportFriendList.get(sportsSelected).toString());
                    }
                    else
                    {
                        callback.OnCallback(sportFriendList.get(sportsSelected));
                    }
                }


            }
        });

    }


}

interface MyCallback{
    void OnCallback(ArrayList<String> list);
}