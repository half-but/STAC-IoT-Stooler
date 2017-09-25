package dirtybro.stooler.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dirtybro.stooler.Adapter.SettingAdapter;
import dirtybro.stooler.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by root1 on 2017. 9. 21..
 */

public class SettingFragment extends Fragment {

    private Context context;
    public SettingFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(new SettingAdapter(getPreferences()));
        return view;
    }

    private SharedPreferences getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }
}
