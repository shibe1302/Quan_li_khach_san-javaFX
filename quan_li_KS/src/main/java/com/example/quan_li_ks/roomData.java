package com.example.quan_li_ks;

public class roomData {
    private Integer phongso;
    private String LoaiPhong;
    private String TinhTrang;
    private Double thanhTien;

    public roomData(Integer phongso, String loaiPhong, String tinhTrang, Double thanhTien) {
        this.phongso = phongso;
        LoaiPhong = loaiPhong;
        TinhTrang = tinhTrang;
        this.thanhTien = thanhTien;
    }

    public Integer getPhongso() {
        return phongso;
    }

    public void setPhongso(Integer phongso) {
        this.phongso = phongso;
    }

    public String getLoaiPhong() {
        return LoaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        LoaiPhong = loaiPhong;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public Double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
