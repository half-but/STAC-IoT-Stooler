package dirtybro.stooler.Model;

/**
 * Created by root1 on 2017. 9. 10..
 */

public class StoolDateData {
    private int year;
    private int month;
    private int day;

    //자신이 작으면 트루 크면 팰스
    public boolean compareTo(StoolDateData stoolDateData){
        if(stoolDateData.year > year){
            return true;
        }else if(stoolDateData.year == year && stoolDateData.month > month){
            return true;
        }else if(stoolDateData.year == year && stoolDateData.month == month && stoolDateData.day > day){
            return true;
        }else{
            return false;
        }
    }

    public StoolDateData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
