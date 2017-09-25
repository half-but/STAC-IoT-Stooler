package dirtybro.stooler.Adapter;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import dirtybro.stooler.R;

/**
 * Created by root1 on 2017. 9. 21..
 */

public class SettingAdapter extends RecyclerView.Adapter {

    private SharedPreferences preference;

    public SettingAdapter(SharedPreferences preference){
        this.preference = preference;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_switch,parent,false);
                return new SettingViewHolderWithSwitch(view1);
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_setting_arrow,parent,false);
                return new SettingViewHolder(view2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position > 1){
            SettingViewHolderWithSwitch settingViewHolderWithSwitch = (SettingViewHolderWithSwitch)holder;

        }else{
            SettingViewHolder settingViewHolder = (SettingViewHolder)holder;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(position > 1){
            return 0;
        }else{
            return 1;
        }
    }
}

class SettingViewHolderWithSwitch extends RecyclerView.ViewHolder{

    Switch settingSwitch;
    TextView titleText;

    public SettingViewHolderWithSwitch(View itemView) {
        super(itemView);

    }

}

class SettingViewHolder extends RecyclerView.ViewHolder{

    TextView titleText;
    ImageButton nextButton;

    public SettingViewHolder(View itemView) {
        super(itemView);
    }

}
