package dirtybro.stooler.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(isFirst()){
            goNextActivity(SignActivity.class);
        }else{
            goNextActivity(MainActivity.class);
        }


    }

    private boolean isFirst(){
        return getPreferences().getString("cookie","").isEmpty();
    }
}
