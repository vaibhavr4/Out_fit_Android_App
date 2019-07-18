package com.vaibhav.out_fit;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OutdoorInviteFriendsAdapter extends BaseAdapter {

    public OutdoorInviteFriendsAdapter(Context context, ArrayList<OutdoorInviteFriendsAdapterItem> friendsItems) {
        this.context = context;
        this.friendsItems = friendsItems;
    }

    Context context;
    ArrayList<OutdoorInviteFriendsAdapterItem> friendsItems;

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
            view = View.inflate(context, R.layout.outdoor_invite_friends_adapter_item, null);
        }

        TextView textView = view.findViewById(R.id.FriendsNameAdapterItem);
        textView.setText(friendsItems.get(i).getFriendsName());

        return view;
    }

}
