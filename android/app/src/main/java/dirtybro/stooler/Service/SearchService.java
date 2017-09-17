package dirtybro.stooler.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import dirtybro.stooler.Action.SearchAP;
import dirtybro.stooler.R;

/**
 * Created by root1 on 2017. 7. 20..
 */

public class SearchService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder notification = new Notification.Builder(getApplicationContext());
        notification.setSmallIcon(R.drawable.logo);
        notification.setContentTitle("대변인의 AutoCatch");
        notification.setContentText("대변인이 당신의 변을 관찰하고 있습니다");
        startForeground(1,notification.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startScan();

        return START_REDELIVER_INTENT;
    }

    private void startScan(){
        IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(new SearchAP(), filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
