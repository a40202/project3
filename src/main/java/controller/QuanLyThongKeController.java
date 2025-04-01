package controller;

import bean.KhoaHocBean;
import bean.LopHocBean;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.Task;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import service.ThongKeService;
import service.ThongKeServiceImpl;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 29, 2024
 */
public class QuanLyThongKeController {
    private ThongKeService thongKeService = null;
    
    public QuanLyThongKeController(){
        thongKeService = new ThongKeServiceImpl();
    }
    
    public void setDataToChart1(JPanel jpnItem){
        List<LopHocBean> listItem = thongKeService.getListByLopHoc();
        if (listItem != null){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (LopHocBean item : listItem){
                dataset.addValue(item.getSoLuongHocVien(), "Học Viên", item.getNgayDangKy());
            }
            
            JFreeChart barChart = ChartFactory.createBarChart("BIỂU ĐỒ THỐNG KÊ SỐ LƯỢNG HỌC VIÊN ĐĂNG KÝ", 
                    "Thời Gian", "Số Lượng", dataset, PlotOrientation.VERTICAL, false, true, false);
            
            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));
            
            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }
    
    public void setDataToChart2(JPanel jpnItem){
        List<KhoaHocBean> listItem = thongKeService.getListByKhoaHoc();
        
//          Biểu đồ gantt (Biểu đồ ngang, dùng xem thời gian khóa học diễn ra)  
            TaskSeriesCollection ds = new TaskSeriesCollection();
            
//          Tạo biểu đồ gantt  
            JFreeChart ganttChart = ChartFactory.createGanttChart("BIỂU ĐỒ THEO DÕI TÌNH TRẠNG KHÓA HỌC", 
                    "Khóa Học", "Thời Gian", ds, true, false, false);
            
            TaskSeries taskSeries;
            Task task;
            
            if (listItem != null) {
            for (KhoaHocBean item : listItem){
                taskSeries = new TaskSeries(item.getTenKhoaHoc());
                task = new Task(item.getTenKhoaHoc(), new SimpleTimePeriod(item.getNgayBatDau(), item.getNgayKetThuc()));
                taskSeries.add(task);
                ds.add(taskSeries);
            }
            
            ChartPanel chartPanel = new ChartPanel(ganttChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));
            
            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }
}
