package com.doorboard3.doorboardmobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = MainActivity.class.getSimpleName();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.profile_icon:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                            case R.id.message_icon:
                                selectedFragment = MessageFragment.newInstance();
                                break;
                            case R.id.schedule_icon:
                                selectedFragment = ScheduleFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ProfileFragment.newInstance());
        transaction.commit();
//
//        // Used to select an item programmatically
//        bottomNavigationView.getMenu().getItem(0).setChecked(false);
//        bottomNavigationView.getMenu().getItem(1).setChecked(true);

//        mBottomBar = BottomBar.bind(this, R.layout.activity_main,
//                savedInstanceState);
//
//        // Set up tabs
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        final MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Log.i(TAG, "Current tab: " + tab.getPosition());
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

}
