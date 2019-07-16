package com.vaibhav.out_fit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MaterialTabActivity extends AppCompatActivity {

    private MaterialTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_tab);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new MaterialTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), getResources().getString(R.string.tab1));
        adapter.addFragment(new Tab2Fragment(), getResources().getString(R.string.tab2));
        adapter.addFragment(new Tab3Fragment(), getResources().getString(R.string.tab3));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setIcons();
    }
    protected void setIcons()
    {
        int[] tabIcons = {
                R.drawable.newsfeed,
                R.drawable.outdoor,
                R.drawable.friends
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


}
