package dirtybro.stooler.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import dirtybro.stooler.Adapter.MainPagerAdapter;
import dirtybro.stooler.R;
import dirtybro.stooler.Util.BaseActivity;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    TabShape tab_shape_array[];

    ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_pager = (ViewPager)findViewById(R.id.view_pager);
        TabShape issue_tab_shape = new TabShape((LinearLayout)findViewById(R.id.issue_layout), (TextView)findViewById(R.id.issue_text), findViewById(R.id.issue_check_view),0);
        TabShape calendar_tab_shape = new TabShape((LinearLayout)findViewById(R.id.calendar_layout), (TextView)findViewById(R.id.calendar_text), findViewById(R.id.calendar_check_view),1);
        TabShape setting_tab_shape = new TabShape((LinearLayout)findViewById(R.id.setting_layout), (TextView)findViewById(R.id.setting_text), findViewById(R.id.setting_check_view),2);

        tab_shape_array = new TabShape[]{issue_tab_shape, calendar_tab_shape, setting_tab_shape};

        view_pager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),getPreferences().getString("cookie","")));
        view_pager.addOnPageChangeListener(this);

        setSelectButtonStyle(issue_tab_shape);
    }

    private void setSelectButtonStyle(TabShape tabShape){
        for(TabShape tab_shape : tab_shape_array){
            tab_shape.textView.setTextColor(getResources().getColor(R.color.colorUnSelectText));
            tab_shape.view.setVisibility(View.INVISIBLE);
        }

        tabShape.textView.setTextColor(Color.WHITE);
        tabShape.view.setVisibility(View.VISIBLE);

        view_pager.setCurrentItem(tabShape.position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setSelectButtonStyle(tab_shape_array[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class TabShape {
        LinearLayout layout;
        TextView textView;
        View view;
        int position;

        public TabShape(LinearLayout layout, TextView textView, View view, int position) {
            this.layout = layout;
            this.textView = textView;
            this.view = view;
            this.position = position;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectButtonStyle(TabShape.this);
                }
            });
        }
    }
}


