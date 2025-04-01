package service;
import dao.TaiKhoanDAOImpl;
import model.TaiKhoan;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 30, 2024
 */
public class TaiKhoanServiceImpl implements TaiKhoanService{
    private TaiKhoanDAOImpl taiKhoan = null;

    public TaiKhoanServiceImpl() {
        taiKhoan = new TaiKhoanDAOImpl();
    }
    
    @Override
    public TaiKhoan login(String userName, String passWord) {
        return taiKhoan.login(userName, passWord);
    }

}
