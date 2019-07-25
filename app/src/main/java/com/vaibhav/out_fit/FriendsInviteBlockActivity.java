package com.vaibhav.out_fit;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendsInviteBlockActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<FriendsInviteBlockAdapterItem> friends = new ArrayList<FriendsInviteBlockAdapterItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_invite_block);
        TextView sport = findViewById(R.id.sportsNameFriends);
        final String sport_name = getIntent().getStringExtra("SPORT");
        sport.setText(sport_name);

        listView = findViewById(R.id.friendsListInviteBlock);
        populateList();
        FriendsInviteBlockAdapter myAdapter = new FriendsInviteBlockAdapter(this,friends);
        listView.setAdapter(myAdapter);

    }

    private void populateList(){
        friends.add(new FriendsInviteBlockAdapterItem("Praggy (0.1miles)"));
        friends.add(new FriendsInviteBlockAdapterItem("Divya (0.6miles)"));
        friends.add(new FriendsInviteBlockAdapterItem("Menita (1.1miles)"));
        friends.add(new FriendsInviteBlockAdapterItem("Anisha (2.1miles)"));
    }

    public void InviteFriends(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Invited";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void Block(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Blocked";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();
    }
}
