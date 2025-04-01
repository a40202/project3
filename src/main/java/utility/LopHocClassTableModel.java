package utility;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import model.LopHoc;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 6, 2024
 */
public class LopHocClassTableModel {
    public DefaultTableModel setTableLopHoc(List<LopHoc> listItem, String []listColumn){
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ngăn chặn chỉnh sửa trên table
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class; // MaLopHoc
                    case 1: return Integer.class; // STT
                    case 2: return String.class; // ten_lop_hoc
                    case 3: return String.class;  // MaKhoaHoc
                    case 4: return String.class;  // TenMonHoc
                    case 5: return Integer.class; // MaHocVien
                    case 6: return String.class;  // HoTen
                    case 7: return String.class;  // NgayDangKy
                    case 8: return Boolean.class; // TinhTrang
                    default: return String.class; // Mặc định là String để tránh lỗi ép kiểu từ int sang string khi ta nhập số vào trong txtSearch, java sẽ gặp khó khăn trong việc so sánh các giá trị int và string
                }
//                return columnIndex == 7 ? Boolean.class : String.class; // Nếu columnIndex == 7 thì hiển thị nó theo kiểu boolean (dạng checkbox) ngược lại hiển thị theo kiểu string
            }
        };
        
        // đặt tên cho các cột trong bảng dựa trên mảng listColum
        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length; // Lấy số lượng cột: columns lưu trữ số lượng cột trong bảng.
        Object []obj = null;
        int rows = listItem.size(); // Lấy số lượng hàng: rows lưu trữ số lượng đối tượng LopHoc trong danh sách listItem
         
        if (rows > 0){
            for (int i = 0; i < rows; i++){
                LopHoc lopHoc = listItem.get(i);
                obj = new Object[columns];
                obj[0] = lopHoc.getMaLopHoc();
                obj[1] = (i +1); // đoạn này để khi hiển thị lên trên bảng nó sẽ hiển thị số thứ tự, sang bên bảng ta sẽ ẩn đi cột mã LopHoc, thay vào đó ta hiển thị chỉ số index lên cho gọn
                obj[2] = lopHoc.getTenLopHoc(); // Tên lớp học
                obj[3] = lopHoc.getKhoaHoc().getMaKhoaHoc(); // Major ID
                obj[4] = lopHoc.getKhoaHoc().getTenMonHoc(); // Major name
                obj[5] = lopHoc.getHocVien().getMaHocVien(); // Student ID
                obj[6] = lopHoc.getHocVien().getHoTen(); // Student Name
                obj[7] = lopHoc.getNgayDangKy(); // Ngày đăng ký
                obj[8] = lopHoc.isTinhTrang(); // Tình trạng
                
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}