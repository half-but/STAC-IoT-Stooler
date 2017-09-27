package dirtybro.stooler.Util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by root1 on 2017. 8. 23..
 */

public class BaseActivity extends AppCompatActivity {

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void goNextActivity(Class nextClass){
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
        finish();
    }

    public SharedPreferences getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref;
    }

    public void saveData(String key, String value){
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();

        Log.d("data = ", key + " : " + value);
        Log.d("save data = ", getPreferences().getString(key, "fail"));
    }

}
