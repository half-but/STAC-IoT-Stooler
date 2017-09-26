package dirtybro.stooler.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dirtybro.stooler.R;
import dirtybro.stooler.Util.BaseActivity;

/**
 * Created by root1 on 2017. 9. 26..
 */

public class AppLockActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_app);

        if(Boolean.parseBoolean(getPreferences().getString("isLock", false + ""))){
            goNextActivity(MainActivity.class);
        }

        final EditText passwordEdit = (EditText)findViewById(R.id.passwordEdit);
        Button nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEdit.getText().toString().isEmpty()){
                    return;
                }

                String currentPw = getPreferences().getString("password", "");
                if(currentPw.equals(passwordEdit.getText().toString())){
                    goNextActivity(MainActivity.class);
                }
            }
        });
    }
}
