package dao;
import java.util.List;
import model.LopHoc;

/**
 *
 * @author tunguyen
 */
public interface LopHocDAO {
    public List<LopHoc> getList();
    
    public int createOrUpdate(LopHoc lopHoc);
    
    public boolean detete(int maLopHoc);
}
