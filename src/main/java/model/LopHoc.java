package model;

//import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 11, 2024
 */
public class LopHoc{
    private int maLopHoc;
    private String tenLopHoc;
    private KhoaHoc khoaHoc;
    private HocVien hocVien;
    private Date ngayDangKy;
    private boolean tinhTrang;

    public int getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(int maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getTenLopHoc(){
        return tenLopHoc;
    }
    
    public void setTenLopHoc(String tenLopHoc){
        this.tenLopHoc = tenLopHoc;
    }
    
    public KhoaHoc getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(KhoaHoc khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public HocVien getHocVien() {
        return hocVien;
    }

    public void setHocVien(HocVien hocVien) {
        this.hocVien = hocVien;
    }

    public Date getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Date ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "LopHoc{" + "maLopHoc=" + maLopHoc + ", tenLopHoc=" + tenLopHoc + ", khoaHoc=" + khoaHoc + ", hocVien=" + hocVien + ", ngayDangKy=" + ngayDangKy + ", tinhTrang=" + tinhTrang + '}';
    }
}