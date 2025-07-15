import java.awt.*;
import javax.swing.*;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main extends JFrame {
    
    public Main() {
        setTitle("SignUp page");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new BackgroundPanel1());
        setLayout(null);
        // Initialize and Add UI components
        initUI();
    }

    private void initUI() {

        JLabel title = new JLabel("Welcome to Student Registation System");
        title.setBounds(350, 30, 650, 30);
        title.setFont(new Font("Arial", Font.BOLD, 33));
        title.setForeground(Color.WHITE);
        add(title);
                
        JLabel infor = new JLabel("Verify Identity");
        infor.setBounds(350, 180, 400, 30);
        infor.setFont(new Font("Arial", Font.BOLD, 17));
        infor.setForeground(Color.WHITE);
        add(infor);
        
        JButton student = new JButton("Student");
        student.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
        student.setBackground(Color.white);
        student.setBounds(350, 250, 300, 30); // x, y, width, height
        add(student);
        
        JButton admin = new JButton("Admin");
        admin.setBounds(350, 290, 300, 30); // x, y, width, height
        admin.setBackground(Color.BLUE);
        admin.setForeground(Color.WHITE);
        admin.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
        add(admin);
                
        JButton exit = new JButton("Exit system");
        exit.setBounds(350, 330, 300, 30); // x, y, width, height
        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.WHITE);
        exit.setBorder(BorderFactory.createLineBorder(Color.BLUE, 0));
        add(exit);
        
        student.addActionListener(e -> {            
        goto_student();
        });
        
        admin.addActionListener(e -> {            
        goto_admin();
        
        });

        exit.addActionListener(e -> {            
        exit();
        });
    }
    
    void exit(){
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to exit?", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            System.exit(0);
        }
    }
    
    void goto_student(){
        int confirm = JOptionPane.showConfirmDialog(this, "Go to Student information.", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            SignUp_page signup = new SignUp_page();
            signup.setVisible(true);
        }
}
    
    void goto_admin(){
        int confirm = JOptionPane.showConfirmDialog(this, "Go to register page", "Exit confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == YES_OPTION){
            Admin_login admin = new Admin_login();
            admin.setVisible(true);
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
            new Main().setVisible(true);
        });
    }
}
