package view;

import model.*;
import service.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Frame Data Appointment - Menu 5
 * Menunjukkan Class Relasi (Aggregation) antara Pasien dan Dokter
 * Menggunakan Enum untuk Status
 */
public class AppointmentFrame1 extends JFrame {
    
    private JTextField txtId, txtWaktu, txtKeluhan, txtCari;
    private JComboBox<String> cmbPasien, cmbDokter, cmbStatus;
    private JTable tableAppointment;
    private DefaultTableModel tableModel;
    private List<Appointment> listAppointment;
    private List<Pasien> listPasien;
    private List<Dokter> listDokter;
    private FileHandler fileHandler;
    private Appointment selectedAppointment;
    
    public AppointmentFrame1() {
        fileHandler = new FileHandler();
        listPasien = fileHandler.loadPasien();
        listDokter = fileHandler.loadDokter();
        listAppointment = fileHandler.loadAppointment(listPasien, listDokter);
        
        initComponents();
        loadTableData();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Data Appointment - Aplikasi Klinik");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Appointment"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Appointment:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        formPanel.add(txtId, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Waktu (dd-MM-yyyy HH:mm):"), gbc);
        gbc.gridx = 3;
        txtWaktu = new JTextField(15);
        formPanel.add(txtWaktu, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Pasien:"), gbc);
        gbc.gridx = 1;
        cmbPasien = new JComboBox<>();
        updatePasienComboBox();
        formPanel.add(cmbPasien, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Dokter:"), gbc);
        gbc.gridx = 3;
        cmbDokter = new JComboBox<>();
        updateDokterComboBox();
        formPanel.add(cmbDokter, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Keluhan:"), gbc);
        gbc.gridx = 1;
        txtKeluhan = new JTextField(15);
        formPanel.add(txtKeluhan, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3;
        // Menggunakan Enum untuk Status
        cmbStatus = new JComboBox<>();
        for (StatusEnum status : StatusEnum.values()) {
            cmbStatus.addItem(status.getDisplayName());
        }
        formPanel.add(cmbStatus, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnTambah = createButton("Tambah", new Color(70, 130, 180));
        JButton btnUbah = createButton("Ubah Status", new Color(255, 165, 0));
        JButton btnHapus = createButton("Hapus", new Color(220, 20, 60));
        JButton btnDetail = createButton("Lihat Detail", new Color(147, 112, 219));
        JButton btnRefresh = createButton("Refresh", new Color(60, 179, 113));
        JButton btnKeluar = createButton("Keluar", new Color(128, 128, 128));
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnDetail);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnKeluar);
        
        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBackground(Color.WHITE);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Cari (ID/Pasien):"));
        txtCari = new JTextField(20);
        searchPanel.add(txtCari);
        JButton btnCari = createButton("Cari", new Color(100, 149, 237));
        searchPanel.add(btnCari);
        
        String[] columns = {"ID", "Pasien", "Dokter", "Waktu", "Keluhan", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAppointment = new JTable(tableModel);
        tableAppointment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableAppointment.getSelectedRow();
                if (row != -1) loadFormFromTable(row);
            }
        });
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(tableAppointment), BorderLayout.CENTER);
        
        // Main Layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Event Listeners
        btnTambah.addActionListener(e -> tambahAppointment());
        btnUbah.addActionListener(e -> ubahStatus());
        btnHapus.addActionListener(e -> hapusAppointment());
        btnDetail.addActionListener(e -> lihatDetail());
        btnCari.addActionListener(e -> cariAppointment());
        btnRefresh.addActionListener(e -> { clearForm(); loadTableData(); });
        btnKeluar.addActionListener(e -> dispose());
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 30));
        return btn;
    }
    
    private void updatePasienComboBox() {
        cmbPasien.removeAllItems();
        for (Pasien p : listPasien) {
            cmbPasien.addItem(p.getId() + " - " + p.getNama());
        }
    }
    
    private void updateDokterComboBox() {
        cmbDokter.removeAllItems();
        for (Dokter d : listDokter) {
            cmbDokter.addItem(d.getId() + " - " + d.getNama() + " (" + d.getSpesialisasi() + ")");
        }
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Appointment apt : listAppointment) {
            Object[] row = {
                apt.getIdAppointment(),
                apt.getPasien().getNama(),
                apt.getDokter().getNama(),
                apt.formatWaktu(),
                apt.getKeluhan(),
                apt.getStatus().getDisplayName()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadFormFromTable(int row) {
        String id = tableModel.getValueAt(row, 0).toString();
        
        for (Appointment apt : listAppointment) {
            if (apt.getIdAppointment().equals(id)) {
                selectedAppointment = apt;
                
                txtId.setText(apt.getIdAppointment());
                txtWaktu.setText(apt.formatWaktu());
                txtKeluhan.setText(apt.getKeluhan());
                
                // Set ComboBox
                String pasienText = apt.getPasien().getId() + " - " + apt.getPasien().getNama();
                cmbPasien.setSelectedItem(pasienText);
                
                String dokterText = apt.getDokter().getId() + " - " + apt.getDokter().getNama() + 
                                   " (" + apt.getDokter().getSpesialisasi() + ")";
                cmbDokter.setSelectedItem(dokterText);
                
                cmbStatus.setSelectedItem(apt.getStatus().getDisplayName());
                break;
            }
        }
    }
    
    private void clearForm() {
        txtId.setText(""); txtWaktu.setText(""); txtKeluhan.setText(""); txtCari.setText("");
        if (cmbPasien.getItemCount() > 0) cmbPasien.setSelectedIndex(0);
        if (cmbDokter.getItemCount() > 0) cmbDokter.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        selectedAppointment = null;
        tableAppointment.clearSelection();
    }
    
    /**
     * Tambah Appointment - demonstrasi Class Relasi (Aggregation)
     */
    private void tambahAppointment() {
        try {
            if (!validateInput()) return;
            
            // Cek data pasien dan dokter tersedia
            if (listPasien.isEmpty() || listDokter.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Pastikan sudah ada data Pasien dan Dokter!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String id = txtId.getText().trim();
            
            // Cek duplikasi ID
            for (Appointment apt : listAppointment) {
                if (apt.getIdAppointment().equals(id)) {
                    JOptionPane.showMessageDialog(this, "ID Appointment sudah ada!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Parse waktu
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime waktu = LocalDateTime.parse(txtWaktu.getText().trim(), formatter);
            
            // Dapatkan Pasien yang dipilih (Relasi Aggregation)
            String pasienSelected = cmbPasien.getSelectedItem().toString();
            String pasienId = pasienSelected.split(" - ")[0];
            Pasien pasien = findPasienById(pasienId);
            
            // Dapatkan Dokter yang dipilih (Relasi Aggregation)
            String dokterSelected = cmbDokter.getSelectedItem().toString();
            String dokterId = dokterSelected.split(" - ")[0];
            Dokter dokter = findDokterById(dokterId);
            
            // Dapatkan Status dari Enum
            String statusText = cmbStatus.getSelectedItem().toString();
            StatusEnum status = StatusEnum.fromString(statusText);
            
            // Buat Appointment baru (Class Relasi)
            Appointment appointmentBaru = new Appointment(id, pasien, dokter, 
                waktu, txtKeluhan.getText().trim(), status);
            
            listAppointment.add(appointmentBaru);
            fileHandler.saveAppointment(listAppointment, listPasien, listDokter);
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this, 
                "Appointment berhasil dibuat!\nBiaya: " + appointmentBaru.hitungBiaya(), 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Ubah Status Appointment - menggunakan Enum
     */
    private void ubahStatus() {
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "Pilih appointment terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String statusText = cmbStatus.getSelectedItem().toString();
        StatusEnum newStatus = StatusEnum.fromString(statusText);
        
        // Update status menggunakan method dari Appointment
        selectedAppointment.updateStatus(newStatus);
        
        fileHandler.saveAppointment(listAppointment, listPasien, listDokter);
        loadTableData();
        
        JOptionPane.showMessageDialog(this, 
            "Status appointment berhasil diubah menjadi: " + newStatus.getDisplayName(), 
            "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void hapusAppointment() {
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "Pilih appointment terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin menghapus appointment: " + selectedAppointment.getIdAppointment() + "?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            listAppointment.remove(selectedAppointment);
            fileHandler.saveAppointment(listAppointment, listPasien, listDokter);
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Appointment berhasil dihapus!");
        }
    }
    
    /**
     * Lihat detail lengkap appointment (demonstrasi Polymorphism)
     */
    private void lihatDetail() {
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "Pilih appointment terlebih dahulu!");
            return;
        }
        
        // Demonstrasi Polymorphism: memanggil method getInfoLengkap()
        // yang di-override di subclass Pasien dan Dokter
        String detail = "=== DETAIL APPOINTMENT ===\n\n" +
                       selectedAppointment.getInfoAppointment() + "\n\n" +
                       "=== INFO PASIEN ===\n" +
                       selectedAppointment.getPasien().getInfoLengkap() + "\n\n" +
                       "=== INFO DOKTER ===\n" +
                       selectedAppointment.getDokter().getInfoLengkap();
        
        JTextArea textArea = new JTextArea(detail);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Detail Appointment", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cariAppointment() {
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }
        
        tableModel.setRowCount(0);
        int found = 0;
        
        for (Appointment apt : listAppointment) {
            if (apt.getIdAppointment().toLowerCase().contains(keyword.toLowerCase()) ||
                apt.getPasien().getNama().toLowerCase().contains(keyword.toLowerCase())) {
                Object[] row = {
                    apt.getIdAppointment(),
                    apt.getPasien().getNama(),
                    apt.getDokter().getNama(),
                    apt.formatWaktu(),
                    apt.getKeluhan(),
                    apt.getStatus().getDisplayName()
                };
                tableModel.addRow(row);
                found++;
            }
        }
        
        if (found == 0) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
            loadTableData();
        }
    }
    
    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty() || txtWaktu.getText().trim().isEmpty() ||
            txtKeluhan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", 
                "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime.parse(txtWaktu.getText().trim(), formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Format waktu salah! Gunakan: dd-MM-yyyy HH:mm\nContoh: 15-08-2024 09:30", 
                "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private Pasien findPasienById(String id) {
        for (Pasien p : listPasien) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
    
    private Dokter findDokterById(String id) {
        for (Dokter d : listDokter) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }
}