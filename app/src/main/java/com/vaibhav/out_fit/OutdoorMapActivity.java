package com.vaibhav.out_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OutdoorMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_map);

        TextView textView = findViewById(R.id.sportsName);

        final String sport = getIntent().getStringExtra("SPORT");
        textView.setText(sport +" Playgrounds");
        Button buttonGo = findViewById(R.id.outdoorSportsButton);

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutdoorMapActivity.this,OutdoorDateTimeActivity.class);
                intent.putExtra("PLAYGROUND", "Fenway Park");
                intent.putExtra("SPORT", sport);
                startActivity(intent);
            }
        });

    }
}
