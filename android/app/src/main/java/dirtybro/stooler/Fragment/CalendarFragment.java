package dirtybro.stooler.Fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dirtybro.stooler.Action.CheckColor;
import dirtybro.stooler.Activity.SignActivity;
import dirtybro.stooler.Connect.RetrofitClass;
import dirtybro.stooler.Model.StoolData;
import dirtybro.stooler.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root1 on 2017. 7. 24..
 */


public class CalendarFragment extends Fragment implements CalendarView.OnDateChangeListener {


    private String cookie;

    public CalendarFragment(String cookie){
        this.cookie = cookie;
    }

    MyAdapter adapter;
    RecyclerView recyclerView;
    TextView notDataText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

        Log.e("init", "onCreateView: ");
        if(cookie.isEmpty()){
            Intent intent = new Intent(getContext(), SignActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        notDataText = (TextView)view.findViewById(R.id.notDataText);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(this);
        calendarView.setDate(System.currentTimeMillis());

        Date date = new Date(calendarView.getDate());
        getData(new SimpleDateFormat("yyyy-MM-dd").format(date));

        return view;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Log.d("xxx", "" + year + month + dayOfMonth);
        getData(year + "-" + (month + 1)  + "-" +  dayOfMonth);
    }

    private void getData(String date){
        RetrofitClass.getInstance().apiInterface.getData(cookie, "getCalendarData" , date).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200 :
                        Log.d("xxx", response.body().get("data").toString());
                        Gson gson = new Gson();
                        StoolData stoolData[] = gson.fromJson(response.body().get("data"), StoolData[].class);
                        adapter.setStoolDatas(stoolData);
                        notDataText.setVisibility(View.GONE);
                        break;
                    case 204 :
                        adapter.setStoolDatas(null);
                        notDataText.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "데이터가 없습니다.",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "서버 오류", Toast.LENGTH_SHORT);
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter{

        StoolData[] stoolDatas;

        public synchronized void setStoolDatas(StoolData[] stoolDatas) {
            this.stoolDatas = stoolDatas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            String colorText[] = new String[]{"빨간색", "노란색", "초록색", "흰색", "검정색", "갈색"};

            try {
                Date time = new SimpleDateFormat("mm:ss").parse(stoolDatas[position].getTime());
                myViewHolder.timeText.setText(time.getMinutes() + "분 " + time.getSeconds() + "");

                int count = new CheckColor().GetColorValue(stoolDatas[position].getColor());

                count--;
                int color = Color.parseColor("#" + stoolDatas[position].getColor());

                myViewHolder.statusCode.setBackgroundTintList(ColorStateList.valueOf(color));
                myViewHolder.colorText.setText(colorText[count]);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            if(stoolDatas == null){
                return 0;
            }else{
                return stoolDatas.length;
            }
        }

        private class MyViewHolder extends RecyclerView.ViewHolder{
            TextView timeText, dateText, colorText;
            FloatingActionButton statusCode;
            public MyViewHolder(View itemView) {
                super(itemView);
                statusCode = (FloatingActionButton) itemView.findViewById(R.id.statusColor);
                timeText = (TextView) itemView.findViewById(R.id.timeText);
                dateText = (TextView) itemView.findViewById(R.id.dateText);
                colorText = (TextView) itemView.findViewById(R.id.colorText);
            }
        }
    }
}