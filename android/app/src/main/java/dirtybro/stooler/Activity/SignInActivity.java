package dirtybro.stooler.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
        setContentView(R.layout.activity_signin);

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
                        Log.v("signIn", call.toString() + response.toString());
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
