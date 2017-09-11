package dirtybro.stooler.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,container,false);

        if(cookie.isEmpty()){
            Intent intent = new Intent(getContext(), SignActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(this);
        calendarView.setDate(System.currentTimeMillis());

        return view;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Log.d("xxx", "" + year + month + dayOfMonth);
        RetrofitClass.getInstance().apiInterface.getData(cookie, "getCalendarData" ,year+"-"+(month + 1)+"-"+dayOfMonth).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200 :
                        Log.d("xxx", response.body().get("data").toString());
                        Gson gson = new Gson();
                        StoolData stoolData[] = gson.fromJson(response.body().get("data"), StoolData[].class);
                        adapter.setStoolDatas(stoolData);
                        break;
                    case 204 :
                        adapter.setStoolDatas(new StoolData[]{new StoolData(), new StoolData()});
                        break;
                    default:
                        break;
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
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

        }

        @Override
        public int getItemCount() {
            if(stoolDatas == null){
                return 1;
            }else{
                return stoolDatas.length;
            }
        }

        private class MyViewHolder extends RecyclerView.ViewHolder{
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}