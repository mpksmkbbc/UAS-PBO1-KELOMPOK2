/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import view.LoginFrame1;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author HP
 */
public class Main {
     public static void main(String[] args) {
        // Set Look and Feel agar tampilan lebih modern
        try {
            // Menggunakan Nimbus Look and Feel untuk tampilan yang lebih bagus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Jika Nimbus tidak tersedia, gunakan default
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tampilkan halaman Login sebagai halaman pertama
                LoginFrame1 loginFrame = new LoginFrame1();
                loginFrame.setVisible(true);
                
                // Print info aplikasi ke console
                
            }
        });
    }
}
    
    /**
     * Method tambahan untuk menampilkan informasi aplikasi
     */

