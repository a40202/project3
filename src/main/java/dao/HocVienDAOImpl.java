package dao;

import java.util.List;
import model.HocVien;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 15, 2024
 */
public class HocVienDAOImpl implements HocVienDAO{

    // Continued
    @Override
    public List<HocVien> getList() {
        try {
            Connection SqlConn = dao.DBConnect.getConnection();
            String SqlQuery = "SELECT * FROM hoc_vien";
            List<HocVien> list = new ArrayList<HocVien>();
            PreparedStatement pst = SqlConn.prepareCall(SqlQuery);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next() == true){
                HocVien hocVien = new HocVien();
                hocVien.setMaHocVien(rs.getInt("ma_hoc_vien"));
                hocVien.setHoTen(rs.getString("ho_ten"));
                hocVien.setNgaySinh(rs.getDate("ngay_sinh"));
                hocVien.setSoDienThoai(rs.getString("so_dien_thoai"));
                hocVien.setGioiTinh(rs.getBoolean("gioi_tinh"));
                hocVien.setDiaChi(rs.getString("dia_chi"));
                hocVien.setTinhTrang(rs.getBoolean("tinh_trang"));
                
                list.add(hocVien);
            }
            pst.close();
            rs.close();
            DBConnect.closeConnection(SqlConn);
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        HocVienDAO hocVienDAO = new HocVienDAOImpl();
        System.out.println(hocVienDAO.getList());
    }

    @Override
    public int createOrUpdate(HocVien hocVien) {
        try {
            // Connect to database
            Connection SqlConn = DBConnect.getConnection();
            
            // SQL Query
            String SqlQuery = "INSERT INTO hoc_vien(ma_hoc_vien, ho_ten, ngay_sinh, gioi_tinh, so_dien_thoai, dia_chi, tinh_trang) VALUES (?, ?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE ho_ten = VALUES(ho_ten), ngay_sinh = VALUES(ngay_sinh), gioi_tinh = VALUES(gioi_tinh), so_dien_thoai = VALUES(so_dien_thoai), dia_chi = VALUES(dia_chi), tinh_trang = VALUES(tinh_trang);";
//          ON DUPLICATE KEY UPDATE: Cú pháp này sẽ được kích hoạt nếu có một bản ghi trùng khóa chính (hoặc trùng khóa duy nhất) với dữ liệu đang được chèn.
//          ho_ten = VALUES(ho_ten): Cập nhật cột ho_ten với giá trị mới từ câu lệnh INSERT. Các cột khác (ngay_sinh, gioi_tinh, so_dien_thoai, dia_chi, tinh_trang) cũng được cập nhật tương tự.
            
            //  Create PreparedStatement
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            // Các giá trị từ 1 đến n sẽ tương ứng với các parameter ? trong SQLQuery
            pst.setInt(1, hocVien.getMaHocVien());
            pst.setString(2, hocVien.getHoTen());
            pst.setDate(3, new Date(hocVien.getNgaySinh().getTime()));
            pst.setBoolean(4, hocVien.isGioiTinh());
            pst.setString(5, hocVien.getSoDienThoai());
            pst.setString(6, hocVien.getDiaChi());
            pst.setBoolean(7, hocVien.isTinhTrang());
            
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
    public boolean detete(int maHocVien) {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "DELETE FROM hoc_vien WHERE ma_hoc_vien = ?";
        try {
            PreparedStatement pst = SqlConn.prepareStatement(SqlQuery);
            pst.setInt(1, maHocVien);
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
