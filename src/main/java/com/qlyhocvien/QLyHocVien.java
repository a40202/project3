package com.qlyhocvien;

import view.MainJFrame;
import java.sql.Connection;
import javax.swing.JDialog;
import javax.swing.JFrame;
import view.DangNhapJDialog;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 11, 2024
 */
public class QLyHocVien {

    public static void main(String[] args) {
//        new MainJFrame();
        DangNhapJDialog dialog = new DangNhapJDialog(null, true);
        dialog.setTitle("Đăng Nhập Hệ Thống");
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        try {
//          Test thử kết nối
            Connection SqlConn = dao.DBConnect.getConnection();
            System.out.println("Mission Success");

//          Thử ngắt kết nối
            dao.DBConnect.closeConnection(SqlConn);
            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
