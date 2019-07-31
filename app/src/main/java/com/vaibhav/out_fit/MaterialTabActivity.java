package com.vaibhav.out_fit;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vaibhav.out_fit.MaterialTabAdapter;

public class MaterialTabActivity extends AppCompatActivity {

    private MaterialTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private FirebaseAuth autenticationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nav);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new MaterialTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedTabFragment(), getResources().getString(R.string.tab1));
        adapter.addFragment(new OutdoorTabFragment(), getResources().getString(R.string.tab2));
        adapter.addFragment(new FriendsTabFragment(), getResources().getString(R.string.tab3));
        adapter.addFragment(new FriendRequestsTabFragment(),"Requests");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setIcons();

        autenticationRef = FirebaseAuth.getInstance();


        dl = (DrawerLayout)findViewById(R.id.activity_test_nav);
        t = new ActionBarDrawerToggle(this, dl,R.string.open, R.string.close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Context currentContext=MaterialTabActivity.this;
                        Intent intents= new Intent(MaterialTabActivity.this,UserProfileActivity.class);
                        currentContext.startActivity(intents);break;
                    case R.id.settings:
                        Toast.makeText(MaterialTabActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mysports:
                        Context CurrentObj=MaterialTabActivity.this;
                        Intent Intents= new Intent(MaterialTabActivity.this,SportsInterestGridActivity.class);
                        CurrentObj.startActivity(Intents);
                        break;
                    case R.id.logout:
                        autenticationRef.signOut();
                        Context obj=MaterialTabActivity.this;
                        Intent intent= new Intent(MaterialTabActivity.this,MainActivity.class);
                        obj.startActivity(intent);
                    default:
                        return true;
                }


                return true;

            }
        });


    }

    protected void setIcons()
    {
        int[] tabIcons = {
                R.drawable.newsfeed,
                R.drawable.outdoor,
                R.drawable.friends,
                R.drawable.friend_request
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


}
