package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendsTabFragment extends Fragment {

    ListView listView;
    ArrayList<FriendsListAdapterItem> sports = new ArrayList<FriendsListAdapterItem>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            listView = view.findViewById(R.id.friendsList);
            populateList();
            FriendsListAdapter myAdapter = new FriendsListAdapter(getActivity(),sports);
            listView.setAdapter(myAdapter);

        }

        private void populateList(){
            sports.add(new FriendsListAdapterItem("CRICKET"));
            sports.add(new FriendsListAdapterItem("FOOTBALL"));
            sports.add(new FriendsListAdapterItem("RUGBY"));
            sports.add(new FriendsListAdapterItem("RUNNING"));
        }
}
