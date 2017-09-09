package dirtybro.stooler.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 2017. 9. 9..
 */

public class StoolData {

    public StoolData(){}

    public StoolData(String title){}
    @SerializedName("date")
    private String date;

    @SerializedName("color")
    private String color;

    @SerializedName("time")
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
