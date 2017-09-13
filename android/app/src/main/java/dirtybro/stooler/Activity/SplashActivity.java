package dirtybro.stooler.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import dirtybro.stooler.R;
import dirtybro.stooler.Util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 9..
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        if(cookieCheck()){
//            goNextActivity(SignActivity.class);
//        }else{
//            goNextActivity(MainActivity.class);
//        }
    }

    private boolean cookieCheck(){
        return getPreferences().getString("cookie","").isEmpty();
    }
}
