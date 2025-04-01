package service;
import java.util.List;
import model.LopHoc;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 6, 2024
 */
public interface LopHocService {
    public List<LopHoc> getList();
    
    public int createOrUpdate(LopHoc lopHoc);
    
    public boolean delete(int maLopHoc);
}
