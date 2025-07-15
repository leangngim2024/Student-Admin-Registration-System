import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Login_Page extends JFrame {
    Connection con;
    Statement st;
    ResultSet rs;
    private JTextField username_input, password_input;

    
    public Login_Page() {
        setTitle("Login Page");
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
    
    void comfirm_check(){
        int confirm = JOptionPane.showConfirmDialog(this, "Already check again?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            MyLogin();
        }
    }


    
    private void initUI() {
        JLabel title = new JLabel("Student Information");
        title.setBounds(430, 30, 400, 30);
        title.setFont(new Font("Arial", Font.BOLD, 33));
        title.setForeground(Color.WHITE);
        add(title);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(150, 160, 400, 260); // x, y, width, height
        inputPanel.setLayout(null);  // use null layout for absolute positioning
        inputPanel.setBackground(new Color(220, 220, 220)); // light gray background

        // Create SignUp text
        JLabel signUp_label = new JLabel("Log in");
        signUp_label.setFont(new Font("Arial", Font.BOLD, 16));
        signUp_label.setBounds(10, 10, 200, 25);
        inputPanel.add(signUp_label);
        
        // Create a label
        JLabel email_pass_txt = new JLabel("Email or username:");
        email_pass_txt.setBounds(10, 50, 150, 25);
        inputPanel.add(email_pass_txt);


        // Create a text field
        username_input = new JTextField();
        username_input.setBounds(180, 50, 200, 30);
        inputPanel.add(username_input);
        
        // Create a label
        JLabel password_txt = new JLabel("Password:");
        password_txt.setBounds(10, 100, 100, 25);
        inputPanel.add(password_txt);
        
        // Create a text field
        password_input = new JTextField();
        password_input.setBounds(180, 100, 200, 30);
        inputPanel.add(password_input);
        
        // Add the inputPanel to the main frame
        add(inputPanel);

        JButton login_btn = new JButton("Login");
        login_btn.setBounds(80, 150, 300, 30); // x, y, width, height
        inputPanel.add(login_btn);

        JButton signin_btn = new JButton("Create an account?");
        signin_btn.setBounds(80, 190, 300, 30); // x, y, width, height
        signin_btn.setBackground(new Color(220, 220, 220));
        signin_btn.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
        inputPanel.add(signin_btn);
        
        signin_btn.addActionListener(e -> {
        SignUp_page signup = new SignUp_page();
        signup.setVisible(true);
        });
        
        login_btn.addActionListener(e -> {
        comfirm_check();
        });
}

        
    public void MyLogin(){
        boolean check = false;
        String selectQuery = "SELECT * FROM user_tb";

        try {
            st = con.createStatement();
            rs = st.executeQuery(selectQuery);
            
            
            while(rs.next()){
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                
                if(username_input.getText().equals(username) || username_input.getText().equals(email) && password_input.getText().equals(password)){
                    check = true;
                    break;
                }
            }
            if(check == true){
                JOptionPane.showMessageDialog(this, "Welcome to your personal information");
                RegisterBackground student_side = new RegisterBackground();
                student_side.setVisible(true);

            }else {
            JOptionPane.showMessageDialog(this, "Invalid username/email or password");
        }
        } catch (SQLException ex) {
            Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    public void MyConnect() {
        String url = "jdbc:mysql://localhost:3309/db02";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            Logger.getLogger(Login_Page.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database connection failed.");
        }
    }

    
    void exit(){
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            System.exit(0);
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
            new Login_Page().setVisible(true);
        });
    }
}
