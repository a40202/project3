package service;
import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author tungu
 */
public interface KhoaHocService {
    public List<KhoaHoc> getList();
    
    public int createOrUpdate(KhoaHoc khoaHoc);
    
    public boolean delete(int maKhoaHoc);
}
