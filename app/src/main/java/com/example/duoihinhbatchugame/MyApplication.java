package com.example.duoihinhbatchugame; // Thay bằng package của bạn

import android.app.Application;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duoihinhbatchugame.Service.MusicService;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0; // Biến đếm số Activity đang "Start"

    @Override
    public void onCreate() {
        super.onCreate();
        // Đăng ký theo dõi vòng đời của các Activity
        registerActivityLifecycleCallbacks(this);
    }

    // Hàm gửi lệnh đến Service
    public void controlMusic(String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        startService(intent);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (activityCount == 0) {
            // === SỬA ĐỔI Ở ĐÂY ===
            // 1. Đọc cài đặt
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE);
            boolean isSoundOn = prefs.getBoolean(SettingsActivity.KEY_SOUND, true); // Mặc định là true

            // 2. Chỉ phát nhạc nếu người dùng cho phép
            if (isSoundOn) {
                controlMusic(MusicService.ACTION_PLAY); // Gửi lệnh PLAY
            }
            // === KẾT THÚC SỬA ĐỔI ===
        }
        activityCount++;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            // Luôn gửi lệnh PAUSE khi app vào nền,
            // Service sẽ tự kiểm tra xem nó có đang phát hay không.
            controlMusic(MusicService.ACTION_PAUSE); // Gửi lệnh PAUSE
        }
    }

    // Các hàm này bắt buộc phải có khi implement, nhưng chúng ta không cần dùng
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}
    @Override
    public void onActivityResumed(@NonNull Activity activity) {}
    @Override
    public void onActivityPaused(@NonNull Activity activity) {}
    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {}
}