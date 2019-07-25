package com.vaibhav.out_fit;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
