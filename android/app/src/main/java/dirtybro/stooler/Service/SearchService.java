package dirtybro.stooler.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import dirtybro.stooler.R;

import static android.content.ContentValues.TAG;

/**
 * Created by root1 on 2017. 7. 20..
 */

public class SearchService extends Service {

    Timer timer;

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
        timer = new Timer();
        TimerTask searchAP = new TimerTask() {
            @Override
            public void run() {

                //ap탐색!
                Log.d(TAG, "run: " + "searchAP");

            }
        };

        timer.schedule(searchAP, 3000);


        return START_REDELIVER_INTENT;
    }

    WindowManager windowManager;
    View view;

    private void startLockScreen(){
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );

        layoutParams.gravity = Gravity.CENTER;
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.activity_lock,null);
        Button button = (Button)view.findViewById(R.id.exitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(view);
            }
        });
        windowManager.addView(view, layoutParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
