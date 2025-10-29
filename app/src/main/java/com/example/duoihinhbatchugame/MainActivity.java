package com.example.duoihinhbatchugame;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duoihinhbatchugame.api.LayCauHoi;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btnPlay = findViewById(R.id.btnPlay);
//
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        new LayCauHoi().execute();
    }
    public void choiGame(View view){
        if(DATA.getData().arrCauDo.size()>0){
            startActivity(new Intent(this,PlayActivity.class));
        } else {
            // Thêm một thông báo Toast để cảnh báo người dùng
            Toast.makeText(this, "Đang tải dữ liệu. Vui lòng thử lại sau giây lát.", Toast.LENGTH_SHORT).show();
        }
    }
}