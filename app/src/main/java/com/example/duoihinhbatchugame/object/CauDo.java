package com.example.duoihinhbatchugame.object;

// ĐÃ XÓA: import com.google.gson.annotations.Expose;
// ĐÃ XÓA: import com.google.gson.annotations.SerializedName;

public class CauDo {

    // Đảm bảo tên biến (ten, dapAn, anh) KHỚP CHÍNH XÁC với tên khóa trong Firebase
    private String ten;
    private String dapAn;
    private String anh;

    // CONSTRUCTOR RỖNG (CẦN THIẾT cho Firebase để tạo đối tượng khi đọc dữ liệu)
    public CauDo() {
    }

    // CONSTRUCTOR ĐẦY ĐỦ (Tùy chọn, dùng để tạo đối tượng thủ công)
    public CauDo(String ten, String dapAn, String anh) {
        this.ten = ten;
        this.dapAn = dapAn;
        this.anh = anh;
    }

    // GETTERS VÀ SETTERS (BẮT BUỘC cho Firebase để đọc và gán giá trị vào biến private)
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
