package dirtybro.stooler.Util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by root1 on 2017. 8. 23..
 */

public class BaseActivity extends AppCompatActivity {

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void goNextActivity(Class nextClass){
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
    }

    public SharedPreferences getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }

    public void saveData(String key, String value){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
    }



}
