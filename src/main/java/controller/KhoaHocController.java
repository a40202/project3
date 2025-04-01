package controller;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.KhoaHoc;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 7, 2024
 */
public class KhoaHocController {
    private JButton jbtnAdd;
    private JButton jbtnDelete;
    private JLabel jlbMsg;
    private JTextField jtxtmaKhoaHoc;
    private JTextField jtxttenKhoaHoc;
    private JTextArea jtaMoTa;
    private JDateChooser jdcNgayBatDau;
    private JDateChooser jdcNgayKetThuc;
    private JCheckBox jcbTinhTrang;

    private KhoaHoc khoaHoc = null;
    private KhoaHocService khoaHocService = null;
    
    public KhoaHocController(JButton jbtnAdd, JButton jbtnDelete, JLabel jlbMsg, JTextField jtxtmaKhoaHoc, JTextField jtxttenKhoaHoc, JTextArea jtaMoTa, JDateChooser jdcNgayBatDau, JDateChooser jdcNgayKetThuc, JCheckBox jcbTinhTrang) {
        this.jbtnAdd = jbtnAdd;
        this.jbtnDelete = jbtnDelete;
        this.jlbMsg = jlbMsg;
        this.jtxtmaKhoaHoc = jtxtmaKhoaHoc;
        this.jtxttenKhoaHoc = jtxttenKhoaHoc;
        this.jtaMoTa = jtaMoTa;
        this.jdcNgayBatDau = jdcNgayBatDau;
        this.jdcNgayKetThuc = jdcNgayKetThuc;
        this.jcbTinhTrang = jcbTinhTrang;
        
        khoaHocService = new KhoaHocServiceImpl();
    }
    
    public void setView(KhoaHoc khoaHoc){
        this.khoaHoc = new KhoaHoc();
        
        jtxtmaKhoaHoc.setText("# " + khoaHoc.getMaKhoaHoc());
        jtxttenKhoaHoc.setText(khoaHoc.getTenMonHoc());
        jtaMoTa.setText(khoaHoc.getMoTa());
        jdcNgayBatDau.setDate(khoaHoc.getNgayBatDau());
        jdcNgayKetThuc.setDate(khoaHoc.getNgayKetThuc());
        jcbTinhTrang.setSelected(khoaHoc.isTinhTrang());
        
        //set event
        setEvent();
    }
    
    public void setEvent(){
        jbtnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNull()){
                        jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc !");
                    } else {
                        khoaHoc.setTenMonHoc(jtxttenKhoaHoc.getText().trim());
                        khoaHoc.setMoTa(jtaMoTa.getText());
                        khoaHoc.setNgayBatDau(convertDateToDateSql(jdcNgayBatDau.getDate()));
                        khoaHoc.setNgayKetThuc(convertDateToDateSql(jdcNgayKetThuc.getDate()));
                        khoaHoc.setTinhTrang(jcbTinhTrang.isSelected());
                        
                        if (showDialog()){
                            int lastID = khoaHocService.createOrUpdate(khoaHoc);
                            if (lastID != 0) {
                                khoaHoc.setMaKhoaHoc(khoaHoc.getMaKhoaHoc());
                                jtxtmaKhoaHoc.setText("# " + lastID);
                                jlbMsg.setText("Cập Nhật Dữ Liệu Thành Công");
                                
//                                parentController.refeshTable(); // thử nghiệm refeshTable
                            } else {
                                jlbMsg.setText("Có lỗi xảy ra, vui lòng thử lại!");
                            }
                        }
                    }
                } catch (Exception ez) {
                    jlbMsg.setText(ez.toString());
                    ez.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jbtnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jbtnAdd.setBackground(new Color(100, 221, 23));
            }
        });
        
        jbtnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int maKhoaHoc = Integer.parseInt(jtxtmaKhoaHoc.getText().substring(2));
// Chuyển đổi chuỗi thành số nguyên, đặt vị trí index tại 2 là vì ở vị trí 0 ta đang đặt dấu thăng #, vị trí 1 đang để dấu khoảng trắng
                    if (showDialogDelete()){
                        boolean success = khoaHocService.delete(maKhoaHoc);
                        if (success){
                            jlbMsg.setText("Xóa Khóa Học Thành Công");
                        } else {
                            jlbMsg.setText("Không Thể Xóa Khóa Học Này Vì Có Bản Ghi Liên Quan Trong Bảng Khác");
                        }
                    }
                } catch (Exception ex) {
                    jlbMsg.setText(ex.toString());
                    ex.printStackTrace();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                jbtnDelete.setBackground(new Color(255, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jbtnDelete.setBackground(new Color(240, 84, 84));
            }
        });
    }
    
    private boolean checkNull() {
        return jtxttenKhoaHoc.getText() != null && !jtxttenKhoaHoc.getText().equalsIgnoreCase("");
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
