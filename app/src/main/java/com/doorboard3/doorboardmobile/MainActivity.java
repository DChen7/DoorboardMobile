package com.doorboard3.doorboardmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import static android.content.ContentValues.TAG;
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DoorboardDbHelper dbHelper = new DoorboardDbHelper(this);
//        dbHelper.clearDB();

        bundle = this.getIntent().getExtras();

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.profile_icon:
                                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                selectedFragment = ProfileFragment.newInstance();
                                getSupportActionBar().setTitle("Profile");
                                break;
                            case R.id.message_icon:
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                                selectedFragment = MessageFragment.newInstance();
                                getSupportActionBar().setTitle("Post a Message");
                                break;
                            case R.id.schedule_icon:
                                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                                selectedFragment = ScheduleFragment.newInstance();
                                getSupportActionBar().setTitle("Schedule");
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        // Go to message fragment if it was from the update message activity
        if (bundle != null && bundle.getBoolean("UPDATE")) {
            Log.i(TAG, "MESSAGE???");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, MessageFragment.newInstance());
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            bottomNavigationView.getMenu().getItem(2).setChecked(false);
            getSupportActionBar().setTitle("Post a Message");
            transaction.commit();
        } else if (bundle != null && bundle.getBoolean("scheduleUpdate")) {
            Log.i(TAG, "SCHEDULE???");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, ScheduleFragment.newInstance());
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
            bottomNavigationView.getMenu().getItem(1).setChecked(false);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            getSupportActionBar().setTitle("Schedule");
            transaction.commit();
        } else {
            // Manually display the first fragment
            Log.i(TAG, "PROFILE???");
            Log.i(TAG, bottomNavigationView.getMenu().getItem(0).toString());
            Log.i(TAG, bottomNavigationView.getMenu().getItem(1).toString());
            Log.i(TAG, bottomNavigationView.getMenu().getItem(2).toString());
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            bottomNavigationView.getMenu().getItem(1).setChecked(false);
            bottomNavigationView.getMenu().getItem(2).setChecked(false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, ProfileFragment.newInstance());
            getSupportActionBar().setTitle("Profile");
            transaction.commit();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
