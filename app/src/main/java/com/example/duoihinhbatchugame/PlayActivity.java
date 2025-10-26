package com.example.duoihinhbatchugame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.duoihinhbatchugame.adapter.DapAnAdapter;
import com.example.duoihinhbatchugame.model.PlayModel;
import com.example.duoihinhbatchugame.object.CauDo;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    PlayModel model;
    CauDo cauDo;
    private String dapAn = "yeuot";
    ArrayList<String> arrCauTraLoi;
    GridView gdvCauTraLoi;
    int index= 0;
    ArrayList<String> arrDapAn;
    GridView gdvDapAn;
    ImageView imgAnhCauDo;
    Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        anhXa();
        setOnClick();
       hienCauDo();
    }
    private void anhXa(){
        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        imgAnhCauDo = findViewById(R.id.imgAnhCauDo);
    }
    private void init(){
        model = new PlayModel(this);
        arrCauTraLoi = new ArrayList<>();


        arrDapAn = new ArrayList<>();

    }
    private void hienCauDo(){
        cauDo = model.layCauDo();
        dapAn = cauDo.dapAn;
        bamData();
        hienThiCauTraLoi();
        hienThiDapAn();
        Glide.with(this).load(cauDo.anh).into(imgAnhCauDo);
    }
    private void hienThiCauTraLoi(){
        gdvCauTraLoi.setNumColumns(arrCauTraLoi.size());
        gdvCauTraLoi.setAdapter(new DapAnAdapter(this,0, arrCauTraLoi));
    }
    private void hienThiDapAn(){
        gdvDapAn.setNumColumns(arrDapAn.size()/2);
        gdvDapAn.setAdapter(new DapAnAdapter(this,0, arrDapAn));
    }
    private void setOnClick() {
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                if(s.length()!=0 && index<arrCauTraLoi.size()){

                    for(int i = 0; i<arrCauTraLoi.size();i++){
                        if(arrCauTraLoi.get(i).length()==0){
                            index = i;
                            break;
                        }
                    }
                    arrDapAn.set(position,"");
                    arrCauTraLoi.set(index,s);
                    index++;
                    hienThiCauTraLoi();
                    hienThiDapAn();
                    checkWin();
                }
            }
        });
        gdvCauTraLoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                if(s.length()!=0 ){
                    index = position;
                    arrCauTraLoi.set(position,"");
                    for(int i =0;i<arrDapAn.size();i++){
                        if(arrDapAn.get(i).length()==0){
                            arrDapAn.set(i,s);
                            break;
                        }
                    }
                    hienThiCauTraLoi();
                    hienThiDapAn();
                    checkWin();

                }
            }
        });
    }
    private void bamData(){
        index= 0;
        arrCauTraLoi.clear();
        arrDapAn.clear();
        Random r = new Random();
        for(int i=0; i<dapAn.length();i++){
            arrCauTraLoi.add("");
            String s = "" + (char)(r.nextInt(26)+65);
            arrDapAn.add(s);
            String s1 = "" + (char)(r.nextInt(26)+65);
            arrDapAn.add(s1);
        }
        for(int i=0; i<dapAn.length();i++){
            String s = "" + dapAn.charAt(i);
            arrDapAn.set(i,s.toUpperCase());
        }
        for (int i = 0; i<arrDapAn.size(); i++){
            String s  = arrDapAn.get(i);
            int vt = r.nextInt(arrDapAn.size());
            arrDapAn.set(i,arrDapAn.get(vt));
            arrDapAn.set(vt,s);
        }
    }
    private void checkWin(){
        String s = "";
    for (String s1:arrCauTraLoi){
        s = s+s1;
    }
    s = s.toUpperCase();
    if(s.equals(dapAn.toUpperCase())){
        Toast.makeText(this,"Ban Da Chien Thang", Toast.LENGTH_SHORT).show();
        hienCauDo();
    }


    }

    public void doiCauHoi (View view) {
        models.layThongTin();
        if(models.nguoiDung.tien <10){
            Toast.makeText(this,"Ban Da Het Tien", Toast.LENGTH_SHORT).show();
            return;
        }
        models.nguoiDung.tien = models.nguoiDung.tien -10;
        models.luuThongTin();
        txvTienNguoiDung.setText(models.nguoiDung.tien + "$");
        hienCauDo();
    }
}
