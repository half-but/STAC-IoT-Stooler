package dirtybro.stooler.Activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import dirtybro.stooler.R;
import dirtybro.stooler.Util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 13..
 */

public class SplashActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);


        TedPermission.with(this)
                .setDeniedMessage("Why")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(SplashActivity.this, "Wifi승인", Toast.LENGTH_SHORT).show(); if(isFirst()){
                            goNextActivity(SignActivity.class);
                        }else{
                            goNextActivity(MainActivity.class);
                        }
                        if(isFirst()){
                            goNextActivity(SignActivity.class);
                        }else{
                            goNextActivity(MainActivity.class);
                        }
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        Toast.makeText(SplashActivity.this, "퍼미션거부", Toast.LENGTH_SHORT).show();

                    }
                }).check();




    }

    private boolean isFirst(){
        return getPreferences().getString("cookie","").isEmpty();
    }
}
