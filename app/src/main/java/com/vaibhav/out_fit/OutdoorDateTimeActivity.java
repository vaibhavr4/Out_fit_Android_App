package com.vaibhav.out_fit;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class OutdoorDateTimeActivity extends AppCompatActivity {

    TimePickerDialog picker;
    EditText eText;
    ImageButton closeButton;

    DatePickerDialog picker2;
    EditText eText2;
    Button btnGet2;

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

        closeButton = (ImageButton) findViewById(R.id.CloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutdoorDateTimeActivity.this,MaterialTabActivity.class);
                startActivity(intent);
            }
        });

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
}