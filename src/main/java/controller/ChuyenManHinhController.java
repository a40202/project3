package controller;

import bean.DanhMucBean;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.HocVienJPanel;
import view.KhoaHocJPanel;
import view.LopHocJPanel;
import view.ThongKeJPanel;
import view.TrangChuJPanel;

/**
 *
 * @author Van Tu Nguyen - 72TT11 - UTT - Adam Nguyen.
 * @date Jun 12, 2024
 */
public class ChuyenManHinhController {
    private JPanel root;
    private String kindSelected = "";
    
    private List<DanhMucBean> listItem = null;
    
    public ChuyenManHinhController(JPanel jpnRoot) {
        this.root = jpnRoot;
    }
    
    public void setView(JPanel jpnItem, JLabel jlbItem){
        kindSelected = "TrangChu";
        jpnItem.setBackground(new Color(96, 100, 191));
        jlbItem.setBackground(new Color(96, 100, 191));
        jlbItem.setForeground(Color.WHITE);
        
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChuJPanel()); // Khi bắt đầu chạy, chương trình sẽ nhảy vào jpanel này đầu tiên
        root.validate();
        root.repaint();
    }
    
    public void setEvent (List<DanhMucBean> listItem){
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }
    
    class LabelEvent implements MouseListener{
        private JPanel node;
        
        private String kind;
        private JPanel jpanelItem;
        private JLabel jlabelItem;
        
        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem){
            this.kind = kind;
            this.jlabelItem = jlbItem;
            this.jpanelItem = jpnItem;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            switch(kind){
                case "TrangChu" :
                    node = new TrangChuJPanel();
                    break;
                case "HocVien" :
                    node = new HocVienJPanel();
                    break;
                case "KhoaHoc" :
                    node = new KhoaHocJPanel();
                    break;
                // More ...    
                case "ThongKe" :
                    node = new ThongKeJPanel();
                    break;
                case "LopHoc" :
                    node = new LopHocJPanel();
                    break;
                default :
                    node = new TrangChuJPanel();
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpanelItem.setBackground(new Color(96, 100, 191));
            jlabelItem.setBackground(new Color(96, 100, 191));
            jlabelItem.setForeground(Color.WHITE);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpanelItem.setBackground(new Color(96, 100, 191));
            jlabelItem.setBackground(new Color(96, 100, 191));
            jlabelItem.setForeground(Color.WHITE);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)){
                jpanelItem.setBackground(new Color(76, 175, 80));
                jlabelItem.setBackground(new Color(76, 175, 80));
                jlabelItem.setForeground(Color.WHITE);
            }
        }
    }
    private void setChangeBackground(String kind){
        for (DanhMucBean item : listItem){
            if(item.getKind().equalsIgnoreCase(kind)){
                item.getJpn().setBackground(new Color(96, 100, 191));
                item.getJlb().setBackground(new Color(96, 100, 191));
            } else {
                item.getJpn().setBackground(new Color(76, 175, 80));
                item.getJlb().setBackground(new Color(76, 175, 80));
            }
        }
    }
}
