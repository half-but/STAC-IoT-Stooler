package dirtybro.stooler.Fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dirtybro.stooler.Activity.SignActivity;
import dirtybro.stooler.Connect.RetrofitClass;
import dirtybro.stooler.Model.StoolData;
import dirtybro.stooler.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root1 on 2017. 7. 19..
 */

public class HomeFragment extends Fragment {
    public static String TAG = "check point";

    private String cookie;

    public HomeFragment(String cookie){
        this.cookie = cookie;
    }

    private TextView getTextView(int id){
        return (TextView) view.findViewById(id);
    }

    TextView dateText, turnCountText, timeCountMinText, timeCountSecText, dangerGaugeText;
    RecyclerView recyclerView;
    View view;

    private void setData(String startDateStr, StoolData[] stoolDatas){
        ArrayList<String> times = new ArrayList<>();
        for(StoolData stoolData : stoolDatas){
            times.add(stoolData.getTime());
        }

        try {
            setAvgTime(times);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setAvgTurn(stoolDatas.length);
    }

    private void setAvgTurn(int turn){
        turnCountText.setText(turn + "");
    }

    private void setAvgTime(ArrayList<String> times) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        int sum = 0;
        for(String time : times){
            sum += format.parse(time).getMinutes() * 60;
            sum += format.parse(time).getSeconds();
        }
        sum /= times.size();
        timeCountMinText.setText((sum / 60) + "");
        timeCountSecText.setText((sum % 60) + "");
    }

    public void getDataToServer(){
        RetrofitClass.getInstance().apiInterface.getData(cookie,"getWeekData","").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200 :
                        String startDateStr = response.body().get("date").getAsString();
                        Gson gson = new Gson();
                        JsonElement temp = response.body().get("data");
                        StoolData stoolData[] = gson.fromJson(temp, StoolData[].class);
                        setData(startDateStr, stoolData);
                    case 204 :
                        Toast.makeText(getContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "서버 오류", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "서버 오류", Toast.LENGTH_SHORT);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        if(cookie.isEmpty()){
            Intent intent = new Intent(getContext(), SignActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

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

    @Override
    public void onStart() {
        super.onStart();
        getDataToServer();
    }

    private class HomeListAdapter extends RecyclerView.Adapter{

        private int[] colorResouceArr = new int[]{R.drawable.main_circle_shape_red, R.drawable.main_circle_shape_yellow, R.drawable.main_circle_shape_green, R.drawable.main_circle_shape_white, R.drawable.main_circle_shape_black};
        private String[] colorTitleArr = new String[]{"빨간색", "노란색", "초록색", "흰색", "검정색"};
        private int[] colorTime;

        public void setColorTime(int[] colorTime) {
            this.colorTime = colorTime;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_card_main, parent,false);
            TextView timeText = (TextView) view.findViewById(R.id.timeText);
            if(colorTime != null){
                timeText.setText(colorTime[viewType] + "");
            }
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
                ImageButton infoButton = (ImageButton) view.findViewById(R.id.infoButton);

                colorTitleText = (TextView) view.findViewById(R.id.colorTitleText);
                shapeIconView = view.findViewById(R.id.shapeIconView);
                colorTitleText.setText(colorTitleArr[position]);
                shapeIconView.setBackgroundResource(colorResouceArr[position]);
            }
        }

    }
}

