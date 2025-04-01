package dao;
import model.HocVien;
import java.util.List;
/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 15, 2024
 */
public interface HocVienDAO {
    public List<HocVien> getList();
    
    public int createOrUpdate(HocVien hocVien);
    
    public boolean detete(int maHocVien);
}
