package dirtybro.stooler.Connect;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by root1 on 2017. 8. 5..
 */

public interface ApiInterface {

    @GET("{getDataType}")
    Call<JsonObject> getData(@Header("Set-Cookie")String cookie, @Path("getDataType") String getDataType, @Query("date")String date);

    @FormUrlEncoded
    @POST("{sign}")
    Call<Void> sign(@Path("sign") String sign, @Field("id") String id, @Field("pw") String pw);

    @GET("{aboutCover}")
    Call<Void> aboutCover(@Path("aboutCover")String path, @Header("Set-Cookie")String cookie, @Query("ssid")String ssid, @Query("date")String date);

}
