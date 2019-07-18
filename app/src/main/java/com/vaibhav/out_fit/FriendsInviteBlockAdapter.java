package com.vaibhav.out_fit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsInviteBlockAdapter extends BaseAdapter {

    public FriendsInviteBlockAdapter(Context context, ArrayList<FriendsInviteBlockAdapterItem> friendsItems) {
        this.context = context;
        this.friendsItems = friendsItems;
    }

    Context context;
    ArrayList<FriendsInviteBlockAdapterItem> friendsItems;

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
            view = View.inflate(context, R.layout.friends_invite_block_adapter_item, null);
        }

        TextView textView = view.findViewById(R.id.nameAdapterItem);
        textView.setText(friendsItems.get(i).getFriendsName());

        return view;
    }

}
