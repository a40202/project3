package dao;

import java.util.List;
import model.LopHoc;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import model.KhoaHoc;
import model.HocVien;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 6, 2024
 */
public class LopHocDAOImpl implements LopHocDAO {

    @Override
    public List<LopHoc> getList() {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "SELECT * FROM lop_hoc";
        List<LopHoc> list = new ArrayList<>();
        try {
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            ResultSet rs = pst.executeQuery();

            while (rs.next() == true) {
                LopHoc lopHoc = new LopHoc();

                lopHoc.setMaLopHoc(rs.getInt("ma_lop_hoc"));
                lopHoc.setTenLopHoc(rs.getString("ten_lop_hoc"));
                lopHoc.setNgayDangKy(rs.getDate("ngay_dang_ky"));
                lopHoc.setTinhTrang(rs.getBoolean("tinh_trang"));

                // Xử lý kiểu dữ liệu đối tượng KhoaHoc
                int maKhoaHoc = rs.getInt("ma_khoa_hoc");
                KhoaHoc khoaHoc = getKhoaHocById(maKhoaHoc);
                lopHoc.setKhoaHoc(khoaHoc);

                // Fetch HocVien object
                int maHocVien = rs.getInt("ma_hoc_vien");
                HocVien hocVien = getHocVienById(maHocVien);
                lopHoc.setHocVien(hocVien);

                list.add(lopHoc);
            }

            pst.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private KhoaHoc getKhoaHocById(int maKhoaHoc) {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "SELECT * FROM khoa_hoc WHERE ma_khoa_hoc = ?";
        try {
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            pst.setInt(1, maKhoaHoc);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                KhoaHoc khoaHoc = new KhoaHoc();
                khoaHoc.setMaKhoaHoc(rs.getInt("ma_khoa_hoc"));
                khoaHoc.setTenMonHoc(rs.getString("ten_khoa_hoc"));
                // Có thể thêm các trường khác nếu muốn ở bên dưới

                rs.close();
                pst.close();
                return khoaHoc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HocVien getHocVienById(int maHocVien) {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "SELECT * FROM hoc_vien WHERE ma_hoc_vien = ?";
        try {
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            pst.setInt(1, maHocVien);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                HocVien hocVien = new HocVien();
                hocVien.setMaHocVien(rs.getInt("ma_hoc_vien"));
                hocVien.setHoTen(rs.getString("ho_ten"));

                rs.close();
                pst.close();
                return hocVien;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        LopHocDAO lopHocDAO = new LopHocDAOImpl();
//        System.out.println(lopHocDAO.getList());
//    }
    @Override
    public int createOrUpdate(LopHoc lopHoc) {
    try {
        // Connect to database
        Connection SqlConn = DBConnect.getConnection();
        
        // SQL Query
        String SqlQuery = "INSERT INTO lop_hoc(ma_lop_hoc, ten_lop_hoc, ma_khoa_hoc, ma_hoc_vien, ngay_dang_ky, tinh_trang) VALUES (?, ?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE ten_lop_hoc = VALUES(ten_lop_hoc), ma_khoa_hoc = VALUES(ma_khoa_hoc), ma_hoc_vien = VALUES(ma_hoc_vien), ngay_dang_ky = VALUES(ngay_dang_ky), tinh_trang = VALUES(tinh_trang);";

        // Create PreparedStatement
        PreparedStatement pst = SqlConn.prepareStatement(SqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        
        // Ghi log các giá trị trước khi thực hiện câu lệnh
        System.out.println("maLopHoc: " + lopHoc.getMaLopHoc());
        System.out.println("tenLopHoc: " + lopHoc.getTenLopHoc());
        System.out.println("maKhoaHoc: " + lopHoc.getKhoaHoc().getMaKhoaHoc());
        System.out.println("maHocVien: " + lopHoc.getHocVien().getMaHocVien());
        System.out.println("ngayDangKy: " + lopHoc.getNgayDangKy());
        System.out.println("tinhTrang: " + lopHoc.isTinhTrang());
        
        // Các giá trị từ 1 đến n sẽ tương ứng với các parameter ? trong SQLQuery
        pst.setInt(1, lopHoc.getMaLopHoc());
        pst.setString(2, lopHoc.getTenLopHoc());
        pst.setInt(3, lopHoc.getKhoaHoc().getMaKhoaHoc());
        pst.setInt(4, lopHoc.getHocVien().getMaHocVien());
        pst.setDate(5, new Date(lopHoc.getNgayDangKy().getTime()));
        pst.setBoolean(6, lopHoc.isTinhTrang());
        
        // Execute query
        pst.execute();
        
        // Create Result Set (Dùng để chứa dữ liệu trả về từ DateBase)
        ResultSet rs = pst.getGeneratedKeys();
        // Lấy giá trị khóa tự sinh trong database từ result set
        int generatedKey = 0;
        if (rs.next()) {
            generatedKey = rs.getInt(1);
            // Lay gia tri cua cot dau tien trong result set (Cột này thường sẽ là khóa chính và tự động tăng)
        }
        pst.close();
        rs.close();
        DBConnect.closeConnection(SqlConn);
        return generatedKey;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

    @Override
    public boolean detete(int maLopHoc) {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "DELETE FROM lop_hoc WHERE ma_lop_hoc = ?";
        try {
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            pst.setInt(1, maLopHoc);
            int rowAffected = pst.executeUpdate();
            pst.close();
            SqlConn.close();
            return rowAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
