import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.YES_OPTION;

public class RegisterBackground extends JFrame {
    Connection con;
    Statement st;
    ResultSet rs;

    private JLabel nameLabel, genderLabel, birthLabel, courseLabel, timeLabel, priceLabel, dayLabel, locationLabel, pictureLabel, phoneLabel;
    private JPanel imagePanel;
    private JLabel imageLabel;
//    private JTextField username_txt;

    
    public RegisterBackground() {
        setTitle("Background Image Example");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new BackgroundPanel1());
        setLayout(null);

        // Initialize DB
        MyConnect();
        // Initialize and Add UI components
        initUI();
        // Show data after components are ready
        showData();
    }

    private void initUI() {
        JLabel title = new JLabel("Student Information");
        title.setBounds(430, 30, 400, 30);
        title.setFont(new Font("Arial", Font.BOLD, 33));
        title.setForeground(Color.WHITE);
        add(title);

        nameLabel = new JLabel();
        genderLabel = new JLabel();
        birthLabel = new JLabel();
        courseLabel = new JLabel();
        timeLabel = new JLabel();
        priceLabel = new JLabel();
        dayLabel = new JLabel();
        locationLabel = new JLabel();
        phoneLabel = new JLabel();

        Font font = new Font("Arial", Font.PLAIN, 17);
        JLabel[] labels = {
            nameLabel, genderLabel, birthLabel, courseLabel,
            timeLabel, priceLabel, dayLabel, locationLabel,
            phoneLabel
        };

        int y = 90;
        for (JLabel label : labels) {
            label.setFont(font);
            label.setForeground(Color.WHITE);
            label.setBounds(200, y, 600, 25);
            add(label);
            y += 45;
        }
        
        imagePanel = new JPanel();
        imagePanel.setBounds(700, 100, 300, 300); // adjust size & position
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(new BorderLayout());

        imageLabel = new JLabel(); // Will hold the image
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        imagePanel.add(imageLabel, BorderLayout.CENTER);
        add(imagePanel);
        
        JButton refreshButton = new JButton("Exit Page");
        refreshButton.setBounds(850, 450, 150, 30); // x, y, width, height
        add(refreshButton);
        
        refreshButton.addActionListener(e -> {
        exit(); // call the method to reload student data
        });
    }

    public void MyConnect() {
        String url = "jdbc:mysql://localhost:3309/db02";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            Logger.getLogger(RegisterBackground.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database connection failed.");
        }
    }
    
    void exit(){
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            Main leave = new Main();
            leave.setVisible(true);
        }
    }
    
    void showData() {
//        int id = Integer.parseInt(s);
//        Login_Page login_data = new Login_Page();
//        username_txt = login_data.getusername(); 
//        String username = username_txt.getText();
        
//        JOptionPane.showMessageDialog(null, username);
      
        String query = "SELECT * FROM student_tb WHERE id = 21";
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                nameLabel.setText("Name:      " + rs.getString("name"));
                genderLabel.setText("Gender:      " + rs.getString("sex"));
                birthLabel.setText("Birth Date:      " + rs.getDate("birth"));
                courseLabel.setText("Course:      " + rs.getString("course"));
                timeLabel.setText("Time:      " + rs.getString("time"));
                priceLabel.setText("Price:      " +"$"+ rs.getDouble("price"));
                dayLabel.setText("Day:      " + rs.getString("day"));
                locationLabel.setText("Location:      " + rs.getString("locationStudy"));
//                pictureLabel.setText("Picture: " + rs.getString("picture"));
                phoneLabel.setText("Phone:      " + rs.getString("phoneNumber"));
                String picturePath = rs.getString("picture"); // E.g., "images/student20.jpg"

                if (picturePath != null && !picturePath.isEmpty()) {
                    ImageIcon icon = new ImageIcon(picturePath);
                    Image scaledImage = icon.getImage().getScaledInstance(290, 290, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                } else {
                    imageLabel.setText("No Image");
                }
            } else {
                nameLabel.setText("No data found for ID = 20");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegisterBackground.class.getName()).log(Level.SEVERE, null, ex);
            nameLabel.setText("Error loading data.");
        }
    }
    
    // Custom JPanel for background image
    class BackgroundPanel1 extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel1() {
            backgroundImage = new ImageIcon(getClass().getResource("/nightSky.jpeg")).getImage(); // Make sure the image is in /resources
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterBackground().setVisible(true);
        });
    }
}
