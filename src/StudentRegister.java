
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class StudentRegister extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;
    
    public StudentRegister() {
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
                Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    void clear(){
        nametxt.setText("");
        buttonGroup1.clearSelection();
        coursetxt.setSelectedIndex(0);
        timetxt.setSelectedIndex(0);
        montxt.setSelected(false);
        sattxt.setSelected(false);
        pricetxt.setText("");
        locationtxt.setSelectedIndex(0);
        numbertxt.setText("");
        picturetxt.setText("");
        datetxt.setDate(null);
        BoxShow.setIcon(null);
    }
    
    void showData(){
        DefaultTableModel model = (DefaultTableModel)tabletxt.getModel();
        String insertAll = "SELECT * FROM `student_tb`";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(insertAll);
            model.setRowCount(0);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("sex");
                Date date = rs.getDate("birth");
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
            Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateData(){
        int selectRow = tabletxt.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabletxt.getModel();
        
        if(selectRow == -1){
            JOptionPane.showMessageDialog(this, "Please select a row");
        }
        int id = (int) model.getValueAt(selectRow, 0);
        
        String name = nametxt.getText();
        String gender;

        if (maletxt.isSelected()){
            gender = "Male";
        }else{
            gender = "Female";
        }
        Date birth = (Date)datetxt.getDate();
        java.sql.Date sqlDate = new java.sql.Date(birth.getTime());
        String course = coursetxt.getSelectedItem().toString();
        String number = numbertxt.getText();
        String picture = picturetxt.getText();
        double price = Double.parseDouble(pricetxt.getText());
        String day;
        String location = locationtxt.getSelectedItem().toString();
        if (montxt.isSelected() && sattxt.isSelected()){
            day = "Mon-Thu & Sat-Sun";
            
        }else if (sattxt.isSelected()){
            day = "Sat-Sun";
            
        }else if (montxt.isSelected()){
            day = "Mon-Thu";
        }
        else{
            day = "";
        }
        String time = timetxt.getSelectedItem().toString();
        
        String sql = "UPDATE student_tb SET name = ?, sex = ?, birth = ?, course = ?, time = ?, price = ?, day = ?, phoneNumber = ?, picture = ?, locationStudy = ? WHERE id = ?";

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, gender);
            pst.setDate(3, sqlDate);
            pst.setString(4, course);
            pst.setString(5, time);
            pst.setDouble(6, price);
            pst.setString(7, day);
            pst.setString(8, number);
            pst.setString(9, picture);
            pst.setString(10, location);
            pst.setInt(11, id);
            
            int rowUpdate = pst.executeUpdate();
            if(rowUpdate > 0){
                JOptionPane.showMessageDialog(this, "Updated Successfully.");
            }else{
                JOptionPane.showMessageDialog(this, "Update Failed");
            }   
        } catch (SQLException ex) {
            Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
        } 
    }
    
    public void mouseClick(java.awt.event.MouseEvent evt){
        DefaultTableModel model = (DefaultTableModel)tabletxt.getModel();
        int selectRow = tabletxt.getSelectedRow();
      
        String name = model.getValueAt(selectRow, 1).toString();
        String gender = model.getValueAt(selectRow, 2).toString();
        Date date = (Date)model.getValueAt(selectRow, 3);
        String course = model.getValueAt(selectRow, 4).toString();
        String time = model.getValueAt(selectRow, 5).toString();
        String price = model.getValueAt(selectRow, 6).toString();
        String day = model.getValueAt(selectRow, 7).toString();
        String number = model.getValueAt(selectRow, 8).toString();
        String picture = model.getValueAt(selectRow, 9).toString();
        String place = model.getValueAt(selectRow, 10).toString();
        
        clear();
        nametxt.setText(name);
        if (gender.equals("Male")){
            maletxt.setSelected(true);
        }else{
            femaletxt.setSelected(true);
        }
        coursetxt.setSelectedItem(course);
        pricetxt.setText(price);
        locationtxt.setSelectedItem(place);
        switch (day) {
            case "Mon-Thu" -> montxt.setSelected(true);
            case "Sat-Sun" -> sattxt.setSelected(true); 
            default -> {
                montxt.setSelected(false);
                sattxt.setSelected(false);
            }
        }
        datetxt.setDate(date);
        timetxt.setSelectedItem(time);
        numbertxt.setText(number);
        picturetxt.setText(picture);
        BoxShow.setPreferredSize(new Dimension(150, 150));
        ImageIcon myimage = new ImageIcon(picture);
        Image images = myimage.getImage().getScaledInstance(BoxShow.getWidth(),BoxShow.getHeight(), Image.SCALE_SMOOTH);
        BoxShow.setIcon(new ImageIcon(images));
    }
    
    void showImage(){
        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(this);
        File file = jf.getSelectedFile();
        picturetxt.setText(file.toString());
        String fileString = file.getAbsolutePath();
        
        ImageIcon myimage = new ImageIcon(fileString);
        Image image = myimage.getImage().getScaledInstance(BoxShow.getWidth(),BoxShow.getHeight(), Image.SCALE_SMOOTH);
        BoxShow.setIcon(new ImageIcon(image));
    }
    
    void insertStudent(){
        String insertQuery = "INSERT INTO student_tb (name, sex, birth, course, time, price, day, locationStudy, picture, phoneNumber) VALUES(?,?,?,?,?,?,?,?,?,?)";
        String name = nametxt.getText();
        String gender;
        if (maletxt.isSelected()){
            gender = "Male";
        }
        else{
            gender = "Female";
        }
        Date selectedDate = datetxt.getDate();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
        String course = coursetxt.getSelectedItem().toString();
        String time = timetxt.getSelectedItem().toString();
        double price = Double.parseDouble(pricetxt.getText());
        String day;
        if (montxt.isSelected()){
            day  = "Mon-Thu";
        }
        else if (sattxt.isSelected()){ 
            day = "Sat-Sun";}
        else day = " ";
        
        String location = locationtxt.getSelectedItem().toString();
        String picture = picturetxt.getText();
        String phone = numbertxt.getText();
        
            try {
                pst = con.prepareStatement(insertQuery);
                pst.setString(1, name);
                pst.setString(2, gender);
                pst.setDate(3, sqlDate);
                pst.setString(4, course);
                pst.setString(5, time);
                pst.setDouble(6, price);
                pst.setString(7, day);
                pst.setString(8, location);
                pst.setString(9, picture);
                pst.setString(10, phone);
                int check = pst.executeUpdate();
                System.out.println("Check = "+check);
                if(check > 0){
                    JOptionPane.showMessageDialog(this, "Student added");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Error adding ");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            }  
    }
    
    void search(){
        DefaultTableModel model = (DefaultTableModel)tabletxt.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        String searchText = researchtxt.getText().toLowerCase();

        tabletxt.setRowSorter(sorter);
        if (searchText.trim().length() == 0){
            sorter.setRowFilter(null);}
        else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)"+searchText));
        }    
    }
    
    public void deleteFromDatabase() {
        DefaultTableModel model = (DefaultTableModel) tabletxt.getModel();
        int selectRow = tabletxt.getSelectedRow();

        if (selectRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
            return;
        }
        int id = (int) model.getValueAt(selectRow, 0); 
        String insertSql = "INSERT INTO deleting_student ( id, name, sex, birth, course, time, price, day, phoneNumber, picture, locationStudy) " +
                           "SELECT id, name, sex, birth, course, time, price, day, phoneNumber, picture, locationStudy FROM student_tb WHERE id = ?";
        String deleteSql = "DELETE FROM student_tb WHERE id = ?";

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
                JOptionPane.showMessageDialog(null, "Deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Row found to delete.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentRegister.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error deleting row: " + ex.getMessage());
        }
    }

    void Exit(){
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if (confirm == YES_OPTION){
            System.exit(0);
        }      
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        datetxt = new com.toedter.calendar.JDateChooser();
        nametxt = new javax.swing.JTextField();
        researchtxt = new javax.swing.JTextField();
        pricetxt = new javax.swing.JTextField();
        picturetxt = new javax.swing.JTextField();
        numbertxt = new javax.swing.JTextField();
        maletxt = new javax.swing.JRadioButton();
        femaletxt = new javax.swing.JRadioButton();
        timetxt = new javax.swing.JComboBox<>();
        coursetxt = new javax.swing.JComboBox<>();
        montxt = new javax.swing.JCheckBox();
        sattxt = new javax.swing.JCheckBox();
        locationtxt = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabletxt = new javax.swing.JTable();
        Sextxt = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        BoxShow = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        datetxt.setBackground(new java.awt.Color(102, 102, 255));

        nametxt.setBackground(new java.awt.Color(204, 204, 255));

        researchtxt.setBackground(new java.awt.Color(204, 204, 255));
        researchtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                researchtxtActionPerformed(evt);
            }
        });
        researchtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                researchtxtKeyReleased(evt);
            }
        });

        pricetxt.setBackground(new java.awt.Color(204, 204, 255));

        picturetxt.setBackground(new java.awt.Color(204, 204, 255));

        numbertxt.setBackground(new java.awt.Color(204, 204, 255));
        numbertxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numbertxtActionPerformed(evt);
            }
        });

        maletxt.setBackground(new java.awt.Color(102, 102, 255));
        buttonGroup1.add(maletxt);
        maletxt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        maletxt.setText("Male");

        femaletxt.setBackground(new java.awt.Color(102, 102, 255));
        buttonGroup1.add(femaletxt);
        femaletxt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        femaletxt.setText("Female");
        femaletxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaletxtActionPerformed(evt);
            }
        });

        timetxt.setBackground(new java.awt.Color(204, 204, 255));
        timetxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:30 - 10:00", "10:15 - 11:45", "12:00 - 1:30", "1:45 - 3:15", "3:30 - 5:00" }));

        coursetxt.setBackground(new java.awt.Color(204, 204, 255));
        coursetxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Java", "Python", "Web Frontend", "Web Backend", "C/C++", " " }));

        montxt.setBackground(new java.awt.Color(102, 102, 255));
        montxt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        montxt.setText("Mon-Thu");

        sattxt.setBackground(new java.awt.Color(102, 102, 255));
        sattxt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        sattxt.setText("Sat-Sun");

        locationtxt.setBackground(new java.awt.Color(204, 204, 255));
        locationtxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Etec 1", "Etec 2", "Etec 3", "Etec 4" }));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("University of Technology");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/world1 - Copy.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        tabletxt.setBackground(new java.awt.Color(204, 204, 255));
        tabletxt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Sex", "Birth", "Course Name", "Time", "Price", "Day", "Phone Number", "Picture", "Study location"
            }
        ));
        tabletxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabletxtMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabletxt);

        Sextxt.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        Sextxt.setText("Sex:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Course name: ");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Time: ");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Phone number: ");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Date: ");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Price:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("Picture: ");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("Study location:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setText("Research: ");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setText("Name:");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setText("Day: ");

        jButton1.setBackground(new java.awt.Color(153, 153, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 153, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 153, 255));
        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(153, 153, 255));
        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton4.setText("Recycle bin");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(153, 153, 255));
        jButton5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(153, 153, 255));
        jButton6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(153, 153, 255));
        jButton7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton7.setText("Image");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        BoxShow.setBackground(new java.awt.Color(204, 0, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Sextxt, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(nametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(datetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(maletxt)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(femaletxt, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(montxt, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(sattxt, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pricetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(timetxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(coursetxt, 0, 231, Short.MAX_VALUE))))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(researchtxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(numbertxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(picturetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(locationtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1095, 1095, 1095))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BoxShow, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 992, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jButton4)
                        .addGap(57, 57, 57)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(coursetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel13)
                            .addComponent(jLabel7)
                            .addComponent(numbertxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(timetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(femaletxt)
                                    .addComponent(maletxt)
                                    .addComponent(Sextxt)
                                    .addComponent(jLabel10)
                                    .addComponent(picturetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pricetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(5, 5, 5)
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(montxt)
                            .addComponent(sattxt)
                            .addComponent(researchtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)))
                    .addComponent(datetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jButton6)
                            .addComponent(jButton7)
                            .addComponent(jButton3)))
                    .addComponent(BoxShow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(608, 608, 608))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1525, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void femaletxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaletxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_femaletxtActionPerformed

    private void numbertxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numbertxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numbertxtActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clear();
        showData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Exit();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        updateData();
        showData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        deleteFromDatabase();
        showData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        showImage();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        insertStudent();
        showData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void researchtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_researchtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_researchtxtActionPerformed

    private void tabletxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabletxtMouseClicked

        mouseClick(evt);
    }//GEN-LAST:event_tabletxtMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        recycle_bin deleting = new recycle_bin();
        deleting.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void researchtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_researchtxtKeyReleased
        search();
    }//GEN-LAST:event_researchtxtKeyReleased

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
            java.util.logging.Logger.getLogger(StudentRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BoxShow;
    private javax.swing.JLabel Sextxt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> coursetxt;
    private com.toedter.calendar.JDateChooser datetxt;
    private javax.swing.JRadioButton femaletxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> locationtxt;
    private javax.swing.JRadioButton maletxt;
    private javax.swing.JCheckBox montxt;
    private javax.swing.JTextField nametxt;
    private javax.swing.JTextField numbertxt;
    private javax.swing.JTextField picturetxt;
    private javax.swing.JTextField pricetxt;
    private javax.swing.JTextField researchtxt;
    private javax.swing.JCheckBox sattxt;
    private javax.swing.JTable tabletxt;
    private javax.swing.JComboBox<String> timetxt;
    // End of variables declaration//GEN-END:variables

//    private void initComponents() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
