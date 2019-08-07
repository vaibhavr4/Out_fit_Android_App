package com.vaibhav.out_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import utils.OutdoorInviteFriendsModel;

public class OutdoorEventActivity extends AppCompatActivity {

    ArrayList<OutdoorInviteFriendsModel> friendsInvited = new ArrayList();
    String sender;
    ArrayList<String> receiverList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_event);

        TextView sport = findViewById(R.id.displaySport);
        TextView location = findViewById(R.id.displayLocation);
        TextView date = findViewById(R.id.displayDate);
        TextView time = findViewById(R.id.displayTime);
        TextView sportDesc = findViewById(R.id.SportDesc);
        TextView senderName = findViewById(R.id.eventCreator);

        Bundle args = getIntent().getBundleExtra("BUNDLE");

        final String event_desc = args.getString("DESC");
        sportDesc.setText(event_desc);
        final String sport_name = args.getString("SPORT");
        sport.setText(sport_name);
        String location_name = args.getString("PLAYGROUND");
        location.setText(location_name);
        String date_name = args.getString("DATE");
        date.setText(date_name);
        String time_name = args.getString("TIME");
        time.setText(time_name);



        friendsInvited = (ArrayList<OutdoorInviteFriendsModel>)args.getSerializable("friendList");
        sender = args.getString("CREATOR");
        senderName.setText(sender);
        if(friendsInvited!=null || friendsInvited.size()!=0) {
            for (int i = 0; i < friendsInvited.size(); i++) {
                receiverList.add(friendsInvited.get(i).getReceiverName());
            }

        }

        ListView listView = findViewById(R.id.eventFriendList);
        ArrayAdapter<String> friendListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, receiverList);
        listView.setAdapter(friendListAdapter);

        ImageButton closeButton = (ImageButton) findViewById(R.id.CloseButtonEvent);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorEventActivity.this,MaterialTabActivity.class);
                startActivity(intent);
            }
        });

        Log.d("FRIENDSCOUNT",String.valueOf(friendsInvited.size()));

    }
}
