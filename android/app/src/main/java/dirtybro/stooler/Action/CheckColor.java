package dirtybro.stooler.Action;

import dirtybro.stooler.Model.ColorData;

/**
 * Created by root1 on 2017. 8. 12..
 */

public class CheckColor {

    private static ColorData[] stanColor = {
            new ColorData(153, 102, 51), //갈색
            new ColorData(0, 255, 0), //초록
            new ColorData(255, 0, 0), //빨강
            new ColorData(255, 255, 0), //노랑
            new ColorData(255, 255, 255), //하양
            new ColorData(0, 0, 0) //검정
    };

    public static int getColorValue(ColorData inColor){
        int outColorData = -1;
        Double outData = null;

        for(int i = 0; i < 7; i++) {
            Double tempData = Math.sqrt(getResult(stanColor[i].getR(), inColor.getR()) + getResult(stanColor[i].getG(), inColor.getG()) + getResult(stanColor[i].getB(), inColor.getB()));
            if(outColorData == -1){
                outColorData = i;
                outData = tempData;
            }else{
                if(outData > tempData){
                    outColorData = i;
                    outData = tempData;
                }
            }
        }

        return outColorData;
    }

    private static double getResult(int stanData, int inData){
        return Math.pow(inData - stanData, 2);
    }
}
