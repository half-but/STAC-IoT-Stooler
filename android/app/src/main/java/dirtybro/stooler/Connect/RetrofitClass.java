package dirtybro.stooler.Connect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root1 on 2017. 8. 30..
 */

public class RetrofitClass {

    private static final RetrofitClass ourInstance = new RetrofitClass();

    public static RetrofitClass getInstance() {
        return ourInstance;
    }

    private String url = "http://192.168.1.47:9024";

    public ApiInterface apiInterface;

    private Retrofit retrofit;

    private RetrofitClass() {
        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);
    }
}
