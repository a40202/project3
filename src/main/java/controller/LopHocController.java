package controller;

import model.LopHoc;
import com.toedter.calendar.JDateChooser;
import dao.DBConnect;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import model.HocVien;
import model.KhoaHoc;
import service.LopHocService;
import service.LopHocServiceImpl;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 6, 2024
 */
public class LopHocController {

    private JButton btnAdd;
    private JButton btnDelete;
    private JTextArea txtTenLopHoc;
    private JTextField txtMaLopHoc;
    private JDateChooser jdcNgayDangKy;
    private JComboBox<String> jcbbMaKhoaHoc;
    private JComboBox<String> jcbbMaHocVien;
    private JCheckBox jcbxTinhTrang;
    private JLabel jlbMsg;

    private LopHoc lopHoc = null;
    private LopHocService lopHocService = null;

    private QuanLyLopHocController parentController; // Thử nghiệm

    public LopHocController(JButton btnAdd, JButton btnDelete, JTextArea txtTenLopHoc, JTextField txtMaLopHoc, JDateChooser jdcNgayDangKy,
            JComboBox<String> jcbbMaKhoaHoc, JComboBox<String> jcbbMaHocVien, JCheckBox jcbxTinhTrang, JLabel jlbMsg, QuanLyLopHocController parentController) { // thử nghiệm refeshTable
        this.btnAdd = btnAdd;
        this.btnDelete = btnDelete;
        this.txtTenLopHoc = txtTenLopHoc;
        this.txtMaLopHoc = txtMaLopHoc;
        this.jdcNgayDangKy = jdcNgayDangKy;
        this.jcbbMaKhoaHoc = jcbbMaKhoaHoc;
        this.jcbbMaHocVien = jcbbMaHocVien;
        this.jcbxTinhTrang = jcbxTinhTrang;
        this.jlbMsg = jlbMsg;

        this.lopHocService = new LopHocServiceImpl();
        this.parentController = parentController; // Thử nghiệm
        // Load dữ liệu vào các combobox
        loadDataToComboBoxes();
    }

    public void setView(LopHoc lopHoc) {
        this.lopHoc = lopHoc;

        // set data
        txtMaLopHoc.setText("# " + lopHoc.getMaLopHoc());
        txtTenLopHoc.setText(lopHoc.getTenLopHoc());
        jdcNgayDangKy.setDate(lopHoc.getNgayDangKy());
        jcbxTinhTrang.setSelected(lopHoc.isTinhTrang());

        // Kiểm tra và thiết lập giá trị cho JComboBox jcbbMaKhoaHoc
        if (lopHoc.getKhoaHoc() != null) {
            jcbbMaKhoaHoc.setSelectedItem(lopHoc.getKhoaHoc().getMaKhoaHoc() + " - " + lopHoc.getKhoaHoc().getTenMonHoc());
        } else {
            jcbbMaKhoaHoc.setSelectedIndex(-1); // Chọn index -1 hoặc làm một xử lý phù hợp khác
        }

        // Kiểm tra và thiết lập giá trị cho JComboBox jcbbMaHocVien
        if (lopHoc.getHocVien() != null) {
            jcbbMaHocVien.setSelectedItem(lopHoc.getHocVien().getMaHocVien() + " - " + lopHoc.getHocVien().getHoTen());
        } else {
            jcbbMaHocVien.setSelectedIndex(-1); // Chọn index -1 hoặc làm một xử lý phù hợp khác
        }

//        // set event
        setEvent();
    }

    private void loadDataToComboBoxes() {
        List<String> khoaHocList = getKhoaHocList();
        for (String khoaHoc : khoaHocList) {
            System.out.println("Loading KhoaHoc: " + khoaHoc); // Ghi log để kiểm tra
            jcbbMaKhoaHoc.addItem(khoaHoc);
        }

        List<String> hocVienList = getHocVienList();
        for (String hocVien : hocVienList) {
            System.out.println("Loading HocVien: " + hocVien); // Ghi log để kiểm tra
            jcbbMaHocVien.addItem(hocVien);
        }
    }

    private List<String> getKhoaHocList() {
        List<String> list = new ArrayList<>();
        String query = "SELECT ma_khoa_hoc, ten_khoa_hoc FROM khoa_hoc";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DBConnect.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int maKhoaHoc = rs.getInt("ma_khoa_hoc");
                String tenKhoaHoc = rs.getString("ten_khoa_hoc");
                list.add(maKhoaHoc + " - " + tenKhoaHoc);
            }

            st.close();
            rs.close();
            DBConnect.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getHocVienList() {
        List<String> list = new ArrayList<>();
        String query = "SELECT ma_hoc_vien, ho_ten FROM hoc_vien";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DBConnect.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                int maHocVien = rs.getInt("ma_hoc_vien");
                String hoTen = rs.getString("ho_ten");

                list.add(maHocVien + " - " + hoTen);
            }

            st.close();
            rs.close();
            DBConnect.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setEvent() {
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNull()) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc !");
                    } else {
                        // Kiểm tra và khởi tạo KhoaHoc nếu nó là null để tránh NullPointerException
                        if (lopHoc.getKhoaHoc() == null) {
                            lopHoc.setKhoaHoc(new KhoaHoc());
                        }

                        // Kiểm tra và khởi tạo HocVien nếu nó là null để tránh NullPointerException
                        if (lopHoc.getHocVien() == null) {
                            lopHoc.setHocVien(new HocVien());
                        }

                        lopHoc.setTenLopHoc(txtTenLopHoc.getText().trim());
                        lopHoc.setNgayDangKy(convertDateToDateSql(jdcNgayDangKy.getDate()));
                        lopHoc.setTinhTrang(jcbxTinhTrang.isSelected());

                        // Lấy giá trị từ JComboBox MaKhoaHoc
                        String selectedKhoaHoc = (String) jcbbMaKhoaHoc.getSelectedItem();
                        int maKhoaHoc = Integer.parseInt(selectedKhoaHoc.split(" - ")[0]); // Lấy mã từ chuỗi "ma - ten"
                        String tenKhoaHoc = selectedKhoaHoc.split(" - ")[1]; // Lấy tên từ chuỗi "ma - ten"

                        // Thiết lập cho đối tượng KhoaHoc của LopHoc
                        lopHoc.getKhoaHoc().setMaKhoaHoc(maKhoaHoc);
                        lopHoc.getKhoaHoc().setTenMonHoc(tenKhoaHoc);

                        // Lấy giá trị từ JComboBox MaHocVien
                        String selectedHocVien = (String) jcbbMaHocVien.getSelectedItem();
                        if (selectedHocVien != null && !selectedHocVien.isEmpty()) {
                            int maHocVien = Integer.parseInt(selectedHocVien.split(" - ")[0]); // Lấy mã từ chuỗi "ma - ten"
                            String tenHocVien = selectedHocVien.split(" - ")[1]; // Lấy tên từ chuỗi "ma - ten"

                            // Thiết lập cho đối tượng HocVien của LopHoc
                            lopHoc.getHocVien().setMaHocVien(maHocVien);
                            lopHoc.getHocVien().setHoTen(tenHocVien);
                        } else {
                            jlbMsg.setText("Vui lòng chọn Học Viên hợp lệ!");
                        }

                        // Ghi log để kiểm tra giá trị
                        System.out.println("Selected maHocVien: " + lopHoc.getHocVien().getMaHocVien());

                        if (showDialog() == true) {
                            int lastID = lopHocService.createOrUpdate(lopHoc);
                            if (lastID != 0) {
                                lopHoc.setMaLopHoc(lopHoc.getMaLopHoc());
                                txtMaLopHoc.setText("# " + lastID);
                                jlbMsg.setText("Cập Nhật Dữ Liệu Thành Công");

                                // Ở đây sẽ gọi phương thức refresh bảng
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        }
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e
            ) {
                btnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 23));
            }
        });

        // Delete button
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    String maLopHocStr = txtMaLopHoc.getText().trim(); // Lấy chuỗi và loại bỏ khoảng trắng
                    int maLopHoc = Integer.parseInt(maLopHocStr.substring(2)); 
// Chuyển đổi chuỗi thành số nguyên, đặt vị trí index tại 2 là vì ở vị trí 0 ta đang đặt dấu thăng #, vị trí 1 đang để dấu khoảng trắng

                    if (showDialogDelete()) {
                        boolean success = lopHocService.delete(maLopHoc);
                        if (success) {
                            jlbMsg.setText("Xóa Lớp Học Thành Công");
                        } else {
                            jlbMsg.setText("Xóa Không Thành Công, Vui Lòng Thử Lại");
                        }
                    }
                } catch (NumberFormatException ex) {
                    jlbMsg.setText("Lỗi: Không thể chuyển đổi chuỗi thành số nguyên.");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnDelete.setBackground(new Color(255, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDelete.setBackground(new Color(240, 84, 84));
            }
        });
    }

    private boolean checkNull() {
        return txtTenLopHoc.getText() != null && !txtTenLopHoc.getText().equalsIgnoreCase("");
//      JTextField khác null và nó không rỗng
    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn Có Muốn Cập Nhật Dữ Liệu Này Không ?", "Thông Báo",
                JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    private boolean showDialogDelete() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Bạn Có Muốn Xóa Dữ Liệu Này Không ?", "Thông Báo",
                JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    public java.sql.Date convertDateToDateSql(Date date) {
        return new java.sql.Date(date.getTime());
    }
}
