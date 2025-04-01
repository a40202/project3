package dao;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 15, 2024
 */
public class DBConnect {
// Connection method
    public static Connection getConnection(){
        Connection connection = null;
        try {
// Đăng ký MySQL với DriverManger để nó quản lý tất cả các phiên bản Driver của ta, tránh gặp lỗi (JDK 9 trở đi nó đã tự động quản lý rồi nhưng ta cứ làm theo quy trình để hiểu bài hơn nhe)
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        
// Chuỗi kết nối
        String url = "jdbc:mySQL://localhost:3306/quan_ly_hoc_vien";
        String userName = "root";
        String passWord = "";
        
// Tạo kết nối
        connection = DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
// Disconnection method
    public static void closeConnection(Connection connection){
        try {
            if (connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
