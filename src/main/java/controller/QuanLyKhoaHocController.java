package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.HocVien;
import model.KhoaHoc;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
import utility.KhoaHocClassTableModel;
import view.KhoaHocJFrame;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 7, 2024
 */
public class QuanLyKhoaHocController { //đây là nơi điều khiển KhoaHocJpanel
    private JTextField jtxtSearch;
    private JButton jbtnAdd;
    private JButton jbtnDelete;
    private JButton jbtnPrint;
    private JTable table;
    private JPanel jpnView;
    
    private KhoaHocService khoaHocService = null;
    
    private String[] listColumn = {"Mã Khóa Học", "STT", "Tên Khóa Học", "Mô Tả", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Tình Trạng"};

    private TableRowSorter<TableModel> rowSorter = null;
    
    public QuanLyKhoaHocController(JTextField jtxtSearch, JButton jbtnAdd, JButton jbtnDelete, JButton jbtnPrint, JPanel jpnView) {
        this.jtxtSearch = jtxtSearch;
        this.jbtnAdd = jbtnAdd;
        this.jbtnDelete = jbtnDelete;
        this.jbtnPrint = jbtnPrint;
        this.jpnView = jpnView;
        
        this.khoaHocService = new KhoaHocServiceImpl();
    }
    
    public void setDataToTable(){
        List<KhoaHoc> listItem = khoaHocService.getList();
        
        DefaultTableModel model = new KhoaHocClassTableModel().setTableKhoaHoc(listItem, listColumn);
        
        table = new JTable(model);
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        
        jtxtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtxtSearch.getText();
                if (text.trim().length() == 0){ // nếu jtextfield trống thì giữ nguyên trạng thái, không hiển thị gì
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//              Nếu văn bản không trống, áp dụng bộ lọc để chỉ hiển thị các hàng khớp với văn bản tìm kiếm, không phân biệt chữ hoa chữ thường (RowFilter.regexFilter("(?i)" + text)).                      
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) { // giống như bên trên chỉ có điều là nó được gọi khi văn bản bị xóa khỏi jtextfield
                String text = jtxtSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        
        // Cài đặt hiển thị cho cột đầu tiên (Nó là cột mã học viên trong database nhưng ta không hiển thị, chỉ hiển thị số thứ tự ra thôi). Ẩn nó đi luôn
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Cài đặt hiển thị cho cột có chỉ số index là 1 (Cột STT).
        table.getColumnModel().getColumn(1).setMinWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        
        // Cài đặt hiển thị cho cột có chỉ số index là 3 (Cột Tên Khóa Học).
        table.getColumnModel().getColumn(3).setMinWidth(400);
        table.getColumnModel().getColumn(3).setMaxWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(400);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);

                    KhoaHoc khoaHoc = new KhoaHoc();
                    khoaHoc.setMaKhoaHoc((int) model.getValueAt(selectedRowIndex, 0));
                    //column 1 bỏ qua vì nó là STT
                    khoaHoc.setTenMonHoc(model.getValueAt(selectedRowIndex, 2) != null
                            ? model.getValueAt(selectedRowIndex, 2).toString() : ""); // Tránh NullPointer Exception
                    khoaHoc.setMoTa((String) model.getValueAt(selectedRowIndex, 3) != null
                            ? model.getValueAt(selectedRowIndex, 3).toString() : "");
                    khoaHoc.setNgayBatDau((Date) model.getValueAt(selectedRowIndex, 4));
                    khoaHoc.setNgayKetThuc((Date) model.getValueAt(selectedRowIndex, 5));
                    khoaHoc.setTinhTrang((boolean) model.getValueAt(selectedRowIndex, 6));

                    KhoaHocJFrame frame = new KhoaHocJFrame(khoaHoc, QuanLyKhoaHocController.this); // thử nghiệm (đoạn này thử nghiệm refeshTable)
                    frame.setTitle("Thông Tin Khóa Học");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
            
        });
        
        // design
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
        scrollPane.setPreferredSize(new Dimension(1350, 400));

        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scrollPane);
        jpnView.validate();
        jpnView.repaint();
    }
    
    public void setEvent(){
        jbtnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // set sự kiện khi nhấn nút thêm thì sẽ hiển thị ra HocVienJPanel để ta thêm học viên
                KhoaHocJFrame frame = new KhoaHocJFrame(new KhoaHoc(), QuanLyKhoaHocController.this);// Thử ngiệm refeshTable
                frame.setTitle("Thông Tin Khóa Học");
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
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
                if (table.getSelectedRow() != -1){
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                    int maKhoaHoc = (int) table.getModel().getValueAt(selectedRowIndex, 0);
                    
                    int dialogResult = JOptionPane.showConfirmDialog(null, 
                            "Bạn Có Chắc Chắn Muốn Xóa Khóa Học Này Không?", "Thông Báo", 
                            JOptionPane.YES_NO_OPTION);
                    
                    if (dialogResult == JOptionPane.YES_OPTION){
                        boolean success = khoaHocService.delete(maKhoaHoc);
                        
                        if (success){
                            JOptionPane.showMessageDialog(null, "Xóa Khóa Học Thành Công");
                            refeshTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không Thể Xóa Khóa Học Này Vì Có Bản Ghi Liên Quan Trong Bảng Khác");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Khóa Học Cần Xóa");
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
        
        // Đoạn này là print button
        jbtnPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("KHÓA HỌC");

                    XSSFRow row = null;
                    Cell cell = null;

                    row = sheet.createRow((short) 2); // Tạo hàng mới ở dòng thứ 2 trong worksheet
                    row.setHeight((short) 500);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue("STT");

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue("Tên Khóa Học");

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue("Mô Tả");

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue("Ngày Bắt Đầu");

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue("Ngày Kết Thúc");

                    List<KhoaHoc> listItem = khoaHocService.getList();

                    if (listItem != null) {
                        int s = listItem.size();

                        for (int i = 0; i < s; i++) {
                            KhoaHoc khoaHoc = listItem.get(i);

                            row = sheet.createRow((short) 4 + i); // Tạo một hàng mới trong sheet tại vị trí 4 + i. Ví dụ, nếu i là 0, hàng sẽ được tạo tại vị trí 4.
                            row.setHeight((short) 400);
                            row.createCell(0).setCellValue(i + 1); // Tạo một ô tại cột 0 của hàng và đặt giá trị cho ô là i + 1. Điều này đảm bảo số thứ tự bắt đầu từ 1.

                            row.createCell(1).setCellValue(khoaHoc.getTenMonHoc()); // Tạo một ô tại cột 1 của hàng và đặt giá trị cho ô là họ và tên của khóa học

                            row.createCell(2).setCellValue(khoaHoc.getMoTa());
                            
                            row.createCell(3).setCellValue(khoaHoc.getNgayBatDau().toString()); //Tạo 1 ô tại cột 2 của hàng và đặt giá trị là ngày bắt đầu
                            
                            row.createCell(4).setCellValue(khoaHoc.getNgayKetThuc().toString()); //Tạo 1 ô tại cột 2 của hàng và đặt giá trị là ngày kết thúc
                        }

                        // Sử dụng JFileChooser để chọn vị trí lưu file và đặt tên file
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Save Excel File");
                        fileChooser.setSelectedFile(new File("khoahoc.xlsx"));

                        int userSelection = fileChooser.showSaveDialog(null);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                                fileToSave = new File(fileToSave + ".xlsx");
                            }
                            try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                                workbook.write(fos);
                                JOptionPane.showMessageDialog(null, "Xuất File Thành Công. Lưu File Tại: " + fileToSave.getAbsolutePath());
                            }
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi Xuất File: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jbtnPrint.setBackground(new Color(0, 191, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jbtnPrint.setBackground(new Color(0, 178, 238));
            }
        }
        );
    }
    
    // Thử nghiệm refeshTable
    public void refeshTable(){
        setDataToTable();
    }
}
