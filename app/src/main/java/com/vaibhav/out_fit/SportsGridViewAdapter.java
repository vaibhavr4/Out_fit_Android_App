package com.vaibhav.out_fit;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
public class SportsGridViewAdapter extends BaseAdapter{
  private Activity activity;
  private String[] strings;
  public ArrayList selectedPositions;

  public SportsGridViewAdapter(String[] strings, Activity activity,ArrayList<Integer> selectedPositions) {
    this.strings = strings;
    this.activity = activity;
    this.selectedPositions = selectedPositions;
  }

  @Override
  public int getCount() {
    return strings.length;
  }

  @Override
  public Object getItem(int position) {
    return strings[position];
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    SportsGridItemView customView = (convertView == null) ? new SportsGridItemView(activity) : (SportsGridItemView) convertView;
    customView.display(strings[position], selectedPositions.contains(position));

    return customView;
  }
}