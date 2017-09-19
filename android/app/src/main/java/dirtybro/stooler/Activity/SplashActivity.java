package dirtybro.stooler.Activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
                .setDeniedMessage("대변인 커버 탐색을 위해서 권한이 필요합니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if(isFirst()){
                            goNextActivity(SignActivity.class);
                        }else{
                            goNextActivity(MainActivity.class);
                        }
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        finish();
                    }
                }).check();




    }

    private boolean isFirst(){
        return getPreferences().getString("cookie","").isEmpty();
    }
}
