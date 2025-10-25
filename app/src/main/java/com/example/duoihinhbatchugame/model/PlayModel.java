package com.example.duoihinhbatchugame.model;

import com.example.duoihinhbatchugame.PlayActivity;
import com.example.duoihinhbatchugame.object.CauDo;

import java.util.ArrayList;

public class PlayModel {
    PlayActivity c;
    ArrayList<CauDo> arr;
    int cauSo= -1;

    public PlayModel(PlayActivity c) {
        this.c = c;
        taoData();
    }
    private void taoData(){
        arr = new ArrayList<>();
        arr.add(new CauDo("man 1","baola","https://statics.123itvn.com/uploads/2024/03/2014-07-1723.37.47-1.png"));
        arr.add(new CauDo("man 2","obama","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTe8ziuu4UAXk7TL5t2tPTpjzr9d6x3b5yG7A&s"));


    }
    public CauDo layCauDo(){
        cauSo++;
        if (cauSo>= arr.size()) {
            cauSo=arr.size()-1;
        }
        return arr.get(cauSo);
    }

}
