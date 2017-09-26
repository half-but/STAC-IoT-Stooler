package com.dirtybro.drstooler;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public class CkeckColor {

    double[] getColor = new double[3];

    double[][] color = { //7가지 색 rgb 값
            {153, 102, 51}, //갈색
            {0, 255, 0}, // 초록
            {255, 0, 0}, // 빨강
            {255, 255, 0}, //노랑
            {0, 0, 255}, // 파랑
            {255, 255, 255}, //하양
            {0, 0, 0} // 검정
    };

    ArrayList<Double> result = new ArrayList<>();
    double minimum;
    int locate = NULL;

    public int GetColorValue(String hex) { //이 함수를 외부에서 호출.
        changeHex(hex);
        calcSimilar(getColor);
        minimum = getMin();
        locate = getLocate();

        return locate; // 1:갈색 / 2:초록 / 3:빨강 / 4:노랑 / 5:파랑 / 6:하양 / 7:검정
    }

    public void changeHex(String hex) {
        double R, G, B;
        String h1, h2, h3;

        h1 = hex.substring(0, 2);
        h2 = hex.substring(2, 4);
        h3 = hex.substring(4);

        R = Integer.parseInt(h1, 16);
        G = Integer.parseInt(h2, 16);
        B = Integer.parseInt(h3, 16);

        getColor[0] = R;
        getColor[1] = G;
        getColor[2] = B;
    }

    public void calcSimilar(double[] getColor) { //유사값 추출
        Double index;

        for(int i = 0; i < 7; i++) {
            index = Math.sqrt(Math.pow(getColor[0] - color[i][0], 2) + Math.pow(getColor[1] - color[i][1], 2) + Math.pow(getColor[2] - color[i][2], 2));
            result.add(index);
        }
    }

    public double getMin() { //최소값 계산
        double min = result.indexOf(0);

        for(int i = 1; i < result.size(); i++) {
            if(result.indexOf(i) < min) {
                min = result.indexOf(i);
            }
        }
        return min;
    }

    public int getLocate() {
        int loc = NULL;
        for (int i = 0; i < result.size(); i++) {
            if (result.indexOf(i) == minimum){
                loc = i;
            }
        }
        return loc;
    }
}
