package dirtybro.stooler.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dirtybro.stooler.Fragment.HomeFragment;

/**
 * Created by root1 on 2017. 8. 23..
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new HomeFragment();
            case 1 : return new HomeFragment();
            case 2 : return new HomeFragment();
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
