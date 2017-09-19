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

import java.text.SimpleDateFormat;
import java.util.Date;

import dirtybro.stooler.Connect.RetrofitClass;
import dirtybro.stooler.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by root1 on 2017. 8. 12..
 */

public class SearchAP extends BroadcastReceiver {

    private WifiManager wifiManager;
    String identifier = "StoolerCover";

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

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

            Log.d("scan data", scanResult.SSID + "/" + scanResult.level);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd tt:mm:ss");
            String findDate = simpleDateFormat.format(date);
            if(scanResult.SSID.contains(identifier)){
                startLockScreen(scanResult.SSID, findDate);
                RetrofitClass.getInstance().apiInterface.aboutCover("findAP",getCookie(),scanResult.SSID, findDate).enqueue(null);
            }
        }
    }

    WindowManager windowManager;
    View view;
    Chronometer chronometer;

    private void startLockScreen(final String ssid, final String date){
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
            unLockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetrofitClass.getInstance().apiInterface.aboutCover("connectCheckForMobile",getCookie(),ssid,date).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200){
                                setLock(false);
                                windowManager.removeView(view);
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
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

    private String getCookie(){
        return getPreferences().getString("cookie","");
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
