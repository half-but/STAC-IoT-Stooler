package dirtybro.stooler.Connect;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by root1 on 2017. 8. 5..
 */

public interface ApiInterface {

    @GET("{getDataType}")
    Call<JSONObject> getData(@Path("getDataType") String getDataType);

    @FormUrlEncoded
    @POST("{sign}")
    Call<Void> sign(@Path("sign") String sign, @Field("id") String id, @Field("pw") String pw);

    @GET("findAP")
    Call<Void> findAP(@QueryMap Map<String, String> para);


}