package dirtybro.stooler.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dirtybro.stooler.Action.CheckColor;
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

    private Context context;

    private String cookie;

    public HomeFragment(String cookie){
        this.cookie = cookie;
    }

    private TextView getTextView(int id){
        return (TextView) view.findViewById(id);
    }

    TextView dateText, turnCountText, timeCountMinText, timeCountSecText;
    RecyclerView recyclerView;
    View view, greenPoint, redPoint, yellowPoint;

    private void setData(String startDateStr, StoolData[] stoolDatas){
        ArrayList<String> times = new ArrayList<>();

        for(StoolData stoolData : stoolDatas){
            times.add(stoolData.getTime());
        }

        setColor(stoolDatas);

        try {
            setAvgTurn(stoolDatas.length);
            setStateCheck(setAvgTime(times), stoolDatas.length);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setColor(StoolData[] data){
        int colorCount[] = new int[5];
        for(int i=0; i<data.length; i++){
            int count = new CheckColor().GetColorValue(data[i].getColor());
            if(count >= 6){
                break;
            }

            colorCount[count - 1] += 1;
        }
        adapter.setColorTime(colorCount);
        adapter.notifyDataSetChanged();
    }

    private void setStateCheck(int time, int count){
        if (time < 5 * 60 && count >= 3){
            setWaringColor(2);
        }else if(time < 5 * 60 || count >= 3){
            setWaringColor(1);
        }else{
            setWaringColor(0);
        }
    }

    private void setAvgTurn(int turn){
        turnCountText.setText(turn + "");
    }

    private int setAvgTime(ArrayList<String> times) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        int sum = 0;
        for(String time : times){
            sum += format.parse(time).getMinutes() * 60;
            sum += format.parse(time).getSeconds();
        }
        sum /= times.size();

        timeCountMinText.setText((sum / 60) + "");
        timeCountSecText.setText((sum % 60) + "");

        return sum;
    }

    public void getDataToServer(){
        RetrofitClass.getInstance().apiInterface.getData(cookie,"getIssueData","").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e(TAG, "" + response.code() );
                switch (response.code()){
                    case 200 :
                        String startDateStr = response.body().get("signUpDate").getAsString();
                        Gson gson = new Gson();
                        JsonElement temp = response.body().get("data");
                        StoolData stoolData[] = gson.fromJson(temp, StoolData[].class);
                        setData(startDateStr, stoolData);
                        break;
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
                Toast.makeText(context, "서버 오류", Toast.LENGTH_SHORT);
            }
        });
    }

    private void setWaringColor(int count){
        int idArr[] = new int[]{R.id.redPoint, R.id.yellowPoint, R.id.greenPoint};
        for(int i=0;i<3;i++){
            if (count == i){
                (view.findViewById(idArr[i])).setVisibility(View.VISIBLE);
            }else{
                (view.findViewById(idArr[i])).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        context = getActivity();

        if(cookie.isEmpty()){
            Intent intent = new Intent(getContext(), SignActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        dateText = getTextView(R.id.dateText);
        turnCountText = getTextView(R.id.turnCountText);
        timeCountMinText = getTextView(R.id.timeCountMinText);
        timeCountSecText = getTextView(R.id.timeCountSecText);

        greenPoint = view.findViewById(R.id.greenPoint);
        yellowPoint = view.findViewById(R.id.yellowPoint);
        redPoint = view.findViewById(R.id.redPoint);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private HomeListAdapter adapter = new HomeListAdapter();

    @Override
    public void onStart() {
        super.onStart();
        getDataToServer();
    }

    private class HomeListAdapter extends RecyclerView.Adapter{

        private int[] colorResouceArr = new int[]{R.color.red, R.color.yellow, R.color.green, R.color.white, R.color.black};
        private String[] colorTitleArr = new String[]{"빨간색", "노란색", "초록색", "흰색", "검정색"};
        private int[] infoStrArr = new int[]{R.string.RED, R.string.YELLOW, R.string.GREEN, R.string.WHITE, R.string.BLACK};
        private int[] colorTime;

        public void setColorTime(int[] colorTime) {
            this.colorTime = colorTime;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_card_main, parent,false);
            return new HomeListViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            HomeListViewHolder homeListHolder = (HomeListViewHolder)holder;

            homeListHolder.colorTitleText.setText(colorTitleArr[position]);
            Drawable drawable = homeListHolder.shapeIconView.getBackground();
            drawable.setColorFilter(getResources().getColor(colorResouceArr[position]), PorterDuff.Mode.SRC_IN);
            homeListHolder.shapeIconView.setBackground(drawable);
            if(colorTime != null){
                homeListHolder.timeText.setText(colorTime[position]+"");
            }

            Log.d(TAG, "" + position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        class HomeListViewHolder extends RecyclerView.ViewHolder{
            TextView colorTitleText;
            View shapeIconView;
            ImageButton infoButton;
            TextView timeText;
            LinearLayout timeLayout;

            public HomeListViewHolder(View view, final int position) {
                super(view);
                infoButton = (ImageButton) view.findViewById(R.id.infoButton);
                infoButton.setTag(position);
                timeText = (TextView) view.findViewById(R.id.timeText);
                colorTitleText = (TextView) view.findViewById(R.id.colorTitleText);
                shapeIconView = view.findViewById(R.id.shapeIconView);
                timeLayout = (LinearLayout)view.findViewById(R.id.timeLayout);

                infoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("대변인의 알림");
                        builder.setMessage(infoStrArr[position]);
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder.create().cancel();
                            }
                        });
                        builder.create().show();
                    }
                });
            }
        }

    }
}

