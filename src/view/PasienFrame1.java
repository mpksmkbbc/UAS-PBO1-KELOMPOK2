package view;

import model.Pasien;
import service.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Frame Data Pasien - Menu 3
 * Halaman untuk manajemen data pasien (CRUD)
 * Fitur: Tambah, Tampilkan, Cari, Ubah, Hapus
 */
public class PasienFrame1 extends JFrame {
    
    // Components
    private JTextField txtId, txtNama, txtNoTelp, txtAlamat, txtTglLahir, txtCari;
    private JComboBox<String> cmbJenisKelamin;
    private JTable tablePasien;
    private DefaultTableModel tableModel;
    private JButton btnTambah, btnUbah, btnHapus, btnCari, btnRefresh, btnKeluar;
    
    // Data
    private List<Pasien> listPasien;
    private FileHandler fileHandler;
    private Pasien selectedPasien;
    
    public PasienFrame1() {
        fileHandler = new FileHandler();
        listPasien = fileHandler.loadPasien();
        
        initComponents();
        loadTableData();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Data Pasien - Aplikasi Klinik");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        // === PANEL FORM (ATAS) ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Pasien"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0: ID Pasien
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Pasien:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        formPanel.add(txtId, gbc);
        
        // Row 0: Nama
        gbc.gridx = 2;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 3;
        txtNama = new JTextField(15);
        formPanel.add(txtNama, gbc);
        
        // Row 1: No Telp
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("No. Telp:"), gbc);
        gbc.gridx = 1;
        txtNoTelp = new JTextField(15);
        formPanel.add(txtNoTelp, gbc);
        
        // Row 1: Tanggal Lahir
        gbc.gridx = 2;
        formPanel.add(new JLabel("Tgl Lahir (dd-MM-yyyy):"), gbc);
        gbc.gridx = 3;
        txtTglLahir = new JTextField(15);
        formPanel.add(txtTglLahir, gbc);
        
        // Row 2: Alamat
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextField(15);
        formPanel.add(txtAlamat, gbc);
        
        // Row 2: Jenis Kelamin
        gbc.gridx = 2;
        formPanel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 3;
        String[] jenisKelamin = {"Laki-laki", "Perempuan"};
        cmbJenisKelamin = new JComboBox<>(jenisKelamin);
        formPanel.add(cmbJenisKelamin, gbc);
        
        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        // === PANEL BUTTON ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnTambah = createButton("Tambah", new Color(70, 130, 180));
        btnUbah = createButton("Ubah", new Color(255, 165, 0));
        btnHapus = createButton("Hapus", new Color(220, 20, 60));
        btnRefresh = createButton("Refresh", new Color(60, 179, 113));
        btnKeluar = createButton("Keluar", new Color(128, 128, 128));
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnKeluar);
        
        // === PANEL TABEL (TENGAH) ===
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBackground(Color.WHITE);
        
        // Panel Cari
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Cari (Nama/ID):"));
        txtCari = new JTextField(20);
        searchPanel.add(txtCari);
        btnCari = createButton("Cari", new Color(100, 149, 237));
        searchPanel.add(btnCari);
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        
        // Tabel
        String[] columns = {"ID", "Nama", "No. Telp", "Alamat", "Tgl Lahir", "Umur", "Jenis Kelamin"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tidak bisa edit langsung di tabel
            }
        };
        tablePasien = new JTable(tableModel);
        tablePasien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePasien.getTableHeader().setReorderingAllowed(false);
        
        // Event klik tabel
        tablePasien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tablePasien.getSelectedRow();
                if (selectedRow != -1) {
                    loadFormFromTable(selectedRow);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablePasien);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // === EVENT LISTENERS ===
        setupEventListeners();
    }
    
    /**
     * Setup event listeners untuk semua button
     */
    private void setupEventListeners() {
        // Button Tambah
        btnTambah.addActionListener(e -> tambahPasien());
        
        // Button Ubah
        btnUbah.addActionListener(e -> ubahPasien());
        
        // Button Hapus
        btnHapus.addActionListener(e -> hapusPasien());
        
        // Button Cari
        btnCari.addActionListener(e -> cariPasien());
        
        // Button Refresh
        btnRefresh.addActionListener(e -> {
            clearForm();
            loadTableData();
        });
        
        // Button Keluar
        btnKeluar.addActionListener(e -> dispose());
        
        // Enter key untuk search
        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cariPasien();
                }
            }
        });
    }
    
    /**
     * Helper method untuk membuat button
     */
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    /**
     * Load semua data pasien ke tabel
     */
    private void loadTableData() {
        tableModel.setRowCount(0); // Clear table
        
        for (Pasien p : listPasien) {
            Object[] row = {
                p.getId(),
                p.getNama(),
                p.getNoTelp(),
                p.getAlamat(),
                p.formatTanggal(),
                p.hitungUmur() + " tahun",
                p.getJenisKelamin()
            };
            tableModel.addRow(row);
        }
    }
    
    /**
     * Load data dari tabel ke form
     */
    private void loadFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtNama.setText(tableModel.getValueAt(row, 1).toString());
        txtNoTelp.setText(tableModel.getValueAt(row, 2).toString());
        txtAlamat.setText(tableModel.getValueAt(row, 3).toString());
        txtTglLahir.setText(tableModel.getValueAt(row, 4).toString());
        
        String jenisKelamin = tableModel.getValueAt(row, 6).toString();
        cmbJenisKelamin.setSelectedItem(jenisKelamin);
        
        // Simpan pasien yang dipilih
        String id = txtId.getText();
        for (Pasien p : listPasien) {
            if (p.getId().equals(id)) {
                selectedPasien = p;
                break;
            }
        }
    }
    
    /**
     * Clear form input
     */
    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtNoTelp.setText("");
        txtAlamat.setText("");
        txtTglLahir.setText("");
        cmbJenisKelamin.setSelectedIndex(0);
        txtCari.setText("");
        selectedPasien = null;
        tablePasien.clearSelection();
    }
    
    /**
     * FITUR 1: Tambah Data Pasien
     */
    private void tambahPasien() {
        try {
            // Validasi input
            if (!validateInput()) {
                return;
            }
            
            String id = txtId.getText().trim();
            String nama = txtNama.getText().trim();
            String noTelp = txtNoTelp.getText().trim();
            String alamat = txtAlamat.getText().trim();
            String jenisKelamin = cmbJenisKelamin.getSelectedItem().toString();
            
            // Parse tanggal lahir
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate tglLahir = LocalDate.parse(txtTglLahir.getText().trim(), formatter);
            
            // Cek duplikasi ID
            for (Pasien p : listPasien) {
                if (p.getId().equals(id)) {
                    JOptionPane.showMessageDialog(this,
                        "ID Pasien sudah ada!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Buat objek Pasien baru (Polymorphism: menggunakan superclass Person)
            Pasien pasienBaru = new Pasien(id, nama, noTelp, alamat, tglLahir, jenisKelamin);
            
            // Validasi nomor telepon menggunakan method dari Person
            if (!pasienBaru.validateNoTelp()) {
                JOptionPane.showMessageDialog(this,
                    "Nomor telepon tidak valid! Harus 10-13 digit angka.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tambah ke list
            listPasien.add(pasienBaru);
            
            // Simpan ke file
            fileHandler.savePasien(listPasien);
            
            // Refresh tabel dan clear form
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this,
                "Data pasien berhasil ditambahkan!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * FITUR 2: Ubah Data Pasien
     */
    private void ubahPasien() {
        if (selectedPasien == null) {
            JOptionPane.showMessageDialog(this,
                "Pilih data pasien yang akan diubah!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Validasi input
            if (!validateInput()) {
                return;
            }
            
            String id = txtId.getText().trim();
            String nama = txtNama.getText().trim();
            String noTelp = txtNoTelp.getText().trim();
            String alamat = txtAlamat.getText().trim();
            String jenisKelamin = cmbJenisKelamin.getSelectedItem().toString();
            
            // Parse tanggal lahir
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate tglLahir = LocalDate.parse(txtTglLahir.getText().trim(), formatter);
            
            // Update data pasien (menggunakan setter - enkapsulasi)
            selectedPasien.setId(id);
            selectedPasien.setNama(nama);
            selectedPasien.setNoTelp(noTelp);
            selectedPasien.setAlamat(alamat);
            selectedPasien.setTanggalLahir(tglLahir);
            selectedPasien.setJenisKelamin(jenisKelamin);
            
            // Simpan ke file
            fileHandler.savePasien(listPasien);
            
            // Refresh tabel dan clear form
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this,
                "Data pasien berhasil diubah!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * FITUR 3: Hapus Data Pasien
     */
    private void hapusPasien() {
        if (selectedPasien == null) {
            JOptionPane.showMessageDialog(this,
                "Pilih data pasien yang akan dihapus!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus pasien:\n" + 
            selectedPasien.getNama() + " (" + selectedPasien.getId() + ")?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            listPasien.remove(selectedPasien);
            fileHandler.savePasien(listPasien);
            
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this,
                "Data pasien berhasil dihapus!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * FITUR 4: Cari Data Pasien
     * Menggunakan Interface Searchable
     */
    private void cariPasien() {
        String keyword = txtCari.getText().trim();
        
        if (keyword.isEmpty()) {
            loadTableData(); // Tampilkan semua data
            return;
        }
        
        tableModel.setRowCount(0); // Clear table
        int found = 0;
        
        // Pencarian menggunakan method searchById dan searchByName dari interface
        for (Pasien p : listPasien) {
            if (p.searchById(keyword) || p.searchByName(keyword)) {
                Object[] row = {
                    p.getId(),
                    p.getNama(),
                    p.getNoTelp(),
                    p.getAlamat(),
                    p.formatTanggal(),
                    p.hitungUmur() + " tahun",
                    p.getJenisKelamin()
                };
                tableModel.addRow(row);
                found++;
            }
        }
        
        if (found == 0) {
            JOptionPane.showMessageDialog(this,
                "Data tidak ditemukan!",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
        }
    }
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty() ||
            txtNama.getText().trim().isEmpty() ||
            txtNoTelp.getText().trim().isEmpty() ||
            txtAlamat.getText().trim().isEmpty() ||
            txtTglLahir.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                "Semua field harus diisi!",
                "Validasi Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validasi format tanggal
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(txtTglLahir.getText().trim(), formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Format tanggal salah! Gunakan format: dd-MM-yyyy\nContoh: 15-08-1990",
                "Validasi Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
}