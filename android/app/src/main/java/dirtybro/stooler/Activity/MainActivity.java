package dirtybro.stooler.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import dirtybro.stooler.Fragment.CalendarFragment;
import dirtybro.stooler.Fragment.HomeFragment;
import dirtybro.stooler.R;
import dirtybro.stooler.Service.SearchService;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final int unSelectIconArray [] = {R.drawable.homeblack,R.drawable.calender,R.drawable.setting};
        final int selectIconArray [] = {R.drawable.homewhite,R.drawable.calenderwhite,R.drawable.settingwhite};

        for(int i = 0; i<tabLayout.getTabCount();i++){
            tabLayout.getTabAt(i).setIcon(unSelectIconArray[i]);
        }

        tabLayout.getTabAt(0).setIcon(R.drawable.homewhite);

        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setBackgroundColor(Color.LTGRAY);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(selectIconArray[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(unSelectIconArray[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 1){
                return new CalendarFragment();
            }
            return new HomeFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, SearchService.class));
    }
}


