package dao;

import java.util.List;
import model.KhoaHoc;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 7, 2024
 */
public class KhoaHocDAOImpl implements KhoaHocDAO{

    @Override
    public List<KhoaHoc> getList() {
        try {
            // Connect to database
            Connection SqlConn = DBConnect.getConnection();
            
            // SQlQuery 
            String SqlQuery = "SELECT * FROM khoa_hoc";
            
            // Create array chứa danh sách khóa học
            List<KhoaHoc> list = new ArrayList<>();
            
            // Create prepared statement
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            
            // Create result set
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()){
                KhoaHoc khoaHoc = new KhoaHoc();
                khoaHoc.setMaKhoaHoc(rs.getInt("ma_khoa_hoc"));
                khoaHoc.setTenMonHoc(rs.getString("ten_khoa_hoc"));
                khoaHoc.setMoTa(rs.getString("mo_ta"));
                khoaHoc.setNgayBatDau(rs.getDate("ngay_bat_dau"));
                khoaHoc.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
                khoaHoc.setTinhTrang(rs.getBoolean("tinh_trang"));
                
                list.add(khoaHoc);
            }
            pst.close();
            rs.close();
            DBConnect.closeConnection(SqlConn);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        HocVienDAO hocVienDAO = new HocVienDAOImpl();
        System.out.println(hocVienDAO.getList());
    }

    @Override
    public int createOrUpdate(KhoaHoc khoaHoc) {
        try {
            // Connect to database
            Connection SqlConn = DBConnect.getConnection();
            
            // SQL Query
            String SqlQuery = "INSERT INTO khoa_hoc(ma_khoa_hoc, ten_khoa_hoc, mo_ta, ngay_bat_dau, ngay_ket_thuc, tinh_trang) VALUES (?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE ten_khoa_hoc = VALUES(ten_khoa_hoc), mo_ta = VALUES(mo_ta), ngay_bat_dau = VALUES(ngay_bat_dau), ngay_ket_thuc = VALUES(ngay_ket_thuc), tinh_trang = VALUES(tinh_trang);";
//          ON DUPLICATE KEY UPDATE: Cú pháp này sẽ được kích hoạt nếu có một bản ghi trùng khóa chính (hoặc trùng khóa duy nhất) với dữ liệu đang được chèn.
//          ho_ten = VALUES(ho_ten): Cập nhật cột ho_ten với giá trị mới từ câu lệnh INSERT. Các cột khác (ngay_sinh, gioi_tinh, so_dien_thoai, dia_chi, tinh_trang) cũng được cập nhật tương tự.
            
            //  Create PreparedStatement
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            // Các giá trị từ 1 đến n sẽ tương ứng với các parameter ? trong SQLQuery
            pst.setInt(1, khoaHoc.getMaKhoaHoc());
            pst.setString(2, khoaHoc.getTenMonHoc());
            pst.setString(3, khoaHoc.getMoTa());
            pst.setDate(4, new Date(khoaHoc.getNgayBatDau().getTime()));
            pst.setDate(5, new Date(khoaHoc.getNgayKetThuc().getTime()));
            pst.setBoolean(6, khoaHoc.isTinhTrang());
            
            // Execute query
            pst.execute();
            
            // Create Result Set (Dùng để chứa dữ liệu trả về từ DateBase)
            ResultSet rs = pst.getGeneratedKeys();
            // Lấy giá trị khóa tự sinh trong database từ result set
            int generatedKey = 0;
            if (rs.next()==true){
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
    public boolean detete(int maKhoaHoc) {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "DELETE FROM khoa_hoc WHERE ma_khoa_hoc = ?";
        try {
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            pst.setInt(1, maKhoaHoc);
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
