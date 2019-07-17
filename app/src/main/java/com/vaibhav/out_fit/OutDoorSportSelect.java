package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OutDoorSportSelect extends AppCompatActivity {

    private GridView gridView;
    private View btnGo;
    String[] itemsName;
    private String selectedString;
    String selectedItem;
    TextView GridViewItems,BackSelectedItem;
    int backposition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_door_sport_select);
        ArrayList<String> selectedSports = getIntent().getStringArrayListExtra("SELECTED_LETTER");
        itemsName = selectedSports.toArray(new String[0]);
        gridView = (GridView) findViewById(R.id.outdoorSportsGrid);
        btnGo = findViewById(R.id.outdoorSportsButton);
        gridView.setAdapter(new TextAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                selectedItem = parent.getItemAtPosition(position).toString();
                GridViewItems = (TextView) view;
                GridViewItems.setBackgroundColor(Color.parseColor("#F55303"));
                GridViewItems.setTextColor(Color.parseColor("#FFFFFF"));
                BackSelectedItem = (TextView) gridView.getChildAt(backposition);
                if (backposition != -1)
                {
                    BackSelectedItem.setSelected(false);
                    BackSelectedItem.setBackgroundColor(Color.parseColor("#000000"));
                    BackSelectedItem.setTextColor(Color.parseColor("#FFFFFF"));
                }
                backposition = position;
                selectedString = itemsName[backposition];
            }
        });

        //set listener for Button event
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutDoorSportSelect.this, OutdoorMapFragment.class);
                intent.putExtra("SELECTED_LETTER", selectedString);
                startActivity(intent);
            }
        });

    }

    private class TextAdapter extends BaseAdapter
    {
        Context context;

        public TextAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsName.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemsName[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView text = new TextView(this.context);
            text.setText(itemsName[position]);
            text.setGravity(Gravity.CENTER);
            text.setBackgroundColor(Color.parseColor("#000000"));
            text.setTextColor(Color.parseColor("#FFFFFF"));
            text.setLayoutParams(new GridView.LayoutParams(300, 300));
            return text;
        }
    }
}
