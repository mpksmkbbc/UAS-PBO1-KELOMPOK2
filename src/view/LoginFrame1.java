package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Frame Login - Menu 1
 * Halaman pertama aplikasi untuk autentikasi
 */
public class LoginFrame1 extends JFrame {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    
    // Kredensial default (bisa diganti dengan database)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    
    public LoginFrame1() {
        initComponents();
        setLocationRelativeTo(null); // Center screen
    }
    
    private void initComponents() {
        // Setup Frame
        setTitle("Login - Aplikasi Klinik");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title Label
        JLabel lblTitle = new JLabel("APLIKASI KLINIK SEDERHANA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 20, 300, 30);
        mainPanel.add(lblTitle);
        
        // Subtitle
        JLabel lblSubtitle = new JLabel("Silakan Login");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setBounds(50, 50, 300, 20);
        mainPanel.add(lblSubtitle);
        
        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 90, 100, 25);
        mainPanel.add(lblUsername);
        
        // Username TextField
        txtUsername = new JTextField();
        txtUsername.setBounds(150, 90, 200, 25);
        mainPanel.add(txtUsername);
        
        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 130, 100, 25);
        mainPanel.add(lblPassword);
        
        // Password Field
        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 130, 200, 25);
        mainPanel.add(txtPassword);
        
        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 180, 90, 30);
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        mainPanel.add(btnLogin);
        
        // Exit Button
        btnExit = new JButton("Keluar");
        btnExit.setBounds(260, 180, 90, 30);
        btnExit.setBackground(new Color(220, 20, 60));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mainPanel.add(btnExit);
        
        // Info Label
        JLabel lblInfo = new JLabel("Default: admin / admin123");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfo.setBounds(50, 230, 300, 20);
        mainPanel.add(lblInfo);
        
        // Add Enter key listener untuk login
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        
        add(mainPanel);
    }
    
    /**
     * Method untuk melakukan login
     */
    private void performLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        
        // Validasi input kosong
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username dan Password tidak boleh kosong!", 
                "Validasi Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Cek kredensial
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            JOptionPane.showMessageDialog(this, 
                "Login Berhasil!\nSelamat datang, " + username, 
                "Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Buka Main Menu dan tutup Login Frame
            new MainMenuFrame1().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Username atau Password salah!", 
                "Login Gagal", 
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText(""); // Clear password field
        }
    }
}