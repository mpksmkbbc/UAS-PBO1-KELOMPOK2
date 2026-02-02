/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author HP
 */
public class Dokter extends Person {
    // Atribut tambahan (total jadi lebih dari 3)
    private String spesialisasi;
    private String jadwal;
    private Double tarifKonsultasi; // Wrapper Class
    
    /**
     * Constructor berparameter
     */
    public Dokter(String id, String nama, String noTelp, String spesialisasi, 
                  String jadwal, Double tarifKonsultasi) {
        super(id, nama, noTelp); // Memanggil constructor superclass
        this.spesialisasi = spesialisasi;
        this.jadwal = jadwal;
        this.tarifKonsultasi = tarifKonsultasi;
    }
    
    // Getter dan Setter (Enkapsulasi)
    public String getSpesialisasi() {
        return spesialisasi;
    }
    
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
    
    public String getJadwal() {
        return jadwal;
    }
    
    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }
    
    public Double getTarifKonsultasi() {
        return tarifKonsultasi;
    }
    
    public void setTarifKonsultasi(Double tarifKonsultasi) {
        this.tarifKonsultasi = tarifKonsultasi;
    }
    
    /**
     * Method Overriding - implementasi abstract method dari Person
     * Memenuhi requirement: Polymorphism (method overriding)
     */
    @Override
    public String getInfoLengkap() {
        return "DOKTER\n" +
               "ID: " + getId() + "\n" +
               "Nama: " + getNama() + "\n" +
               "No. Telp: " + getNoTelp() + "\n" +
               "Spesialisasi: " + spesialisasi + "\n" +
               "Jadwal: " + jadwal + "\n" +
               "Tarif: " + formatTarif();
    }
    
    /**
     * Method tambahan 1: Format tarif dengan rupiah
     * Menggunakan Wrapper Class (Double)
     */
    public String formatTarif() {
        if (tarifKonsultasi == null) {
            return "Rp 0";
        }
        return String.format("Rp %.0f", tarifKonsultasi);
    }
    
    /**
     * Method tambahan 2: Validasi tarif
     */
    public boolean validateTarif() {
        return tarifKonsultasi != null && tarifKonsultasi > 0;
    }
    
    /**
     * Method tambahan 3: Cek ketersediaan berdasarkan hari
     */
    public boolean isAvailableOn(String hari) {
        if (jadwal == null) {
            return false;
        }
        return jadwal.toLowerCase().contains(hari.toLowerCase());
    }
    
    /**
     * Override toFileFormat untuk menyimpan ke file
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + "|" + spesialisasi + "|" + 
               jadwal + "|" + tarifKonsultasi;
    }
    
    /**
     * Static method untuk parsing dari file format
     */
    public static Dokter fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            // Wrapper Class: Double.parseDouble
            Double tarif = Double.parseDouble(parts[5]);
            return new Dokter(parts[0], parts[1], parts[2], parts[3], parts[4], tarif);
        }
        return null;
    }
}
