package com.example.onthicuoiky.model;

public class KhoaHoc {
    private int id ;
    private String tenHoaHoc;
    private String soTien;

    public KhoaHoc(int id, String tenHoaHoc, String soTien) {
        super();
        this.id = id;
        this.tenHoaHoc = tenHoaHoc;
        this.soTien = soTien;
    }
    public KhoaHoc() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenHoaHoc() {
        return tenHoaHoc;
    }

    public void setTenHoaHoc(String tenHoaHoc) {
        this.tenHoaHoc = tenHoaHoc;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    @Override
    public String toString() {
        return "KhoaHoc{" +
                "id=" + id +
                ", tenHoaHoc='" + tenHoaHoc + '\'' +
                ", soTien='" + soTien + '\'' +
                '}';
    }
}
