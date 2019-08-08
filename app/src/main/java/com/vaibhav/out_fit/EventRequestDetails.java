package com.vaibhav.out_fit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EventRequestDetails extends AppCompatActivity {

    TextView creator, description, time, date, location, sport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_request_details);

        creator = findViewById(R.id.creator);
        description = findViewById(R.id.description);
        time = findViewById(R.id.eventTime);
        date = findViewById(R.id.date);
        location = findViewById(R.id.EventLocation);
        sport = findViewById(R.id.sport);
        description.setText(getIntent().getStringExtra("Description"));
        creator.setText(getIntent().getStringExtra("Creator"));
        time.setText(getIntent().getStringExtra("Time"));
        date.setText(getIntent().getStringExtra("Date"));
        location.setText(getIntent().getStringExtra("Location"));
        sport.setText(getIntent().getStringExtra("Sport"));
    }
}
