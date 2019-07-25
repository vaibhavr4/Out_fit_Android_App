package com.vaibhav.out_fit;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class FriendsTabFragment extends Fragment {

    ListView listView;
    Spinner friendsSportSelection;
    ArrayList<FriendsListAdapterItem> sports = new ArrayList<FriendsListAdapterItem>();
    ArrayList<String> friendsSports = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            friendsSportSelection = view.findViewById(R.id.friendsSportSelection);
            listView = view.findViewById(R.id.friendsList);
            if (friendsSports.isEmpty()) {
                populateList();
            }
            ArrayAdapter<String> friendsSportAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, friendsSports);
            friendsSportAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            friendsSportSelection.setAdapter(friendsSportAdapter);
            friendsSportSelection.setBackgroundColor(getResources().getColor(R.color.white_greyish,null));

            friendsSportSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    sports.clear();
                    sports.add(new FriendsListAdapterItem(friendsSportSelection.getItemAtPosition(i).toString()));
                    FriendsListAdapter myAdapter = new FriendsListAdapter(getActivity(),sports);
                    listView.setAdapter(myAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



        }

        private void populateList(){
            friendsSports.add("CRICKET");
            friendsSports.add("FOOTBALL");
            friendsSports.add("RUGBY");
            friendsSports.add("RUNNING");

//            sports.add(new FriendsListAdapterItem("CRICKET"));
//            sports.add(new FriendsListAdapterItem("FOOTBALL"));
//            sports.add(new FriendsListAdapterItem("RUGBY"));
//            sports.add(new FriendsListAdapterItem("RUNNING"));
        }
}
