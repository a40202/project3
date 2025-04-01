package service;
import bean.KhoaHocBean;
import bean.LopHocBean;
import java.util.List;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 28, 2024
 */
public interface ThongKeService {
    public List<LopHocBean> getListByLopHoc();
    
    public List<KhoaHocBean> getListByKhoaHoc();
}
