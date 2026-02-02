package view;

import model.Dokter;
import service.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Frame Data Dokter - Menu 4
 * Halaman untuk manajemen data dokter (CRUD)
 */
public class DokterFrame1 extends JFrame {
    
    private JTextField txtId, txtNama, txtNoTelp, txtSpesialisasi, txtJadwal, txtTarif, txtCari;
    private JTable tableDokter;
    private DefaultTableModel tableModel;
    private List<Dokter> listDokter;
    private FileHandler fileHandler;
    private Dokter selectedDokter;
    
    public DokterFrame1() {
        fileHandler = new FileHandler();
        listDokter = fileHandler.loadDokter();
        initComponents();
        loadTableData();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Data Dokter - Aplikasi Klinik");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Data Dokter"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Dokter:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        formPanel.add(txtId, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 3;
        txtNama = new JTextField(15);
        formPanel.add(txtNama, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("No. Telp:"), gbc);
        gbc.gridx = 1;
        txtNoTelp = new JTextField(15);
        formPanel.add(txtNoTelp, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Spesialisasi:"), gbc);
        gbc.gridx = 3;
        txtSpesialisasi = new JTextField(15);
        formPanel.add(txtSpesialisasi, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Jadwal:"), gbc);
        gbc.gridx = 1;
        txtJadwal = new JTextField(15);
        formPanel.add(txtJadwal, gbc);
        
        gbc.gridx = 2;
        formPanel.add(new JLabel("Tarif (Rp):"), gbc);
        gbc.gridx = 3;
        txtTarif = new JTextField(15);
        formPanel.add(txtTarif, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnTambah = createButton("Tambah", new Color(70, 130, 180));
        JButton btnUbah = createButton("Ubah", new Color(255, 165, 0));
        JButton btnHapus = createButton("Hapus", new Color(220, 20, 60));
        JButton btnRefresh = createButton("Refresh", new Color(60, 179, 113));
        JButton btnKeluar = createButton("Keluar", new Color(128, 128, 128));
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnKeluar);
        
        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout(5, 5));
        tablePanel.setBackground(Color.WHITE);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Cari (Nama/ID):"));
        txtCari = new JTextField(20);
        searchPanel.add(txtCari);
        JButton btnCari = createButton("Cari", new Color(100, 149, 237));
        searchPanel.add(btnCari);
        
        String[] columns = {"ID", "Nama", "No. Telp", "Spesialisasi", "Jadwal", "Tarif"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDokter = new JTable(tableModel);
        tableDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableDokter.getSelectedRow();
                if (row != -1) loadFormFromTable(row);
            }
        });
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(tableDokter), BorderLayout.CENTER);
        
        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        add(mainPanel);
        
        // Event Listeners
        btnTambah.addActionListener(e -> tambahDokter());
        btnUbah.addActionListener(e -> ubahDokter());
        btnHapus.addActionListener(e -> hapusDokter());
        btnCari.addActionListener(e -> cariDokter());
        btnRefresh.addActionListener(e -> { clearForm(); loadTableData(); });
        btnKeluar.addActionListener(e -> dispose());
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
        return btn;
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Dokter d : listDokter) {
            Object[] row = {d.getId(), d.getNama(), d.getNoTelp(), 
                           d.getSpesialisasi(), d.getJadwal(), d.formatTarif()};
            tableModel.addRow(row);
        }
    }
    
    private void loadFormFromTable(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtNama.setText(tableModel.getValueAt(row, 1).toString());
        txtNoTelp.setText(tableModel.getValueAt(row, 2).toString());
        txtSpesialisasi.setText(tableModel.getValueAt(row, 3).toString());
        txtJadwal.setText(tableModel.getValueAt(row, 4).toString());
        
        String tarif = tableModel.getValueAt(row, 5).toString().replace("Rp ", "").replace(".", "");
        txtTarif.setText(tarif);
        
        String id = txtId.getText();
        for (Dokter d : listDokter) {
            if (d.getId().equals(id)) {
                selectedDokter = d;
                break;
            }
        }
    }
    
    private void clearForm() {
        txtId.setText(""); txtNama.setText(""); txtNoTelp.setText("");
        txtSpesialisasi.setText(""); txtJadwal.setText(""); txtTarif.setText("");
        txtCari.setText(""); selectedDokter = null;
        tableDokter.clearSelection();
    }
    
    private void tambahDokter() {
        try {
            if (!validateInput()) return;
            
            String id = txtId.getText().trim();
            
            // Cek duplikasi ID
            for (Dokter d : listDokter) {
                if (d.getId().equals(id)) {
                    JOptionPane.showMessageDialog(this, "ID Dokter sudah ada!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Wrapper Class: Double.parseDouble
            Double tarif = Double.parseDouble(txtTarif.getText().trim());
            
            Dokter dokterBaru = new Dokter(id, txtNama.getText().trim(),
                txtNoTelp.getText().trim(), txtSpesialisasi.getText().trim(),
                txtJadwal.getText().trim(), tarif);
            
            if (!dokterBaru.validateNoTelp()) {
                JOptionPane.showMessageDialog(this, 
                    "Nomor telepon tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            listDokter.add(dokterBaru);
            fileHandler.saveDokter(listDokter);
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Data dokter berhasil ditambahkan!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tarif harus berupa angka!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ubahDokter() {
        if (selectedDokter == null) {
            JOptionPane.showMessageDialog(this, "Pilih data dokter terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            if (!validateInput()) return;
            
            Double tarif = Double.parseDouble(txtTarif.getText().trim());
            
            selectedDokter.setId(txtId.getText().trim());
            selectedDokter.setNama(txtNama.getText().trim());
            selectedDokter.setNoTelp(txtNoTelp.getText().trim());
            selectedDokter.setSpesialisasi(txtSpesialisasi.getText().trim());
            selectedDokter.setJadwal(txtJadwal.getText().trim());
            selectedDokter.setTarifKonsultasi(tarif);
            
            fileHandler.saveDokter(listDokter);
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Data dokter berhasil diubah!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusDokter() {
        if (selectedDokter == null) {
            JOptionPane.showMessageDialog(this, "Pilih data dokter terlebih dahulu!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin menghapus dokter: " + selectedDokter.getNama() + "?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            listDokter.remove(selectedDokter);
            fileHandler.saveDokter(listDokter);
            loadTableData();
            clearForm();
            
            JOptionPane.showMessageDialog(this, "Data dokter berhasil dihapus!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cariDokter() {
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }
        
        tableModel.setRowCount(0);
        int found = 0;
        
        for (Dokter d : listDokter) {
            if (d.searchById(keyword) || d.searchByName(keyword)) {
                Object[] row = {d.getId(), d.getNama(), d.getNoTelp(), 
                               d.getSpesialisasi(), d.getJadwal(), d.formatTarif()};
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
        if (txtId.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty() ||
            txtNoTelp.getText().trim().isEmpty() || txtSpesialisasi.getText().trim().isEmpty() ||
            txtJadwal.getText().trim().isEmpty() || txtTarif.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", 
                "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}