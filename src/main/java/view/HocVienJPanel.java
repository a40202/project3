package view;

import controller.QuanLyHocVienController;

/**
 *
 * @author tungu
 */
public class HocVienJPanel extends javax.swing.JPanel {
    public HocVienJPanel() {
        initComponents();
        
        QuanLyHocVienController controller = new QuanLyHocVienController(jPnView, jBtnAdd, txtSearch, jBtnPrint, btnDelete);
        controller.setDataToTable();
        controller.setEvent();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnRoot = new javax.swing.JPanel();
        jBtnAdd = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jPnView = new javax.swing.JPanel();
        jBtnPrint = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        jPnRoot.setBackground(new java.awt.Color(204, 255, 153));

        jBtnAdd.setBackground(new java.awt.Color(76, 175, 80));
        jBtnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jBtnAdd.setForeground(new java.awt.Color(255, 255, 255));
        jBtnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/btn_save.png"))); // NOI18N
        jBtnAdd.setText("+ Thêm Mới");
        jBtnAdd.setBorder(null);

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPnViewLayout = new javax.swing.GroupLayout(jPnView);
        jPnView.setLayout(jPnViewLayout);
        jPnViewLayout.setHorizontalGroup(
            jPnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPnViewLayout.setVerticalGroup(
            jPnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
        );

        jBtnPrint.setBackground(new java.awt.Color(0, 154, 205));
        jBtnPrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jBtnPrint.setForeground(new java.awt.Color(255, 255, 255));
        jBtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printer.png"))); // NOI18N
        jBtnPrint.setText("Xuất Báo Cáo");
        jBtnPrint.setBorder(null);

        btnDelete.setBackground(new java.awt.Color(240, 84, 84));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btnDelete.setText("Xóa Học Viên");
        btnDelete.setBorder(null);

        javax.swing.GroupLayout jPnRootLayout = new javax.swing.GroupLayout(jPnRoot);
        jPnRoot.setLayout(jPnRootLayout);
        jPnRootLayout.setHorizontalGroup(
            jPnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPnRootLayout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                        .addComponent(jBtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPnRootLayout.setVerticalGroup(
            jPnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton jBtnAdd;
    private javax.swing.JButton jBtnPrint;
    private javax.swing.JPanel jPnRoot;
    private javax.swing.JPanel jPnView;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
