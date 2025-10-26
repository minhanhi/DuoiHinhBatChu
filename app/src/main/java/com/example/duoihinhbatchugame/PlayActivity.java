package com.example.duoihinhbatchugame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
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
    int index = 0;
    ArrayList<String> arrDapAn;
    GridView gdvDapAn;
    ImageView imgAnhCauDo;
    TextView txvTienNguoiDung;
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

    private void anhXa() {
        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        imgAnhCauDo = findViewById(R.id.imgAnhCauDo);
        txvTienNguoiDung = findViewById(R.id.txvTienNguoiDung);
    }

    private void init() {
        model = new PlayModel(this);
        arrCauTraLoi = new ArrayList<>();


        arrDapAn = new ArrayList<>();

    }

    private void hienCauDo() {
        cauDo = model.layCauDo();
        dapAn = cauDo.dapAn;
        bamData();
        hienThiCauTraLoi();
        hienThiDapAn();
        Glide.with(this).load(cauDo.anh).into(imgAnhCauDo);
        model.layThongTin();
        txvTienNguoiDung.setText(model.nguoiDung.tien + "$");
    }

    private void hienThiCauTraLoi() {
        gdvCauTraLoi.setNumColumns(arrCauTraLoi.size());
        gdvCauTraLoi.setAdapter(new DapAnAdapter(this, 0, arrCauTraLoi));
    }

    private void hienThiDapAn() {
        gdvDapAn.setNumColumns(arrDapAn.size() / 2);
        gdvDapAn.setAdapter(new DapAnAdapter(this, 0, arrDapAn));
    }

    private void setOnClick() {
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                if (s.length() != 0 && index < arrCauTraLoi.size()) {

                    for (int i = 0; i < arrCauTraLoi.size(); i++) {
                        if (arrCauTraLoi.get(i).length() == 0) {
                            index = i;
                            break;
                        }
                    }
                    arrDapAn.set(position, "");
                    arrCauTraLoi.set(index, s);
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
                if (s.length() != 0) {
                    index = position;
                    arrCauTraLoi.set(position, "");
                    for (int i = 0; i < arrDapAn.size(); i++) {
                        if (arrDapAn.get(i).length() == 0) {
                            arrDapAn.set(i, s);
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

    private void bamData() {
        index = 0;
        arrCauTraLoi.clear();
        arrDapAn.clear();
        Random r = new Random();
        for (int i = 0; i < dapAn.length(); i++) {
            arrCauTraLoi.add("");
            String s = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s);
            String s1 = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s1);
        }
        for (int i = 0; i < dapAn.length(); i++) {
            String s = "" + dapAn.charAt(i);
            arrDapAn.set(i, s.toUpperCase());
        }
        for (int i = 0; i < arrDapAn.size(); i++) {
            String s = arrDapAn.get(i);
            int vt = r.nextInt(arrDapAn.size());
            arrDapAn.set(i, arrDapAn.get(vt));
            arrDapAn.set(vt, s);
        }
    }

    private void checkWin() {
        String s = "";
        for (String s1 : arrCauTraLoi) {
            s = s + s1;
        }
        s = s.toUpperCase();
        if (s.equals(dapAn.toUpperCase())) {
            Toast.makeText(this, "Ban Da Chien Thang", Toast.LENGTH_SHORT).show();
            model.layThongTin();
            model.nguoiDung.tien = model.nguoiDung.tien + 10;
            model.luuThongTin();
            hienCauDo();
        }

    }

//    public void moGoiY(View view){
//        model.layThongTin();
//        if(model.nguoiDung.tien <5){
//            Toast.makeText(this,"Ban Da Het Tien", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        int id =-1;
//        for (int i = 0; i < arrCauTraLoi.size(); i++) {
//            if (arrCauTraLoi.get(i).length() == 0) {
//                id=i;
//                break;
//            }
//        }
//        if (id == -1) {
//            for (int i = 0; i < arrCauTraLoi.size(); i++) {
//                String s = dapAn.toUpperCase().charAt(i) + "";
//                if (!arrCauTraLoi.get(i).toUpperCase().equals(s)) {
//                    id = i;
//                    break;
//                }
//            }
//            for (int i = 0; i < arrDapAn.size(); i++) {
//                if (arrDapAn.get(i).length() == 0) {
//                    arrDapAn.set(i, arrCauTraLoi.get(id));
//                    break;
//                }
//            }
//        }
//        String goiY = "" + dapAn.charAt(id);
//        goiY = goiY.toUpperCase();
//        for (int i=0;i<arrCauTraLoi.size();i++){
//            if (arrCauTraLoi.get(i).toUpperCase().equals(goiY)){
//                arrCauTraLoi.set(i,"");
//                break;
//            }
//        }
//
//        for (int i = 0; i < arrDapAn.size(); i++) {
//            if (goiY.equals(arrDapAn.get(i))) {
//                arrDapAn.set(i, "");
//                break;
//            }
//        }
//
//
//            arrCauTraLoi.set(id, goiY);
//            hienThiCauTraLoi();
//            hienThiDapAn();

    /// /            gdvDapAn.invalidateViews();
//            model.layThongTin();
//            model.nguoiDung.tien = model.nguoiDung.tien - 5;
//            model.luuThongTin();
//            txvTienNguoiDung.setText(model.nguoiDung.tien + "$");
//
//    }
//}
    public void moGoiY(View view) {
        model.layThongTin();
        if (model.nguoiDung.tien < 5) {
            Toast.makeText(this, "Bạn đã hết tiền", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = -1;
        // Ưu tiên tìm ô trống
        for (int i = 0; i < arrCauTraLoi.size(); i++) {
            if (arrCauTraLoi.get(i) == null || arrCauTraLoi.get(i).isEmpty()) {
                id = i;
                break;
            }
        }
        if (id == -1) {
            for (int i = 0; i < arrCauTraLoi.size(); i++) {
                String expected = ("" + dapAn.charAt(i)).toUpperCase();
                String actual = arrCauTraLoi.get(i).toUpperCase();
                if (!expected.equals(actual)) {
                    id = i;
                    break;
                }
            }
        }

        if (id == -1) {
            Toast.makeText(this, "Tất cả ký tự đã đúng rồi!", Toast.LENGTH_SHORT).show();
            return;
        }

        String goiY = ("" + dapAn.charAt(id)).toUpperCase();

        String oldTop = arrCauTraLoi.get(id);
        if (oldTop != null && !oldTop.isEmpty() && !oldTop.equalsIgnoreCase(goiY)) {
            for (int j = 0; j < arrDapAn.size(); j++) {
                if (arrDapAn.get(j) == null || arrDapAn.get(j).isEmpty()) {
                    arrDapAn.set(j, oldTop);
                    break;
                }
            }
            arrCauTraLoi.set(id, "");
        }

        int indexDuoi = -1;
        for (int i = 0; i < arrDapAn.size(); i++) {
            String s = arrDapAn.get(i);
            if (s != null && s.equalsIgnoreCase(goiY)) {
                indexDuoi = i;
                break;
            }
        }
        if (indexDuoi != -1) {
            arrDapAn.set(indexDuoi, "");
            arrCauTraLoi.set(id, goiY);
        } else {
            arrCauTraLoi.set(id, goiY);
        }

        hienThiCauTraLoi();
        hienThiDapAn();
        if (gdvCauTraLoi != null) gdvCauTraLoi.invalidateViews();
        if (gdvDapAn != null) gdvDapAn.invalidateViews();

        model.nguoiDung.tien -= 5;
        model.luuThongTin();
        txvTienNguoiDung.setText(model.nguoiDung.tien + "$");

        StringBuilder current = new StringBuilder();
        for (String s : arrCauTraLoi) {
            current.append(s);
        }

        if (current.toString().equalsIgnoreCase(dapAn)) {
            Toast.makeText(this, "🎉 Chính xác! Sang câu tiếp theo!", Toast.LENGTH_SHORT).show();
//            sangCauTiepTheo(); // <-- Hàm bạn đã có sẵn để load ảnh mới
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



