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
public abstract class Person implements SearchAble {
    // Minimal 3 atribut private
    private String id;
    private String nama;
    private String noTelp;
    
    /**
     * Constructor berparameter
     * Memenuhi requirement: minimal 1 constructor berparameter
     */
    public Person(String id, String nama, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.noTelp = noTelp;
    }
    
    // Getter dan Setter (Enkapsulasi)
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getNoTelp() {
        return noTelp;
    }
    
    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
    
    /**
     * Abstract method untuk mendapatkan info lengkap
     * Harus diimplementasikan oleh subclass
     */
    public abstract String getInfoLengkap();
    
    /**
     * Method tambahan 1: Validasi nomor telepon
     */
    public boolean validateNoTelp() {
        // Wrapper Class: menggunakan Integer untuk parsing
        if (noTelp == null || noTelp.isEmpty()) {
            return false;
        }
        return noTelp.matches("\\d{10,13}"); // 10-13 digit
    }
    
    /**
     * Method tambahan 2: Format data untuk file
     */
    public String toFileFormat() {
        return id + "|" + nama + "|" + noTelp;
    }
    
    /**
     * Implementasi Interface Searchable - searchById
     */
    @Override
    public boolean searchById(String searchId) {
        return this.id.toLowerCase().contains(searchId.toLowerCase());
    }
    
    /**
     * Implementasi Interface Searchable - searchByName
     */
    @Override
    public boolean searchByName(String searchNama) {
        return this.nama.toLowerCase().contains(searchNama.toLowerCase());
    }
}
