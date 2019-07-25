package com.vaibhav.out_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class OutdoorEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_event);

        TextView sport = findViewById(R.id.displaySport);
        TextView location = findViewById(R.id.displayLocation);
        TextView date = findViewById(R.id.displayDate);
        TextView time = findViewById(R.id.displayTime);

        final String sport_name = getIntent().getStringExtra("SPORT");
        sport.setText(sport_name);
        String location_name = getIntent().getStringExtra("PLAYGROUND");
        location.setText(location_name);
        String date_name = getIntent().getStringExtra("DATE");
        date.setText(date_name);
        String time_name = getIntent().getStringExtra("TIME");
        time.setText(time_name);

        ImageButton closeButton = (ImageButton) findViewById(R.id.CloseButtonEvent);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorEventActivity.this,MaterialTabActivity.class);
                startActivity(intent);
            }
        });

    }
}
