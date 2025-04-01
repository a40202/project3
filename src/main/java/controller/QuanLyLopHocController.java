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
import javax.swing.JPanel;
import javax.swing.JTextField;
import service.LopHocService;
import service.LopHocServiceImpl;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
import model.LopHoc;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utility.LopHocClassTableModel;
import view.LopHocJFrame;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 6, 2024
 */
public class QuanLyLopHocController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnPrint;
    private JTextField txtSearch;
    private JTable table;

    private LopHocService lopHocService = null;

    private String[] listColumn = {"Mã Lớp Học", "STT", "Tên Lớp Học", "Mã Khóa Học", "Tên Khóa Học", "Mã Học Viên",
        "Tên Học Viên", "Ngày Đăng Ký", "Tình Trạng"};

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyLopHocController(JPanel jpnView, JButton btnAdd, JButton btnDelete, JButton btnPrint, JTextField txtSearch) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.btnDelete = btnDelete;
        this.btnPrint = btnPrint;
        this.txtSearch = txtSearch;

        this.lopHocService = new LopHocServiceImpl();
    }

    public void setDataToTable() {
        List<LopHoc> listItem = lopHocService.getList();

        DefaultTableModel tableModel = new LopHocClassTableModel().setTableLopHoc(listItem, listColumn);
        table = new JTable(tableModel);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtSearch.getText();
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

        // Cột mã lớp học, cột này ẩn đi (column 0)
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Cột STT (column 1)
        table.getColumnModel().getColumn(1).setMinWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);

        // Cột tên lớp học (column 2)
        table.getColumnModel().getColumn(2).setMinWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        // Cột mã khóa học, cột này thu nhỏ lại
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Cột tên khóa học, cột này cho rộng ra
        table.getColumnModel().getColumn(4).setMinWidth(230);
        table.getColumnModel().getColumn(4).setMaxWidth(230);
        table.getColumnModel().getColumn(4).setPreferredWidth(230);

        // Cột mã học viên, cột này thu nhỏ lại
        table.getColumnModel().getColumn(5).setMinWidth(100);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        // Tên học viên giữ nguyên (Cột 6)
        // Ngày đăng ký thu nhỏ lại
        table.getColumnModel().getColumn(7).setMinWidth(150);
        table.getColumnModel().getColumn(7).setMaxWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);

        // Tình trạng thu nhỏ lại nốt
        table.getColumnModel().getColumn(8).setMinWidth(100);
        table.getColumnModel().getColumn(8).setMaxWidth(100);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);

                    LopHoc lopHoc = new LopHoc();
                    // Bỏ qua column 1 vì nó là cột STT
                    lopHoc.setMaLopHoc((int) model.getValueAt(selectedRowIndex, 0));
                    lopHoc.setTenLopHoc(model.getValueAt(selectedRowIndex, 2).toString());

                    // Tạo đối tượng KhoaHoc và gán cho LopHoc
                    KhoaHoc khoaHoc = new KhoaHoc();
                    khoaHoc.setMaKhoaHoc((int) (model.getValueAt(selectedRowIndex, 3)));
                    khoaHoc.setTenMonHoc(model.getValueAt(selectedRowIndex, 4) != null
                            ? model.getValueAt(selectedRowIndex, 4).toString() : "");
                    lopHoc.setKhoaHoc(khoaHoc);

                    // Tạo đối tượng HocVien và gán cho LopHoc
                    HocVien hocVien = new HocVien();
                    hocVien.setMaHocVien((int) model.getValueAt(selectedRowIndex, 5));
                    hocVien.setHoTen(model.getValueAt(selectedRowIndex, 6) != null
                            ? model.getValueAt(selectedRowIndex, 6).toString() : "");
                    lopHoc.setHocVien(hocVien);

                    lopHoc.setNgayDangKy((Date) model.getValueAt(selectedRowIndex, 7));
                    lopHoc.setTinhTrang((Boolean) model.getValueAt(selectedRowIndex, 8));

                    LopHocJFrame frame = new LopHocJFrame(lopHoc, QuanLyLopHocController.this); // thử nghiệm (đoạn này thử nghiệm refeshTable)
                    frame.setTitle("Thông Tin Lớp Học");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }

        });

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
        scrollPane.setPreferredSize(new Dimension(1300, 400));

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
                // set sự kiện khi nhấn nút thêm thì sẽ hiển thị ra LopHocJPanel để ta thêm lớp học
                LopHocJFrame frame = new LopHocJFrame(new LopHoc(), QuanLyLopHocController.this);
                frame.setTitle("Thông Tin Lớp Học");
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

        // Delete button
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (table.getSelectedRow() != -1) {
                        int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                        int maLopHoc = (int) table.getModel().getValueAt(selectedRowIndex, 0);

                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "Bạn Có Chắc Chắn Muốn Xóa Lớp Học Này Không?", "Thông Báo",
                                JOptionPane.YES_NO_OPTION);

                        if (dialogResult == JOptionPane.YES_OPTION) {
                            boolean success = lopHocService.delete(maLopHoc);

                            if (success) {
                                JOptionPane.showMessageDialog(null, "Xóa Lớp Học Thành Công");
                                refeshTable();
                            } else {
                                JOptionPane.showMessageDialog(null, "Không Thể Xóa Lớp Học Này, Vui Lòng Thử Lại");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Lớp Học Cần Xóa");
                    }
                } catch (Exception ez) {
                    ez.printStackTrace();
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

        // Đoạn này là print button
        btnPrint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Danh sách lớp học");

                    XSSFRow headerRow = sheet.createRow(0);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        headerRow.createCell(i).setCellValue(table.getColumnName(i));
                    }

                    for (int row = 0; row < table.getRowCount(); row++) {
                        XSSFRow excelRow = sheet.createRow(row + 1);
                        for (int col = 0; col < table.getColumnCount(); col++) {
                            Object value = table.getValueAt(row, col);
                            if (value != null) {
                                excelRow.createCell(col).setCellValue(value.toString());
                            } else {
                                excelRow.createCell(col).setCellValue("");
                            }
                        }
                    }

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Lưu file Excel");
                    fileChooser.setSelectedFile(new File("DanhSachLopHoc.xlsx"));
                    int userSelection = fileChooser.showSaveDialog(null);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                            fileToSave = new File(fileToSave + ".xlsx");
                        }

                        try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                            workbook.write(fos);
                            JOptionPane.showMessageDialog(null, "Xuất Excel thành công. File được lưu tại: " + fileToSave.getAbsolutePath());
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnPrint.setBackground(new Color(0, 191, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPrint.setBackground(new Color(0, 178, 238));
            }
        });

    }

    // Thử nghiệm refeshTable
    public void refeshTable() {
        setDataToTable();
    }
}
