package dirtybro.stooler.Model;

/**
 * Created by root1 on 2017. 9. 10..
 */

public class StoolTimeData {
    private int minutes;
    private int second;

    public int allSecond(){
        return minutes * 60 + second;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}