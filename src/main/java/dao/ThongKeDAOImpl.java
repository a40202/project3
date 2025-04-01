package dao;

import bean.KhoaHocBean;
import bean.LopHocBean;
import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 27, 2024
 */
public class ThongKeDAOImpl implements ThongKeDAO {

    @Override
    public List<LopHocBean> getListByLopHoc() {
//      Connect to database
        Connection SqlConn = DBConnect.getConnection();

//      SQLQuery
        String SqlQuery = "SELECT ngay_dang_ky, COUNT(*) AS so_luong FROM lop_hoc GROUP BY ngay_dang_ky";

//      Initiallize LopHocBean ArrayList
        List<LopHocBean> list = new ArrayList<>();
        try {
//      Create Prepared Statement
            PreparedStatement pst = (PreparedStatement) SqlConn.prepareStatement(SqlQuery);

//      Create Result Set
            ResultSet rs = pst.executeQuery();
            while (rs.next() == true) {
                LopHocBean lopHocBean = new LopHocBean();
                lopHocBean.setNgayDangKy(rs.getString("ngay_dang_ky"));
                lopHocBean.setSoLuongHocVien(rs.getInt("so_luong"));
                
                list.add(lopHocBean);
            }
            pst.close();
            SqlConn.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<KhoaHocBean> getListByKhoaHoc() {
//          Connect to database  
        Connection SqlConn = DBConnect.getConnection();

//          Sql Query  
        String SqlQuery = "SELECT ten_khoa_hoc, ngay_bat_dau, ngay_ket_thuc FROM khoa_hoc WHERE tinh_trang = TRUE ORDER BY ngay_bat_dau ASC;";

//          Create KhoaHocBean Array List  
        List<KhoaHocBean> list = new ArrayList<>();
        
        try {
//          Create Prepared Statement
            PreparedStatement pst = (PreparedStatement) SqlConn.prepareStatement(SqlQuery);

//          and execute query
            ResultSet rs = pst.executeQuery();

            while (rs.next() == true) {
                KhoaHocBean khoaHocBean = new KhoaHocBean();
                khoaHocBean.setTenKhoaHoc(rs.getString("ten_khoa_hoc"));
                khoaHocBean.setNgayBatDau(rs.getDate("ngay_bat_dau"));
                khoaHocBean.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
                
                list.add(khoaHocBean);
            }
            pst.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
