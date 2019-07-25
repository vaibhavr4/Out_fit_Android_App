package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

public class OutdoorDateTimeActivity extends AppCompatActivity {

    TimePickerDialog picker;
    EditText eText;
    ImageButton closeButton;

    DatePickerDialog picker2;
    EditText eText2;
    Button btnGet2;

    ListView listView;
    ArrayList<OutdoorInviteFriendsAdapterItem> friends = new ArrayList<OutdoorInviteFriendsAdapterItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_date_time);
        final String location = getIntent().getStringExtra("PLAYGROUND");
        final String sport = getIntent().getStringExtra("SPORT");
        eText=(EditText) findViewById(R.id.TimeText);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(OutdoorDateTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        eText2=(EditText) findViewById(R.id.DateText);
        eText2.setInputType(InputType.TYPE_NULL);
        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker2 = new DatePickerDialog(OutdoorDateTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker2.show();
            }
        });

        listView = findViewById(R.id.displayFriendsList);
        populateList();
        OutdoorInviteFriendsAdapter myAdapter = new OutdoorInviteFriendsAdapter(this,friends);
        listView.setAdapter(myAdapter);

        btnGet2=(Button)findViewById(R.id.dateTimeButton);
        btnGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutdoorDateTimeActivity.this,OutdoorEventActivity.class);
                intent.putExtra("DATE", eText2.getText().toString());
                intent.putExtra("TIME", eText.getText().toString());
                intent.putExtra("PLAYGROUND", location);
                intent.putExtra("SPORT", sport);
                startActivity(intent);
            }
        });
    }

    private void populateList(){
        friends.add(new OutdoorInviteFriendsAdapterItem("Preethi"));
        friends.add(new OutdoorInviteFriendsAdapterItem("Vaibhav"));
        friends.add(new OutdoorInviteFriendsAdapterItem("Aashish"));
        friends.add(new OutdoorInviteFriendsAdapterItem("Goku"));

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