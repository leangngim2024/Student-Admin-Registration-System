import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SignUp_page extends JFrame {
    Connection con;
    PreparedStatement ps;

    private JTextField username_input, pass_input, email_input, re_pass;
    
    public SignUp_page() {
        setTitle("SignUp page");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new BackgroundPanel1());
        setLayout(null);
        // Initialize DB
        MyConnect();
        // Initialize and Add UI components
        initUI();
    }

    private void initUI() {
        JLabel title = new JLabel("Student Information");
        title.setBounds(430, 30, 400, 30);
        title.setFont(new Font("Arial", Font.BOLD, 33));
        title.setForeground(Color.WHITE);
        add(title);
                
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(150, 160, 400, 350); // x, y, width, height
        inputPanel.setLayout(null);  // use null layout for absolute positioning
        inputPanel.setBackground(Color.BLACK); // light gray background

        // Create SignUp text
        JLabel signUp_label = new JLabel("Create an account");
        signUp_label.setFont(new Font("Arial", Font.BOLD, 16));
        signUp_label.setBounds(10, 10, 200, 25);
        signUp_label.setForeground(Color.WHITE);
        inputPanel.add(signUp_label);
        
        // Create a label
        JLabel username_label = new JLabel("Username:");
        username_label.setBounds(10, 50, 100, 25);
        username_label.setForeground(Color.WHITE);
        inputPanel.add(username_label);

        // Create a text field
        username_input = new JTextField();
        username_input.setBounds(180, 50, 200, 30);
        inputPanel.add(username_input);

        // Create a label
        JLabel email_label = new JLabel("Email:");
        email_label.setBounds(10, 100, 100, 25);
        email_label.setForeground(Color.WHITE);
        inputPanel.add(email_label);

        // Create a text field
        email_input = new JTextField();
        email_input.setBounds(180, 100, 200, 30);
        inputPanel.add(email_input);
        
        
        // Create a label
        JLabel pass_label = new JLabel("Password:");
        pass_label.setBounds(10, 150, 100, 25);
        pass_label.setForeground(Color.WHITE);
        inputPanel.add(pass_label);
        
        // Create a text field
        pass_input = new JTextField();
        pass_input.setBounds(180, 150, 200, 30);
        inputPanel.add(pass_input);
        
        
        // Create a label
        JLabel repassword = new JLabel("Comfirm password:");
        repassword.setBounds(10, 200, 130, 25);
        repassword.setForeground(Color.WHITE);

        inputPanel.add(repassword);
        
        // Create a text field
        re_pass = new JTextField();
        re_pass.setBounds(180, 200, 200, 30);
        inputPanel.add(re_pass);
        
        // Add the inputPanel to the main frame
        add(inputPanel);

        JButton signup_bt = new JButton("Sign In");
        signup_bt.setBounds(80, 250, 300, 30); // x, y, width, height
        inputPanel.add(signup_bt);
        
        JButton login_bt = new JButton("Already have an account?");
        login_bt.setBounds(80, 290, 300, 30); // x, y, width, height
        login_bt.setBackground(Color.BLACK);
        login_bt.setForeground(Color.WHITE);
        login_bt.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
        
        inputPanel.add(login_bt);

                
        login_bt.addActionListener(e -> {            
        Login_Page login = new Login_Page();
        login.setVisible(true);
        });
        
        signup_bt.addActionListener(e -> {      
        check_comfirm();
        });
    }
    
    public void insertUser(){
    String username = username_input.getText();
    String email = email_input.getText();
    String password = pass_input.getText();
    String comfirm_pass = re_pass.getText();

    // 1. Check if any field is empty
    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || comfirm_pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        return;
    }

    // 2. Check if passwords match
    if (!password.equals(comfirm_pass)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match. Please re-enter.");
        return; // Prevent insertion
    }

    // 3. If everything is fine, insert into database
    String insertQuery = "INSERT INTO user_tb(username, email, password) VALUES (?, ?, ?)";    
    try {
        ps = con.prepareStatement(insertQuery);
        ps.setString(1, username);
        ps.setString(2, email);
        ps.setString(3, password);

        int check = ps.executeUpdate();
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Account created successfully!");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error: Account not created.\n" + ex.getMessage());
    } 
    
    Login_Page login = new Login_Page();
    login.setVisible(true);
    
}

    
    public void MyConnect() {
        String url = "jdbc:mysql://localhost:3309/db02";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            Logger.getLogger(SignUp_page.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database connection failed.");
        }
    }

    
    void check_comfirm(){
        int confirm = JOptionPane.showConfirmDialog(this, "Please remember your information", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            insertUser();
//            Login_Page login = new Login_Page();
//            login.setVisible(true);
        }
    }
    

    // Custom JPanel for background image
class BackgroundPanel1 extends JPanel {
        private Image backgroundImage;
        public BackgroundPanel1() {
            backgroundImage = new ImageIcon(getClass().getResource("/skyCity.jpg")).getImage(); // Make sure the image is in /resources
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignUp_page().setVisible(true);
        });
    }
}
