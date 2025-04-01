package service;

import java.util.List;
import model.LopHoc;
import dao.LopHocDAO;
import dao.LopHocDAOImpl;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 6, 2024
 */
public class LopHocServiceImpl implements LopHocService{
    private LopHocDAO lopHocDAO = null;

    public LopHocServiceImpl() {
        lopHocDAO = new LopHocDAOImpl();
    }
    
    @Override
    public List<LopHoc> getList() {
        return lopHocDAO.getList();
    }

    @Override
    public int createOrUpdate(LopHoc lopHoc) {
        return lopHocDAO.createOrUpdate(lopHoc);
    }

    @Override
    public boolean delete(int maLopHoc) {
        return lopHocDAO.detete(maLopHoc);
    }
    
}
