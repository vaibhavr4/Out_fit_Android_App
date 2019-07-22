package com.vaibhav.out_fit;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SportsGridItemView extends FrameLayout {
    private TextView textView;
    private ImageView imageView;
    RelativeLayout relativeLayout;
    public SportsGridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.sports_item_grid, this);
        textView = (TextView) getRootView().findViewById(R.id.text);
        imageView = (ImageView) getRootView().findViewById(R.id.grid_image);
        relativeLayout = (RelativeLayout) getRootView().findViewById(R.id.sports_Grid);
    }

    public void display(String text, boolean isSelected) {
        textView.setText(text);
        int imageResID = getResources().getIdentifier(text.toLowerCase(),"drawable",getContext().getPackageName());
        imageView.setImageResource(imageResID);
        imageView.getLayoutParams().width = 300;
        imageView.getLayoutParams().height = 300;


        display(isSelected);
    }

    public void display(boolean isSelected) {
        relativeLayout.setBackgroundResource(isSelected ? R.drawable.red_rect : R.drawable.black_rect);
    }
}