package controller;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.HocVien;
import service.HocVienService;
import service.HocVienServiceImpl;
import java.util.Date;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 18, 2024
 */
public class HocVienController {

    private JButton btnSubmit;
    private JButton btnDelete;
    private JTextField jtfMaHocVien;
    private JTextField jtfHoTen;
    private JDateChooser jdcNgaySinh;
    private JRadioButton jrdMale;
    private JRadioButton jrdFemale;
    private JTextField jtfSoDienThoai;
    private JTextArea jtaDiaChi;
    private JCheckBox jcbTinhTrang;
    private JLabel jlbMsg;

    private HocVien hocVien = null;

    private HocVienService hocVienService = null;
    private QuanLyHocVienController parentController; // Thử nghiệm
    
    public HocVienController(JButton btnSubmit, JButton btnDelete,JTextField jtfMaHocVien, JTextField jtfHoTen,
            JDateChooser jdcNgaySinh, JRadioButton jrdMale, JRadioButton jrdFemale, JTextField jtfSoDienThoai,
            JTextArea jtaDiaChi, JCheckBox jcbTinhTrang, JLabel jlbMsg, QuanLyHocVienController parentController) {// thử nghiệm refeshTable
        this.btnSubmit = btnSubmit;
        this.btnDelete = btnDelete;
        this.jtfMaHocVien = jtfMaHocVien;
        this.jtfHoTen = jtfHoTen;
        this.jdcNgaySinh = jdcNgaySinh;
        this.jrdMale = jrdMale;
        this.jrdFemale = jrdFemale;
        this.jtfSoDienThoai = jtfSoDienThoai;
        this.jtaDiaChi = jtaDiaChi;
        this.jcbTinhTrang = jcbTinhTrang;
        this.jlbMsg = jlbMsg;

        this.hocVienService = new HocVienServiceImpl();
        this.parentController = parentController; // thử nghiệm
    }

    public void setView(HocVien hocVien) {
        this.hocVien = hocVien;

        //set data
        jtfMaHocVien.setText("#" + hocVien.getMaHocVien());
        jtfHoTen.setText(hocVien.getHoTen());
        jdcNgaySinh.setDate(hocVien.getNgaySinh());
        if (hocVien.isGioiTinh() == true) {
            jrdMale.setSelected(true);
        } else {
            jrdFemale.setSelected(true);
        }

        jtfSoDienThoai.setText(hocVien.getSoDienThoai());
        jtaDiaChi.setText(hocVien.getDiaChi());
        jcbTinhTrang.setSelected(hocVien.isTinhTrang());

        //set event
        setEvent();
    }

    public void setEvent() {
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNull()) {
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc !");
                    } else {
                        hocVien.setHoTen(jtfHoTen.getText().trim());
                        hocVien.setNgaySinh(convertDateToDateSql(jdcNgaySinh.getDate()));
                        hocVien.setGioiTinh(jrdMale.isSelected());
                        hocVien.setSoDienThoai(jtfSoDienThoai.getText());
                        hocVien.setDiaChi(jtaDiaChi.getText());
                        hocVien.setTinhTrang(jcbTinhTrang.isSelected());

                        if (showDialog() == true) {
                            int lastID = hocVienService.createUpdate(hocVien);
                            if (lastID != 0) {
                                hocVien.setMaHocVien(hocVien.getMaHocVien());
                                jtfMaHocVien.setText("#" + lastID);
                                jlbMsg.setText("Cập Nhật Dữ Liệu Thành Công");
                                
                                parentController.refeshTable(); // thử nghiệm refeshTable
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
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(100, 221, 23));
            }
        }
        );
        
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int maHocVien = Integer.parseInt(jtfMaHocVien.getText().substring(1));
                    if (showDialogDelete()){
                        boolean success = hocVienService.delete(maHocVien);
                        if (success){
                            jlbMsg.setText("Xóa Học Viên Thành Công");
                        } else {
                            jlbMsg.setText("Không Thể Xóa Học Viên Này Vì Có Bản Ghi Liên Quan Trong Bảng Khác");
                        }
                    }
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
        return jtfHoTen.getText() != null && !jtfHoTen.getText().equalsIgnoreCase("");
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
