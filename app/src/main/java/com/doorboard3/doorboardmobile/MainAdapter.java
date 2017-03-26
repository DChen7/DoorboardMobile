package com.doorboard3.doorboardmobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by danielchen on 3/25/17.
 */
public class MainAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ProfileFragment tab1 = new ProfileFragment();
                return tab1;
            case 1:
                MessageFragment tab2 = new MessageFragment();
                return tab2;
            case 2:
                ScheduleFragment tab3 = new ScheduleFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
