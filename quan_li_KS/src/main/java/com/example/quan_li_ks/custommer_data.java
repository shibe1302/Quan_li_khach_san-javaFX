package com.example.quan_li_ks;

import java.sql.Date;

public class custommer_data {
    private Integer makhach;
    private String hoten;
    private String sdt;
    private Double total;
    private Date vao1;
    private Date ra1;

    public custommer_data(Integer makhach, String hoten, String sdt, Double total, Date vao1, Date ra1) {
        this.makhach = makhach;
        this.hoten = hoten;
        this.sdt = sdt;
        this.total = total;
        this.vao1 = vao1;
        this.ra1 = ra1;
    }

    public Integer getMakhach() {
        return makhach;
    }

    public void setMakhach(Integer makhach) {
        this.makhach = makhach;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getVao1() {
        return vao1;
    }

    public void setVao1(Date vao1) {
        this.vao1 = vao1;
    }

    public Date getRa1() {
        return ra1;
    }

    public void setRa1(Date ra1) {
        this.ra1 = ra1;
    }
}
