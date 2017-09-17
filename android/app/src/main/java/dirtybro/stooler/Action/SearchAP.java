package dirtybro.stooler.Action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;

import java.util.ArrayList;

import dirtybro.stooler.R;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by root1 on 2017. 8. 12..
 */

public class SearchAP extends BroadcastReceiver implements View.OnClickListener {

    private WifiManager wifiManager;
    ArrayList<String> ssidData = new ArrayList<>();
    ArrayList<Integer> rssData = new ArrayList<>();
    String identifier = "StoolerCover";

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        Log.d("xxx", "hello world");

        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        String action = intent.getAction();
        if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
            startLockScreen();
            getWifiResult();
        } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
            context.sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
        }
    }

    private void getWifiResult(){
        if(wifiManager.isWifiEnabled() == false){
            wifiManager.setWifiEnabled(true);
        }

        Log.d("xxx", "getWifiResult: " + wifiManager.getScanResults().toString());

        for(ScanResult scanResult :  wifiManager.getScanResults()){
            ssidData.add(scanResult.SSID);
            rssData.add(scanResult.level * -1);
            Log.d("xxx", scanResult.SSID + scanResult.level);
        }
    }

    WindowManager windowManager;
    View view;
    Chronometer chronometer;

    private void startLockScreen(){
        Log.d("xxx", "nice too meet you" + isLock());
        if(!isLock()){
            setLock(true);
            windowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT
            );

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.activity_lock,null);
            ImageButton unLockButton = (ImageButton) view.findViewById(R.id.unLockButton);
            unLockButton.setOnClickListener(this);
            chronometer = (Chronometer) view.findViewById(R.id.countChronometer);
            //chronometer.setBase();
            chronometer.start();
            chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    Log.d("xxx", chronometer.getText().toString());
                }
            });

            windowManager.addView(view, layoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        setLock(false);
        windowManager.removeView(view);
    }

    private void setLock(boolean isLock){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove("isLock");
        editor.putBoolean("isLock", isLock);
        editor.commit();
    }

    private boolean isLock(){
        return getPreferences().getBoolean("isLock", false);
    }


    private SharedPreferences getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }

}
