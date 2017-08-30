package dirtybro.stooler.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import dirtybro.stooler.Connect.RetrofitClass;
import dirtybro.stooler.R;
import dirtybro.stooler.Util.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root1 on 2017. 8. 2..
 */

public class SignActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final EditText idEditText = (EditText)findViewById(R.id.idEditText);
        final EditText pwEditText = (EditText)findViewById(R.id.pwEditText);

        CheckBox autoLoginCheck = (CheckBox)findViewById(R.id.autoLoginCheck);

        Button loginButton = (Button)findViewById(R.id.loginButton);
        Button registerButton = (Button)findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!idEditText.getText().toString().isEmpty() && !pwEditText.getText().toString().isEmpty()){
                    sign("signIn", idEditText.getText().toString(), pwEditText.getText().toString());
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setCookie(String cookie){
        SharedPreferences.Editor editer = getPreferences().edit();
        editer.remove("cookie");
        editer.putString("cookie", cookie);
        editer.commit();
    }

    private void sign(String sign, String id, String pw){
        RetrofitClass.getInstance().apiInterface.sign(sign, id, pw).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200 : showToast("로그인 성공");
                        setCookie(response.headers().get("Set-Cookie"));
                        break;
                    case 400 : showToast("로그인 실패");
                        break;
                    case 500 : showToast("서버 오류");
                        break;
                    default : showToast("error");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}