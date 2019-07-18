package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsListAdapter extends BaseAdapter {

    public FriendsListAdapter(Context context, ArrayList<FriendsListAdapterItem> friendsItems) {
        this.context = context;
        this.friendsItems = friendsItems;
    }

    Context context;
    ArrayList<FriendsListAdapterItem> friendsItems;

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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = View.inflate(context, R.layout.friends_list_adapter_item, null);
        }

        TextView textView = view.findViewById(R.id.PersonNameAdapterItem);
        textView.setText(friendsItems.get(i).getFriendsName());

        Button button = view.findViewById(R.id.inviteButton0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,FriendsInviteBlockActivity.class);
                intent.putExtra("SPORT", "CRICKET");
                context.startActivity(intent);
            }
        });

        return view;
    }

}
