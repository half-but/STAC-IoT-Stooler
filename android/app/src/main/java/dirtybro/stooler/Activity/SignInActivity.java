package dirtybro.stooler.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dirtybro.stooler.Connect.ApiInterface;
import dirtybro.stooler.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by root1 on 2017. 8. 2..
 */

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText pwText = (EditText)findViewById(R.id.passwordText);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.47:3000/").build();

        final ApiInterface apiService = retrofit.create(ApiInterface.class);


        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.sign("signIn", idText.getText().toString(), pwText.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.v("signIn", response.code() + "");
                        if(response.code() == 200){
                            Snackbar.make(getWindow().getDecorView().getRootView(), "로그인 성공", Snackbar.LENGTH_SHORT).show();
                        }else if(response.code() == 400){
                            Snackbar.make(getWindow().getDecorView().getRootView(), "로그인 실패", Snackbar.LENGTH_LONG).setAction("회원가입", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                                    finish();
                                }
                            }).show();
                        }else{
                            Snackbar.make(getWindow().getDecorView().getRootView(), "서버 오류입니다. 잠시 후 다시 시도해 주세요", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Log.v("signIn", call.toString() + throwable.toString());
                    }
                });
            }
        });

    }
}
