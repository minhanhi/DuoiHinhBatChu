package com.example.duoihinhbatchugame.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.duoihinhbatchugame.R;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    // Các "lệnh" mà chúng ta có thể gửi cho Service
    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_PAUSE = "ACTION_PAUSE";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.song1); // Lấy nhạc từ res/raw
        mediaPlayer.setLooping(true); // Cho phép lặp lại
        mediaPlayer.setVolume(0.5f, 0.5f); // Chỉnh âm lượng (tuỳ chọn)
        Log.d("MusicService", "Service Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case ACTION_PLAY:
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        Log.d("MusicService", "Music Started");
                    }
                    break;
                case ACTION_PAUSE:
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        Log.d("MusicService", "Music Paused");
                    }
                    break;
            }
        }

        // START_NOT_STICKY: Nếu hệ thống giết Service, không tự động khởi động lại
        // cho đến khi app gọi startService() lần nữa.
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên khi Service bị huỷ
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("MusicService", "Service Destroyed, Music Released");
        }
    }
}