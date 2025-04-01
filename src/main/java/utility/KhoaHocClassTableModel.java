package utility;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 7, 2024
 */
public class KhoaHocClassTableModel {
    public DefaultTableModel setTableKhoaHoc(List<KhoaHoc> listItem, String []listColumn){
        DefaultTableModel dtm = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 6 ? Boolean.class : String.class; // cấu trúc if-else. Nếu columnIndex == 7 thì hiển thị nó theo kiểu boolean (dạng checkbox) ngược lại hiển thị theo kiểu string
        }
    };
        dtm.setColumnIdentifiers(listColumn); // đặt tên cho các cột trong bảng dựa trên mảng listColum
        
        int columns = listColumn.length; // Lấy số lượng cột: columns lưu trữ số lượng cột trong bảng.
        
        Object []obj = null; //Khởi tạo mảng obj: obj sẽ được sử dụng để tạm thời lưu trữ các giá trị của một hàng trước khi thêm vào DefaultTableModel.
        
        int rows = listItem.size(); // Lấy số lượng hàng: rows lưu trữ số lượng đối tượng KhoaHoc trong danh sách listItem
        
        if (rows > 0){
            // Kiểm tra danh sách có phần tử nào không: Nếu danh sách không rỗng, thực hiện vòng lặp để thêm dữ liệu vào bảng.
            for (int i=0; i < rows; i++){
                KhoaHoc khoaHoc = listItem.get(i);
                obj = new Object[columns];
                obj[0] = khoaHoc.getMaKhoaHoc();
                obj[1] = i + 1; // đoạn này để khi hiển thị lên trên bảng nó sẽ hiển thị số thứ tự, sang bên bảng ta sẽ ẩn đi cột mã học viên, thay vào đó ta hiển thị chỉ số index lên cho gọn
                obj[2] = khoaHoc.getTenMonHoc();
                obj[3] = khoaHoc.getMoTa();
                obj[4] = khoaHoc.getNgayBatDau();
                obj[5] = khoaHoc.getNgayKetThuc();
                obj[6] = khoaHoc.isTinhTrang();
                
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
