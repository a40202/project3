package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.TaiKhoan;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 30, 2024
 */
public class TaiKhoanDAOImpl implements TaiKhoanDAO{

    @Override
    public TaiKhoan login(String userName, String passWord) {
        Connection SqlConn = DBConnect.getConnection();
        String SqlQuery = "SELECT * FROM tai_khoan WHERE ten_dang_nhap LIKE ? AND mat_khau LIKE ?";
        
        TaiKhoan taiKhoan = null;
        
        try {
            PreparedStatement pst = (PreparedStatement) SqlConn.prepareStatement(SqlQuery);
//          Các chỉ số từ 1 đến n sẽ tương ứng với các dấu ? trong câu truy vấn SQL nhe  
            pst.setString(1, userName);
            pst.setString(2, passWord);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next() == true){
                taiKhoan = new TaiKhoan();
                taiKhoan.setMa_tai_khoan(rs.getInt("ma_tai_khoan"));
                taiKhoan.setTen_dang_nhap(rs.getString("ten_dang_nhap"));
                taiKhoan.setMat_khau(rs.getString("mat_khau"));
                taiKhoan.setTinh_trang(rs.getBoolean("tinh_trang"));
            }
            pst.close();
            rs.close();
            return taiKhoan;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
