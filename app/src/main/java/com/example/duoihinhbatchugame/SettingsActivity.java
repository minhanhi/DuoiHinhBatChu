package com.example.duoihinhbatchugame; // Thay bằng package của bạn

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

// Sửa lại import nếu MusicService của bạn ở package khác
import com.example.duoihinhbatchugame.Service.MusicService;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private SwitchMaterial switchSound, switchVibrate;
    private SharedPreferences sharedPreferences;

    // Tên file lưu trữ
    public static final String PREFS_NAME = "GameSettings";
    public static final String KEY_SOUND = "SoundOn";
    public static final String KEY_VIBRATE = "VibrateOn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Ánh xạ
        btnBack = findViewById(R.id.btn_back);
        switchSound = findViewById(R.id.switch_sound);
        switchVibrate = findViewById(R.id.switch_vibrate);

        // Load cài đặt đã lưu
        loadSettings();

        // Xử lý sự kiện click
        btnBack.setOnClickListener(v -> {
            // Rung khi nhấn nút (nếu được bật)
            VibrationUtils.vibrate(this);
            finish(); // Đóng Activity, quay về màn hình trước
        });

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Lưu cài đặt mới
            saveSetting(KEY_SOUND, isChecked);

            // Cập nhật Service nhạc nền ngay lập tức
            if (isChecked) {
                // Nếu bật, gửi lệnh PLAY
                ((MyApplication) getApplication()).controlMusic(MusicService.ACTION_PLAY);
            } else {
                // Nếu tắt, gửi lệnh PAUSE
                ((MyApplication) getApplication()).controlMusic(MusicService.ACTION_PAUSE);
            }
        });

        // === SỬA ĐỔI Ở ĐÂY ===
        switchVibrate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 1. Lưu cài đặt
            saveSetting(KEY_VIBRATE, isChecked);

            // 2. Nếu người dùng vừa BẬT rung, rung thử 1 cái để báo hiệu
            if (isChecked) {
                VibrationUtils.vibrate(SettingsActivity.this);
            }
        });
        // === KẾT THÚC SỬA ĐỔI ===
    }

    private void loadSettings() {
        // Đọc giá trị, nếu không tìm thấy, mặc định là 'true' (bật)
        boolean isSoundOn = sharedPreferences.getBoolean(KEY_SOUND, true);
        boolean isVibrateOn = sharedPreferences.getBoolean(KEY_VIBRATE, true);

        // Cập nhật giao diện
        switchSound.setChecked(isSoundOn);
        switchVibrate.setChecked(isVibrateOn);
    }

    private void saveSetting(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}