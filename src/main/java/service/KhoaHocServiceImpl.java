package service;

import dao.KhoaHocDAO;
import dao.KhoaHocDAOImpl;
import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jul 7, 2024
 */
public class KhoaHocServiceImpl implements KhoaHocService{

    private KhoaHocDAO khoaHocDAO = null;

    public KhoaHocServiceImpl() {
        khoaHocDAO = new KhoaHocDAOImpl();
    }
    
    @Override
    public List<KhoaHoc> getList() {
        return khoaHocDAO.getList();
    }

    @Override
    public int createOrUpdate(KhoaHoc khoaHoc) {
        return this.khoaHocDAO.createOrUpdate(khoaHoc);
    }

    @Override
    public boolean delete(int maKhoaHoc) {
        return khoaHocDAO.detete(maKhoaHoc);
    }
    
}
