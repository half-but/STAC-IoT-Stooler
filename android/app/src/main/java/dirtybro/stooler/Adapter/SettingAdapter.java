package dirtybro.stooler.Adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import dirtybro.stooler.Activity.SignActivity;
import dirtybro.stooler.R;
import dirtybro.stooler.Util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 21..
 */

public class SettingAdapter extends RecyclerView.Adapter {

    private BaseActivity activity;

    public SettingAdapter(BaseActivity activity){
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 2:
            case 3:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_switch,parent,false);
                return new SettingViewHolderWithSwitch(view1);
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_arrow,parent,false);
                return new SettingViewHolder(view2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String title[] = new String[]{"커버 외이파이 설정", "회원 변경", "앱 잠금 설정"};

        if(position > 1){
            final SettingViewHolderWithSwitch settingViewHolderWithSwitch = (SettingViewHolderWithSwitch)holder;
            settingViewHolderWithSwitch.settingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(activity.getPreferences().getString("password", "").isEmpty()){
                        activity.showToast("비밀번호를 먼저 설정하세요");
                        settingViewHolderWithSwitch.settingSwitch.setChecked(false);
                    }else{
                        activity.saveData("isLock", isChecked+"");
                    }
                }
            });

            settingViewHolderWithSwitch.titleText.setText(title[position]);

            String checked = activity.getPreferences().getString("isLock", "false");
            settingViewHolderWithSwitch.settingSwitch.setChecked(Boolean.parseBoolean(checked));
        }else{

            SettingViewHolder settingViewHolder = (SettingViewHolder)holder;
            if(position == 0){

            }else{
                settingViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.goNextActivity(SignActivity.class);
                    }
                });
            }

            settingViewHolder.titleText.setText(title[position]);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class SettingViewHolderWithSwitch extends RecyclerView.ViewHolder implements View.OnClickListener{

        Switch settingSwitch;
        TextView titleText;

        public SettingViewHolderWithSwitch(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            settingSwitch = (Switch)itemView.findViewById(R.id.settingSwitch);
            titleText = (TextView)itemView.findViewById(R.id.titleText);
        }

        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final EditText editText = new EditText(activity);
            editText.setHint("비밀번호를 입력하세요");

            builder.setTitle("비밀번호 설정").setView(editText)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.saveData("password", editText.getText().toString());
                            builder.create().cancel();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().cancel();
                        }
                    }).create().show();
        }
    }

    class SettingViewHolder extends RecyclerView.ViewHolder{

        TextView titleText;
        ImageButton nextButton;
        View view;

        public SettingViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            titleText = (TextView)itemView.findViewById(R.id.titleText);
        }

    }

}
