package com.example.duoihinhbatchugame;

import com.example.duoihinhbatchugame.object.CauDo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DATA {
    private static DATA data;
    static {
        data = new DATA();
    }
    public static DATA getData(){
        return data;
    }
    public ArrayList<CauDo> arrCauDo = new ArrayList<>();
}
