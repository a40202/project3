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
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.HocVien;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import service.HocVienService;
import service.HocVienServiceImpl;
import utility.ClassTableModel;
import view.HocVienJFrame;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 17, 2024
 */
public class QuanLyHocVienController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
    private JTable table;
    private JButton jBtnPrint;
    private JButton btnDelete;
    
    private HocVienService hocVienService = null;

    private String[] listColumn = {"Mã Học Viên", "STT", "Họ Và Tên", "Ngày Sinh", "Giới Tính", "Số Điện Thoại", "Địa Chỉ", "Tình Trạng"};

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyHocVienController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch, JButton jBtnPrint, JButton btnDelete) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.jBtnPrint = jBtnPrint;
        this.btnDelete = btnDelete;
        
        this.hocVienService = new HocVienServiceImpl();
    }

    public void setDataToTable() {
        List<HocVien> listItem = hocVienService.getList();

        DefaultTableModel model = new ClassTableModel().setTableHocVien(listItem, listColumn);
        table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() { // sự kiện tìm kiếm, hiển thị data rows theo những gì nhập trong jtextfield
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) { // nếu jtextfield trống thì giữ nguyên trạng thái, không hiển thị gì
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//              Nếu văn bản không trống, áp dụng bộ lọc để chỉ hiển thị các hàng khớp với văn bản tìm kiếm, không phân biệt chữ hoa chữ thường (RowFilter.regexFilter("(?i)" + text)).      
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) { // giống như bên trên chỉ có điều là nó được gọi khi văn bản bị xóa khỏi jtextfield
                String text = jtfSearch.getText();
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

        // Cột họ và tên ok, giữ nguyên (index 2)
        
        // Cột ngày sinh thu nhỏ lại chút (index 3)
        table.getColumnModel().getColumn(3).setMinWidth(150);
        table.getColumnModel().getColumn(3).setMaxWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        // Cột giới tính thu nhỏ lại (index 4)
        table.getColumnModel().getColumn(4).setMinWidth(130);
        table.getColumnModel().getColumn(4).setMaxWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);
        
        // Cột std (index 5)
        table.getColumnModel().getColumn(5).setMinWidth(150);
        table.getColumnModel().getColumn(5).setMaxWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        
        // Cột Địa Chỉ OK
        
        // Cột tình trạng, thu nhỏ lại chút
        table.getColumnModel().getColumn(7).setMinWidth(100);
        table.getColumnModel().getColumn(7).setMaxWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);

                    HocVien hocVien = new HocVien();
                    hocVien.setMaHocVien((int) model.getValueAt(selectedRowIndex, 0));
                    //column 1 bỏ qua vì nó là STT
                    hocVien.setHoTen(model.getValueAt(selectedRowIndex, 2).toString());
                    hocVien.setNgaySinh((Date) model.getValueAt(selectedRowIndex, 3));
                    hocVien.setGioiTinh(model.getValueAt(selectedRowIndex, 4).toString().equalsIgnoreCase("Nam"));
                    hocVien.setSoDienThoai(model.getValueAt(selectedRowIndex, 5) != null
                            ? model.getValueAt(selectedRowIndex, 5).toString() : "");
                    hocVien.setDiaChi(model.getValueAt(selectedRowIndex, 6) != null
                            ? model.getValueAt(selectedRowIndex, 6).toString() : "");
                    hocVien.setTinhTrang((boolean) model.getValueAt(selectedRowIndex, 7));

                    HocVienJFrame frame = new HocVienJFrame(hocVien, QuanLyHocVienController.this); // thử nghiệm (đoạn này thử nghiệm refeshTable)
                    frame.setTitle("Thông Tin Học Viên");
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

    public void setEvent() {
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // set sự kiện khi nhấn nút thêm thì sẽ hiển thị ra HocVienJPanel để ta thêm học viên
                HocVienJFrame frame = new HocVienJFrame(new HocVien(), QuanLyHocVienController.this);// Thử ngiệm refeshTable
                frame.setTitle("Thông Tin Học Viên");
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 23));
            }
        });

        
        // Đoạn này là print button
        jBtnPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("HỌC VIÊN");

                    XSSFRow row = null;
                    Cell cell = null;

                    row = sheet.createRow((short) 2); // Tạo hàng mới ở dòng thứ 2 trong worksheet
                    row.setHeight((short) 500);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue("STT");

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue("Họ Và Tên");

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue("Ngày Sinh");

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue("Giới Tính");

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue("Số Điện Thoại");

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue("Địa Chỉ");

                    List<HocVien> listItem = hocVienService.getList();

                    if (listItem != null) {
                        int s = listItem.size();

                        for (int i = 0; i < s; i++) {
                            HocVien hocVien = listItem.get(i);

                            row = sheet.createRow((short) 4 + i); // Tạo một hàng mới trong sheet tại vị trí 4 + i. Ví dụ, nếu i là 0, hàng sẽ được tạo tại vị trí 4.
                            row.setHeight((short) 400);
                            row.createCell(0).setCellValue(i + 1); // Tạo một ô tại cột 0 của hàng và đặt giá trị cho ô là i + 1. Điều này đảm bảo số thứ tự bắt đầu từ 1.

                            row.createCell(1).setCellValue(hocVien.getHoTen()); // Tạo một ô tại cột 1 của hàng và đặt giá trị cho ô là họ và tên của học viên.

                            row.createCell(2).setCellValue(hocVien.getNgaySinh().toString()); //Tạo 1 ô tại cột 2 của hàng và đặt giá trị là ngày sinh của học viên

                            row.createCell(3).setCellValue(hocVien.isGioiTinh() ? "Male" : "Female");

                            row.createCell(4).setCellValue(hocVien.getSoDienThoai());

                            row.createCell(5).setCellValue(hocVien.getDiaChi());
                        }

                        // Sử dụng JFileChooser để chọn vị trí lưu file và đặt tên file
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Save Excel File");
                        fileChooser.setSelectedFile(new File("hocvien.xlsx"));

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
                jBtnPrint.setBackground(new Color(0, 191, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jBtnPrint.setBackground(new Color(0, 178, 238));
            }
        }
        );
        
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() != -1){
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                    int maHocVien = (int) table.getModel().getValueAt(selectedRowIndex, 0);
                    
                    int dialogResult = JOptionPane.showConfirmDialog(null, 
                            "Bạn Có Chắc Chắn Muốn Xóa Học Viên Này Không?", "Thông Báo", 
                            JOptionPane.YES_NO_OPTION);
                    
                    if (dialogResult == JOptionPane.YES_OPTION){
                        boolean success = hocVienService.delete(maHocVien);
                        
                        if (success){
                            JOptionPane.showMessageDialog(null, "Xóa Học Viên Thành Công");
                            refeshTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không Thể Xóa Học Viên Này Vì Có Bản Ghi Liên Quan Trong Bảng Khác");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Học Viên Cần Xóa");
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
    
    // Thử nghiệm refeshTable
    public void refeshTable(){
        setDataToTable();
    }
}
