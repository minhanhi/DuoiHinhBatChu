package com.example.duoihinhbatchugame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationUtils {

    // Đặt độ dài rung (50 mili-giây)
    private static final long VIBRATE_DURATION_MS = 50;

    public static void vibrate(Context context) {
        if (context == null) {
            return;
        }

        // 1. Kiểm tra cài đặt của người dùng
        SharedPreferences prefs = context.getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE);
        // Đọc cài đặt, nếu không có thì mặc định là 'true' (cho phép rung)
        boolean isVibrateOn = prefs.getBoolean(SettingsActivity.KEY_VIBRATE, true);

        if (!isVibrateOn) {
            return; // Người dùng đã tắt rung, không làm gì cả
        }

        // 2. Lấy dịch vụ Rung
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null || !vibrator.hasVibrator()) {
            return; // Thiết bị không hỗ trợ rung
        }

        // 3. Thực hiện rung
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Dành cho Android 8.0 (API 26) trở lên
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION_MS, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            // Dành cho các phiên bản Android cũ hơn
            vibrator.vibrate(VIBRATE_DURATION_MS);
        }
    }
}