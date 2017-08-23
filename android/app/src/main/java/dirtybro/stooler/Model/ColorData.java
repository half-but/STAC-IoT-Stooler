package dirtybro.stooler.Model;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by root1 on 2017. 8. 12..
 */

public class ColorData {
    private int r, g, b;

    public ColorData(String rgbCode){
        int code = Color.parseColor(rgbCode);
        Log.d("color code", "" + code);
    }

    public ColorData(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}
