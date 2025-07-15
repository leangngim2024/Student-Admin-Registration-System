
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class recycle_bin extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;

    public recycle_bin() {
        initComponents();
        MyConnect();
        showData();
    }
    
    public void MyConnect(){
        String url = "jdbc:mysql://localhost:3309/db02";
        String username = "root";
        String password = "";     
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(recycle_bin.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(recycle_bin.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    void showData(){
        DefaultTableModel model = (DefaultTableModel)Table_recycle.getModel();
        String insertAll = "SELECT * FROM `deleting_student`";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(insertAll);
            model.setRowCount(0);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("sex");
                java.util.Date date = rs.getDate("birth");
                String course = rs.getString("course");
                String time = rs.getString("time");
                double price = rs.getDouble("price");
                String day = rs.getString("day");
                String location = rs.getString("locationStudy");
                String picture = rs.getString("picture");
                String phoneNumber = rs.getString("phoneNumber");
                
                Object[] obj = {id, name, gender, date, course, time, price, day, phoneNumber, picture, location};
                model.addRow(obj);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(recycle_bin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        
    public void deleteforever(){
        DefaultTableModel model = (DefaultTableModel)Table_recycle.getModel();
        int selectRow= Table_recycle.getSelectedRow();
        
        if (selectRow == -1){
            JOptionPane.showMessageDialog(this, "Select a row from the table");
            return;  
        }
        int id = (int)model.getValueAt(selectRow, 0);
        String deletesql = "DELETE FROM deleting_student WHERE id = ?";
        
        try {
            pst = con.prepareStatement(deletesql);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
            
            model.removeRow(selectRow);
            JOptionPane.showMessageDialog(this, "Deleted successfully.");
            
        } catch (SQLException ex) {
            Logger.getLogger(recycle_bin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }}
    
    
    public void recoverData() {
        DefaultTableModel model = (DefaultTableModel)Table_recycle.getModel();
        int selectRow = Table_recycle.getSelectedRow();

        if (selectRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }

        int id = (int) model.getValueAt(selectRow, 0); 
        String insertSql = "INSERT INTO student_tb (id, name, sex, birth, course, time, price, day, phoneNumber, picture, locationStudy)" +
                           "SELECT id, name, sex, birth, course, time, price, day, phoneNumber, picture, locationStudy FROM deleting_student WHERE id = ?";

        String deleteSql = "DELETE FROM deleting_student WHERE id = ?";

        try {

        PreparedStatement insertStmt = con.prepareStatement(insertSql);
        insertStmt.setInt(1, id);
        insertStmt.executeUpdate();
        insertStmt.close();

        PreparedStatement deleteStmt = con.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        int rowsDeleted = deleteStmt.executeUpdate();
        deleteStmt.close();

        if (rowsDeleted > 0) {
            model.removeRow(selectRow);
            JOptionPane.showMessageDialog(null, "Recover successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Row found to delete.");
        }

    } catch (SQLException ex) {
        Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error deleting row: " + ex.getMessage());
    }
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_recycle = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        deleteBin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 255));

        Table_recycle.setBackground(new java.awt.Color(255, 204, 204));
        Table_recycle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Gender", "Date", "Course Name", "Time", "Price", "Day", "Phnon Number", "Picture", "Study Location"
            }
        ));
        jScrollPane1.setViewportView(Table_recycle);

        jButton1.setBackground(new java.awt.Color(255, 204, 255));
        jButton1.setText("Restore");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 204, 255));
        jButton2.setText("go back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        deleteBin.setBackground(new java.awt.Color(255, 204, 255));
        deleteBin.setText("Delete");
        deleteBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBinActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Recycle Bin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(386, 386, 386)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(deleteBin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jButton2)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBin)
                        .addGap(3, 3, 3)))
                .addComponent(jButton2)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBinActionPerformed
       deleteforever();
    }//GEN-LAST:event_deleteBinActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        recoverData();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        StudentRegister register = new StudentRegister();
        register.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(recycle_bin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(recycle_bin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(recycle_bin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(recycle_bin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new recycle_bin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_recycle;
    private javax.swing.JButton deleteBin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
