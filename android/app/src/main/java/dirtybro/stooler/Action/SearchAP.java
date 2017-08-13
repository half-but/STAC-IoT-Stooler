package dirtybro.stooler.Action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by root1 on 2017. 8. 12..
 */

public class SearchAP extends BroadcastReceiver {

    private WifiManager wifiManager;
    ArrayList<String> ssidData = new ArrayList<>();
    ArrayList<Integer> rssData = new ArrayList<>();
    String identifier = "StoolerCover";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("xxx", "hello");
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        String action = intent.getAction();
        Log.d("xxx", action);
        if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
            Log.d("xxx", "world");
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

}
