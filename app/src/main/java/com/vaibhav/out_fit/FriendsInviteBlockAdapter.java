package com.vaibhav.out_fit;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = View.inflate(context, R.layout.friends_invite_block_adapter_item, null);
        }

        TextView textView = view.findViewById(R.id.nameAdapterItem);
        textView.setText(friendsItems.get(i).getFriendsName());
        final View finalView = view;
        final Button invite = view.findViewById(R.id.invitationButton);
        final Button block = view.findViewById(R.id.blockButton);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = finalView.getContext();
                CharSequence text = "Invited "+friendsItems.get(i).friendsName;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                toast.show();
                invite.setEnabled(false);
                invite.setBackgroundColor(finalView.getResources().getColor(R.color.grey));
                block.setEnabled(false);
            }
        });
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = finalView.getContext();
                CharSequence text = "Blocked "+friendsItems.get(i).friendsName;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
                toast.show();
                block.setEnabled(false);
                block.setBackgroundColor(finalView.getResources().getColor(R.color.grey));
                invite.setEnabled(false);
            }
        });

        return view;
    }

}
