package dirtybro.stooler.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import dirtybro.stooler.R;

/**
 * Created by root1 on 2017. 7. 19..
 */

public class HomeFragment extends Fragment {
    public static String TAG = "check point";

    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(new HomeListAdapter());

        return view;
    }

    private class HomeListAdapter extends RecyclerView.Adapter{


        int viewID [] = {R.layout.view_circlegraph,R.layout.view_card};

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewID[viewType],parent,false);


            return new MyViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public int getItemViewType(int position) {
            if(position > 0){
                return 1;
            }else{
                return 0;
            }
        }

        private class MyViewHolder extends RecyclerView.ViewHolder{

            View view;

            public MyViewHolder(View itemView,int viewType) {
                super(itemView);
                if(viewType == 0){
                    PieChart pieChart = (PieChart)itemView.findViewById(R.id.circlegraph);
                    pieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
                    pieChart.addPieSlice(new PieModel("Sleep", 15, Color.parseColor("#56B7F1")));
                    pieChart.addPieSlice(new PieModel("Work", 15, Color.parseColor("#CDA67F")));
                    pieChart.addPieSlice(new PieModel("Eating", 19, Color.parseColor("#FED70E")));
                    pieChart.setAnimationTime(500);
                    pieChart.startAnimation();
                }
                view = itemView;
            }

            public View getView(){
                return view;
            }

            public void setView(){

            }
        }
    }
}

