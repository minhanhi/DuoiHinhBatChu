package com.example.duoihinhbatchugame;

// === IMPORT CẦN THIẾT ===
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
// Đảm bảo đường dẫn này đúng với vị trí của Dialog Fragment
import com.example.duoihinhbatchugame.Fragment.NhanXuDialogFragment;
import com.example.duoihinhbatchugame.adapter.DapAnAdapter;
import com.example.duoihinhbatchugame.model.PlayModel;
import com.example.duoihinhbatchugame.object.CauDo;
// Giả định bạn có lớp tiện ích này
// import com.example.duoihinhbatchugame.VibrationUtils;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;
import java.util.Random;

// === IMPLEMENT INTERFACE CỦA DIALOG ===
public class PlayActivity extends AppCompatActivity implements NhanXuDialogFragment.NhanXuDialogListener {
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
    TextView txvSoThuTuCauHoi; // THÊM: Dùng để hiển thị "Câu X"
    Button btnNext;

    //home (Đã sửa ID sang icon_home)
    private ImageView btnHome;

    // === BIẾN CHO ADMOB VÀ HIỆU ỨNG ===
    private RewardedAd mRewardedAd;
    private ImageView btnAddMoney;
    private final String TAG = "PlayActivity";
    // SỬA: Dùng RelativeLayout chứa gdvCauTraLoi làm nền cho hiệu ứng tiền bay
    private RelativeLayout relativeLayoutCauTraLoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // === KHỞI TẠO ADMOB ===
        MobileAds.initialize(this, initializationStatus -> {
            Log.d(TAG, "AdMob Initialized.");
        });

        init();
        anhXa();
        setOnClick();

        loadRewardedAd(); // Tải quảng cáo ngay khi vào Activity

        hienCauDo();
    }

    private void anhXa() {
        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        imgAnhCauDo = findViewById(R.id.imgAnhCauDo);
        txvTienNguoiDung = findViewById(R.id.txvTienNguoiDung);
        txvSoThuTuCauHoi = findViewById(R.id.txvSoThuTuCauHoi); // Ánh xạ TextView số câu

        // SỬA LỖI: Ánh xạ nút Home bằng ID mới
        btnHome = findViewById(R.id.icon_home);

        // SỬA LỖI: Ánh xạ RelativeLayout mới cho hiệu ứng
        relativeLayoutCauTraLoi = findViewById(R.id.relativeLayoutCauTraLoi);
        btnAddMoney = findViewById(R.id.btn_add_money); // Thêm dòng này

        // LƯU Ý: Đã loại bỏ các ánh xạ lỗi như btnAddMoney và layoutDapAn
    }

    private void init() {
        model = new PlayModel(this);
        arrCauTraLoi = new ArrayList<>();
        arrDapAn = new ArrayList<>();
    }

    private void hienCauDo() {
        cauDo = model.layCauDo();

        if (cauDo == null) {
            Toast.makeText(this, "Lỗi tải câu đố: Dữ liệu câu hỏi rỗng.", Toast.LENGTH_LONG).show();
            return;
        }

        dapAn = cauDo.getDapAn();

        // === CẬP NHẬT SỐ THỨ TỰ CÂU HỎI ===
        // Giả sử model.getCurrentQuestionIndex() hoặc cauDo.getID() trả về số thứ tự
        // Sửa tại đây:
        int soThuTu = 1; // Thay thế bằng logic lấy số câu đố của bạn
        if (txvSoThuTuCauHoi != null) {
            txvSoThuTuCauHoi.setText("Câu " + soThuTu);
        }

        bamData();
        hienThiCauTraLoi();
        hienThiDapAn();

        // Giữ nguyên Glide, đảm bảo đã override kích thước nếu cần
        Glide.with(this).load(cauDo.getAnh()).into(imgAnhCauDo);

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
        // === Xử lý GridView (Giữ nguyên) ===
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ... Logic click GridView Đáp án (gdvDapAn) giữ nguyên ...
                // VibrationUtils.vibrate(PlayActivity.this); // Gỡ bỏ nếu không có lớp này
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
                // ... Logic click GridView Câu trả lời (gdvCauTraLoi) giữ nguyên ...
                // VibrationUtils.vibrate(PlayActivity.this); // Gỡ bỏ nếu không có lớp này
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

        // === GÁN SỰ KIỆN CLICK CHO NÚT DẤU CỘNG (btnAddMoney) ===

        // 1. Vô hiệu hóa click trên TextView tiền (đã chuyển chức năng)
        txvTienNguoiDung.setOnClickListener(null);

        // 2. Gán sự kiện click cho nút dấu cộng mới (btnAddMoney)
        // Đảm bảo rằng btnAddMoney đã được ánh xạ thành công và không phải là null
        if (btnAddMoney != null) {
            btnAddMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moDialogNhanXu(); // Kích hoạt hàm mở dialog kiếm tiền
                }
            });
        } else {
            // Log lỗi nếu nút + không được tìm thấy, phòng trường hợp lỗi XML
            Log.e("PlayActivity", "Button Add Money (btnAddMoney) is null.");
        }

        // === THÊM SỰ KIỆN CHO NÚT HOME (Giữ nguyên) ===
        if (btnHome != null) {
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
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

    public void moGoiY(View view) {
        model.layThongTin();
        if (model.nguoiDung.tien < 5) {
            Toast.makeText(this, "Bạn đã hết tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = -1;
        for (int i = 0; i < arrCauTraLoi.size(); i++) {
            String expected = ("" + dapAn.charAt(i)).toUpperCase();
            String actual = arrCauTraLoi.get(i);
            if (actual != null && !actual.isEmpty() && !expected.equalsIgnoreCase(actual)) {
                id = i;
                break;
            }
        }
        if (id == -1) {
            for (int i = 0; i < arrCauTraLoi.size(); i++) {
                if (arrCauTraLoi.get(i) == null || arrCauTraLoi.get(i).isEmpty()) {
                    id = i;
                    break;
                }
            }
        }

        if (id == -1) {
            Toast.makeText(this, "Tất cả ký tự đã đúng rồi!", Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(this::hienCauDo, 1500);
            return;
        }

        String goiY = ("" + dapAn.charAt(id)).toUpperCase();
        String oldTop = arrCauTraLoi.get(id);

        if (oldTop != null && !oldTop.isEmpty() && !oldTop.equalsIgnoreCase(goiY)) {
            int viTriGoiYTrongDapAn = -1;
            for (int j = 0; j < arrDapAn.size(); j++) {
                if (goiY.equalsIgnoreCase(arrDapAn.get(j))) {
                    viTriGoiYTrongDapAn = j;
                    break;
                }
            }

            if (viTriGoiYTrongDapAn != -1) {
                arrDapAn.set(viTriGoiYTrongDapAn, oldTop);
            } else {
                for (int j = 0; j < arrDapAn.size(); j++) {
                    if (arrDapAn.get(j) == null || arrDapAn.get(j).isEmpty()) {
                        arrDapAn.set(j, oldTop);
                        break;
                    }
                }
            }

            arrCauTraLoi.set(id, goiY);
        }
        else {
            for (int i = 0; i < arrDapAn.size(); i++) {
                if (goiY.equalsIgnoreCase(arrDapAn.get(i))) {
                    arrDapAn.set(i, "");
                    break;
                }
            }
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
        for (String s : arrCauTraLoi) current.append(s == null ? "" : s);

        if (current.toString().equalsIgnoreCase(dapAn)) {
            Toast.makeText(this, "Chính xác! Sang câu tiếp theo!", Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(this::hienCauDo, 1500);
        }
    }

    public void doiCauHoi(View view) {
        model.layThongTin();
        if (model.nguoiDung.tien < 10) {
            Toast.makeText(this, "Ban Da Het Tien", Toast.LENGTH_SHORT).show();
            return;
        }
        model.nguoiDung.tien = model.nguoiDung.tien - 10;
        model.luuThongTin();
        txvTienNguoiDung.setText(model.nguoiDung.tien + "$");
        hienCauDo();
    }

    private void moDialogNhanXu() {
        NhanXuDialogFragment dialog = new NhanXuDialogFragment();
        dialog.show(getSupportFragmentManager(), "NhanXuDialog");
    }
    

    /**
     * Tải quảng cáo có thưởng
     */
    private void loadRewardedAd() {
        String adUnitId = "ca-app-pub-3940256099942544/5224354917";

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, adUnitId, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                Log.d(TAG, "Ad was loaded.");
                setAdCallbacks();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, "Ad failed to load: " + loadAdError.getMessage());
                mRewardedAd = null;
            }
        });
    }

    /**
     * Gán các callback (lắng nghe sự kiện) cho quảng cáo
     */
    private void setAdCallbacks() {
        if (mRewardedAd == null) return;

        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed full screen.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.e(TAG, "Ad failed to show: " + adError.getMessage());
                loadRewardedAd(); // Tải lại
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.");
                mRewardedAd = null;
                loadRewardedAd(); // Tải lại quảng cáo mới cho lần sau
            }
        });
    }

    /**
     * Hiển thị quảng cáo và xử lý phần thưởng
     */
    private void showRewardedAd(int rewardAmount) {
        if (mRewardedAd != null) {
            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // NGƯỜI DÙNG ĐÃ XEM XONG
                    Log.d(TAG, "User earned reward. Amount: " + rewardAmount);

                    model.layThongTin();
                    model.nguoiDung.tien += rewardAmount;
                    model.luuThongTin();

                    txvTienNguoiDung.setText(model.nguoiDung.tien + "$");

                    showCoinAnimation(rewardAmount);
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
            Toast.makeText(this, "Quảng cáo chưa sẵn sàng, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            loadRewardedAd(); // Tải lại
        }
    }

    /**
     * Hiển thị hiệu ứng tiền bay lên
     */
    private void showCoinAnimation(int amount) {
        TextView tvPlus = new TextView(this);
        tvPlus.setText("+" + amount + "$");
        tvPlus.setTextColor(Color.WHITE);
        tvPlus.setTextSize(32);
        tvPlus.setShadowLayer(5, 0, 0, Color.BLACK);
        tvPlus.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        // SỬA: Dùng biến RelativeLayout mới
        if (relativeLayoutCauTraLoi != null) {
            relativeLayoutCauTraLoi.addView(tvPlus, params);
        } else {
            Log.e(TAG, "relativeLayoutCauTraLoi is null, cannot show animation.");
            return;
        }


        tvPlus.animate()
                .translationYBy(-300)
                .alpha(0.0f)
                .setDuration(1500)
                .withEndAction(() -> {
                    // SỬA: Dùng biến RelativeLayout mới
                    if (relativeLayoutCauTraLoi != null) {
                        relativeLayoutCauTraLoi.removeView(tvPlus);
                    }
                })
                .start();
    }


    // === CÁC HÀM XỬ LÝ SỰ KIỆN TỪ DIALOG ===
    @Override
    public void onWatchAd5s() {
        Log.d(TAG, "Người dùng chọn xem quảng cáo (nhận 20 xu)");
        showRewardedAd(20);
    }

    @Override
    public void onWatchAd30s() {
        Log.d(TAG, "Người dùng chọn xem quảng cáo (nhận 120 xu)");
        showRewardedAd(120);
    }
}