package dirtybro.stooler.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import dirtybro.stooler.R;

/**
 * Created by root1 on 2017. 7. 24..
 */

public class CalendarFragment extends Fragment {


    public CalendarFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(new MyAdapter());

        final LinearLayout linear = (LinearLayout)view.findViewById(R.id.layout_current_date);

        final Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);

        AppBarLayout appBarLayout = (AppBarLayout)view.findViewById(R.id.appbar);

        final CollapsingToolbarLayout collapsiong = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingToolbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == -collapsiong.getHeight() + toolbar.getHeight()){
                    linear.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
                    linear.setVisibility(View.VISIBLE);
                }else{
                    linear.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        private class MyViewHolder extends RecyclerView.ViewHolder{
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
