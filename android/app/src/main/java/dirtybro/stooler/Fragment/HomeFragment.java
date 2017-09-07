package dirtybro.stooler.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import dirtybro.stooler.R;

/**
 * Created by root1 on 2017. 7. 19..
 */

public class HomeFragment extends Fragment {
    public static String TAG = "check point";

    public HomeFragment(){

    }

    private TextView getTextView(int id){
        return (TextView) view.findViewById(id);
    }

    TextView dateText, turnCountText, timeCountMinText, timeCountSecText, dangerGaugeText;
    RecyclerView recyclerView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        dateText = getTextView(R.id.dateText);
        turnCountText = getTextView(R.id.turnCountText);
        timeCountMinText = getTextView(R.id.timeCountMinText);
        timeCountSecText = getTextView(R.id.timeCountSecText);
        dangerGaugeText = getTextView(R.id.dangerGaugeText);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(new HomeListAdapter());

        return view;
    }

    private class HomeListAdapter extends RecyclerView.Adapter{
        private int[] colorResouceArr = new int[]{R.drawable.main_circle_shape_red, R.drawable.main_circle_shape_yellow, R.drawable.main_circle_shape_green, R.drawable.main_circle_shape_white, R.drawable.main_circle_shape_black};
        private String[] colorTitleArr = new String[]{"빨간색", "노란색", "초록색", "흰색", "검정색"};
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_card_main, parent,false);
            TextView timeText = (TextView) view.findViewById(R.id.timeText);
            ImageButton infoButton = (ImageButton) view.findViewById(R.id.infoButton);
            return new HomeListViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        private class HomeListViewHolder extends RecyclerView.ViewHolder{
            public HomeListViewHolder(View view, int position) {
                super(view);
                TextView colorTitleText;
                View shapeIconView;

                colorTitleText = (TextView) view.findViewById(R.id.colorTitleText);
                shapeIconView = view.findViewById(R.id.shapeIconView);
                colorTitleText.setText(colorTitleArr[position]);
                shapeIconView.setBackgroundResource(colorResouceArr[position]);
            }
        }

    }
}

