package com.vaibhav.out_fit;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SportsGridItemView extends FrameLayout {
    private TextView textView;

    public SportsGridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.sports_item_grid, this);
        textView = (TextView) getRootView().findViewById(R.id.text);
    }

    public void display(String text, boolean isSelected) {
        textView.setText(text);
        display(isSelected);
    }

    public void display(boolean isSelected) {
        textView.setBackgroundResource(isSelected ? R.drawable.red_rect : R.drawable.black_rect);
    }
}
