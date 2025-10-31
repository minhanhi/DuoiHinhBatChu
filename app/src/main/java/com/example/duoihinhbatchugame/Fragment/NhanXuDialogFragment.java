package com.example.duoihinhbatchugame.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.duoihinhbatchugame.R;

public class NhanXuDialogFragment extends DialogFragment {

    // === THAY ĐỔI Ở ĐÂY ===
    // 1. Định nghĩa Interface (đã bỏ onUpgradeVip)
    public interface NhanXuDialogListener {
        void onWatchAd5s();
        void onWatchAd30s();
        // Đã xoá onUpgradeVip()
    }
    // === KẾT THÚC THAY ĐỔI ===

    private NhanXuDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Nạp layout
        View view = inflater.inflate(R.layout.dialog_nhan_xu, container, false);

        // Làm cho nền của Dialog trong suốt để thấy được bo góc của CardView
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo listener (ép kiểu context)
        try {
            // Thử lấy listener từ Activity cha
            listener = (NhanXuDialogListener) getActivity();
        } catch (ClassCastException e) {
            // Thử lấy listener từ Fragment cha (nếu dialog này được gọi từ fragment khác)
            try {
                listener = (NhanXuDialogListener) getParentFragment();
            } catch (ClassCastException e2) {
                throw new ClassCastException("Calling activity or fragment must implement NhanXuDialogListener");
            }
        }


        // Ánh xạ các view (đã bỏ btn_vip)
        ImageView btnClose = view.findViewById(R.id.btn_close);
        Button btnAd5s = view.findViewById(R.id.btn_ad_5s);
        Button btnAd30s = view.findViewById(R.id.btn_ad_30s);


        // Gán sự kiện click (đã bỏ sự kiện click cho btn_vip)
        btnClose.setOnClickListener(v -> {
            dismiss(); // Đóng dialog
        });

        btnAd5s.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWatchAd5s();
            }
            dismiss(); // Đóng dialog sau khi nhấn
        });

        btnAd30s.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWatchAd30s();
            }
            dismiss();
        });

    }
}