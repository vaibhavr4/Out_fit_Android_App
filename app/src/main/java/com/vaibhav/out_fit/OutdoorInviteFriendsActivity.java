package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OutdoorInviteFriendsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<OutdoorInviteFriendsAdapterItem> friends = new ArrayList<OutdoorInviteFriendsAdapterItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_invite_friends);
        TextView sport = findViewById(R.id.sportsNameOutdoor);
        final String sport_name = getIntent().getStringExtra("SPORT");
        sport.setText(sport_name);

        listView = findViewById(R.id.displayFriendsList);
        populateList();
        OutdoorInviteFriendsAdapter myAdapter = new OutdoorInviteFriendsAdapter(this,friends);
        listView.setAdapter(myAdapter);

        ImageButton closeButton = (ImageButton) findViewById(R.id.CloseButtonFriends);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorInviteFriendsActivity.this,MaterialTabActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateList(){
        friends.add(new OutdoorInviteFriendsAdapterItem("Preethi"));
        friends.add(new OutdoorInviteFriendsAdapterItem("Vaibhav"));
        friends.add(new OutdoorInviteFriendsAdapterItem("Aashish"));
        friends.add(new OutdoorInviteFriendsAdapterItem("Goku"));
    }

    public void Invite(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Invited for the game";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();
    }
}
