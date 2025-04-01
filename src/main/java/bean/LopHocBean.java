package bean;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 27, 2024
 */
public class LopHocBean {

    private String ngayDangKy;
    private int soLuongHocVien;

    public LopHocBean() {
    }

    public LopHocBean(String ngayDangKy, int soLuongHocVien) {
        this.ngayDangKy = ngayDangKy;
        this.soLuongHocVien = soLuongHocVien;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public int getSoLuongHocVien() {
        return soLuongHocVien;
    }

    public void setSoLuongHocVien(int soLuongHocVien) {
        this.soLuongHocVien = soLuongHocVien;
    }
}
