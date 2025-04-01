package service;

import java.util.List;
import model.HocVien;
import dao.HocVienDAO;
import dao.HocVienDAOImpl;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 17, 2024
 */
public class HocVienServiceImpl implements HocVienService{

    private HocVienDAO hocVienDAO = null;

    public HocVienServiceImpl() {
        hocVienDAO = new HocVienDAOImpl();
    }
            
    @Override
    public List<HocVien> getList() {
        return hocVienDAO.getList();
    }

    @Override
    public int createUpdate(HocVien hocVien) {
        return hocVienDAO.createOrUpdate(hocVien);
    }

    @Override
    public boolean delete(int maHocVien) {
        return hocVienDAO.detete(maHocVien);
    }
    
}
