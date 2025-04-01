package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import service.TaiKhoanService;
import service.TaiKhoanServiceImpl;
import model.TaiKhoan;
import view.MainJFrame;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 30, 2024
 */
public class TaiKhoanController {

    private JDialog dialog;
    private JButton btnSubmit;
    private JTextField jtfTenDangNhap;
    private JPasswordField jtfMatKhau;
    private JLabel jlbMsg;

    private TaiKhoanService taiKhoanService = null;

    public TaiKhoanController(JDialog dialog, JButton btnSubmit, JTextField jtfTenDangNhap, JPasswordField jtfMatKhau, JLabel jlbMsg) {
        this.dialog = dialog;
        this.btnSubmit = btnSubmit;
        this.jtfTenDangNhap = jtfTenDangNhap;
        this.jtfMatKhau = jtfMatKhau;
        this.jlbMsg = jlbMsg;

        taiKhoanService = new TaiKhoanServiceImpl();
    }

    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = jtfTenDangNhap.getText();
                String password = jtfMatKhau.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc!");
                } else {
                    TaiKhoan taiKhoan = taiKhoanService.login(username, password);
                    if (taiKhoan == null) {
                        jlbMsg.setText("Tên Đăng Nhập Hoặc Mật Khẩu Chưa Đúng!");
                    } else if (!taiKhoan.isTinh_trang()) {
                        jlbMsg.setText("Tài Khoản Đang Bị Tạm Khóa!");
                    } else {
                        dialog.dispose();
                        MainJFrame frame = new MainJFrame();
                        frame.setTitle("Quản Lý Học Viên");
                        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        frame.setVisible(true);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(100, 221, 23));
            }
        });
    }
}
