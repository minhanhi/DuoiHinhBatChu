package com.example.duoihinhbatchugame.model;

// ĐÃ XÓA: import com.google.gson.annotations.Expose;
// ĐÃ XÓA: import com.google.gson.annotations.SerializedName;

public class CauHoiModel {

    // Đảm bảo tên biến (anh, dapAn, ten) KHỚP CHÍNH XÁC với tên khóa trong Firebase
    private String anh;
    private String dapAn;
    private String ten;

    // CONSTRUCTOR RỖNG (BẮT BUỘC cho Firebase)
    public CauHoiModel() {
    }

    // Getters and Setters (BẮT BUỘC cho Firebase)
    public String getAnh() { return anh; }
    public void setAnh(String anh) { this.anh = anh; }

    public String getDapAn() { return dapAn; }
    public void setDapAn(String dapAn) { this.dapAn = dapAn; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
}
