package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Menu Frame - Menu 2
 * Dashboard utama aplikasi dengan akses ke semua fitur
 */
public class MainMenuFrame1 extends JFrame {
    
    private JButton btnPasien;
    private JButton btnDokter;
    private JButton btnAppointment;
    private JButton btnLogout;
    
    public MainMenuFrame1() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Setup Frame
        setTitle("Menu Utama - Aplikasi Klinik");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel lblTitle = new JLabel("MENU UTAMA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 20, 400, 40);
        mainPanel.add(lblTitle);
        
        // Subtitle
        JLabel lblSubtitle = new JLabel("Aplikasi Klinik Sederhana");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setBounds(50, 60, 400, 25);
        mainPanel.add(lblSubtitle);
        
        // Button Pasien
        btnPasien = createMenuButton("Data Pasien", 120, 110);
        btnPasien.setBackground(new Color(70, 130, 180));
        btnPasien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PasienFrame1().setVisible(true);
            }
        });
        mainPanel.add(btnPasien);
        
        // Button Dokter
        btnDokter = createMenuButton("Data Dokter", 120, 170);
        btnDokter.setBackground(new Color(60, 179, 113));
        btnDokter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DokterFrame1().setVisible(true);
            }
        });
        mainPanel.add(btnDokter);
        
        // Button Appointment
        btnAppointment = createMenuButton("Data Appointment", 120, 230);
        btnAppointment.setBackground(new Color(255, 140, 0));
        btnAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AppointmentFrame1().setVisible(true);
            }
        });
        mainPanel.add(btnAppointment);
        
        // Button Logout
        btnLogout = createMenuButton("Logout", 120, 290);
        btnLogout.setBackground(new Color(220, 20, 60));
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainMenuFrame1.this,
                    "Apakah Anda yakin ingin logout?",
                    "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    new LoginFrame1().setVisible(true);
                    dispose();
                }
            }
        });
        mainPanel.add(btnLogout);
        
        // Footer
        JLabel lblFooter = new JLabel("© 2026 Aplikasi Klinik Sederhana Kelompok 2");
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 10));
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setBounds(50, 340, 400, 20);
        mainPanel.add(lblFooter);
        
        add(mainPanel);
    }
    
    /**
     * Helper method untuk membuat button menu
     */
    private JButton createMenuButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 260, 45);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBounds(x - 5, y, 270, 45);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBounds(x, y, 260, 45);
            }
        });
        
        return btn;
    }
}