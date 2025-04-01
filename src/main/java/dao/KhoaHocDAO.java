package dao;
import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author tungu
 */
public interface KhoaHocDAO {
    public List<KhoaHoc> getList();
    
    public int createOrUpdate(KhoaHoc khoaHoc);
    
    public boolean detete(int maKhoaHoc);
}
