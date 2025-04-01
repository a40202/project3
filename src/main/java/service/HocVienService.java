package service;

import java.util.List;
import model.HocVien;

/**
 *
 * @author tungu
 */
public interface HocVienService {
    public List<HocVien> getList();
    
    public int createUpdate(HocVien hocVien);
    
    public boolean delete(int maHocVien);
}
