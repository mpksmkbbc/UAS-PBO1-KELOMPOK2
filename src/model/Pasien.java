/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

/**
 *
 * @author HP
 */
public class Pasien extends Person {
   // Atribut tambahan (total jadi lebih dari 3)
    private String alamat;
    private LocalDate tanggalLahir;
    private String jenisKelamin;
    
    /**
     * Constructor berparameter
     */
    public Pasien(String id, String nama, String noTelp, String alamat, 
                  LocalDate tanggalLahir, String jenisKelamin) {
        super(id, nama, noTelp); // Memanggil constructor superclass
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
    }
    
    // Getter dan Setter
    public String getAlamat() {
        return alamat;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }
    
    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    
    public String getJenisKelamin() {
        return jenisKelamin;
    }
    
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    
    /**
     * Method Overriding - implementasi abstract method dari Person
     * Memenuhi requirement: Polymorphism (method overriding)
     */
    @Override
    public String getInfoLengkap() {
        return "PASIEN\n" +
               "ID: " + getId() + "\n" +
               "Nama: " + getNama() + "\n" +
               "No. Telp: " + getNoTelp() + "\n" +
               "Alamat: " + alamat + "\n" +
               "Tanggal Lahir: " + formatTanggal() + "\n" +
               "Umur: " + hitungUmur() + " tahun\n" +
               "Jenis Kelamin: " + jenisKelamin;
    }
    
    /**
     * Method tambahan 1: Hitung umur pasien
     * Menggunakan Wrapper Class (Integer)
     */
    public Integer hitungUmur() {
        if (tanggalLahir == null) {
            return 0;
        }
        return Period.between(tanggalLahir, LocalDate.now()).getYears();
    }
    
    /**
     * Method tambahan 2: Format tanggal lahir
     */
    public String formatTanggal() {
        if (tanggalLahir == null) {
            return "-";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return tanggalLahir.format(formatter);
    }
    
    /**
     * Override toFileFormat untuk menyimpan ke file
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + "|" + alamat + "|" + 
               formatTanggal() + "|" + jenisKelamin;
    }
    
    /**
     * Static method untuk parsing dari file format
     */
    public static Pasien fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate tglLahir = LocalDate.parse(parts[4], formatter);
            return new Pasien(parts[0], parts[1], parts[2], parts[3], tglLahir, parts[5]);
        }
        return null;
    }
}
