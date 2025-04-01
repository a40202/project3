package dao;
import model.TaiKhoan;
        
/**
 *
 * @author tungu
 */
public interface TaiKhoanDAO {
    public TaiKhoan login(String userName, String passWord);
}
