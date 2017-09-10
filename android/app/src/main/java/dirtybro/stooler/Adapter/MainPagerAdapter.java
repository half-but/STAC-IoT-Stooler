package dirtybro.stooler.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dirtybro.stooler.Fragment.CalendarFragment;
import dirtybro.stooler.Fragment.HomeFragment;

/**
 * Created by root1 on 2017. 8. 23..
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private String cookie;

    HomeFragment homeFragment;
    CalendarFragment calendarFragment;

    public MainPagerAdapter(FragmentManager fm, String cookie) {
        super(fm);
        this.cookie = cookie;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new HomeFragment(cookie);
            case 1 : return new CalendarFragment(cookie);
            case 2 : return new HomeFragment(cookie);
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
