package com.example.duoihinhbatchugame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duoihinhbatchugame.object.CauDo;
// Các import cần thiết cho Firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

// Đã loại bỏ các import Retrofit không cần thiết:
// import com.example.duoihinhbatchugame.api.ApiClient;
// import retrofit2.Call;
// import retrofit2.Callback;
// import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;

    // ĐÃ THÊM DÒNG KHAI BÁO BIẾN CẦN THIẾT NÀY
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Khởi tạo Firebase Database và tham chiếu đến node "CauHoi"
        // getInstance() cần google-services.json đã được thêm vào thư mục app/
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("CauHoi");

        // 2. Bắt đầu tải dữ liệu ngay khi Activity được tạo
        layDuLieuCauHoi();
    }

    /**
     * Tải dữ liệu câu hỏi từ Firebase Realtime Database.
     * Sử dụng addListenerForSingleValueEvent để chỉ tải 1 lần duy nhất.
     */
    private void layDuLieuCauHoi() {
        // Lắng nghe dữ liệu
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại không
                if (dataSnapshot.exists()) {
                    DATA.getData().arrCauDo.clear();

                    // Lặp qua từng đối tượng con trong node CauHoi
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        try {
                            // Firebase tự động chuyển đổi JSON thành đối tượng CauDo
                            CauDo cauDo = postSnapshot.getValue(CauDo.class);
                            if (cauDo != null) {
                                DATA.getData().arrCauDo.add(cauDo);
                            }
                        } catch (Exception e) {
                            // Xử lý nếu có lỗi chuyển đổi (ví dụ: thiếu Getter/Setter trong CauDo)
                            Toast.makeText(MainActivity.this, "Lỗi phân tích dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    Toast.makeText(MainActivity.this, "Tải dữ liệu Firebase thành công! (" + DATA.getData().arrCauDo.size() + " câu)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Không tìm thấy câu hỏi trên Firebase. Vui lòng kiểm tra node 'CauHoi'.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi kết nối hoặc bị từ chối truy cập (thường do Rules bị khóa)
                Toast.makeText(MainActivity.this, "Lỗi kết nối Firebase: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void choiGame(View view){
        if(DATA.getData().arrCauDo.size()>0){
            startActivity(new Intent(this,PlayActivity.class));
        } else {
            // Hiển thị cảnh báo nếu dữ liệu chưa sẵn sàng
            Toast.makeText(this, "Dữ liệu chưa sẵn sàng. Đang thử tải lại...", Toast.LENGTH_SHORT).show();
            // Thử gọi lại Firebase để tải dữ liệu
            layDuLieuCauHoi();
        }
    }
}
