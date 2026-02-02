/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author HP
 */
public class Appointment {
     // Minimal 3 atribut private
    private String idAppointment;
    private Pasien pasien; // Relasi dengan Pasien (Aggregation)
    private Dokter dokter; // Relasi dengan Dokter (Aggregation)
    private LocalDateTime waktuAppointment;
    private String keluhan;
    private StatusEnum status; // Menggunakan Enum
    
    /**
     * Constructor berparameter
     */
    public Appointment(String idAppointment, Pasien pasien, Dokter dokter, 
                      LocalDateTime waktuAppointment, String keluhan, StatusEnum status) {
        this.idAppointment = idAppointment;
        this.pasien = pasien;
        this.dokter = dokter;
        this.waktuAppointment = waktuAppointment;
        this.keluhan = keluhan;
        this.status = status;
    }
    
    // Getter dan Setter (Enkapsulasi)
    public String getIdAppointment() {
        return idAppointment;
    }
    
    public void setIdAppointment(String idAppointment) {
        this.idAppointment = idAppointment;
    }
    
    public Pasien getPasien() {
        return pasien;
    }
    
    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }
    
    public Dokter getDokter() {
        return dokter;
    }
    
    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }
    
    public LocalDateTime getWaktuAppointment() {
        return waktuAppointment;
    }
    
    public void setWaktuAppointment(LocalDateTime waktuAppointment) {
        this.waktuAppointment = waktuAppointment;
    }
    
    public String getKeluhan() {
        return keluhan;
    }
    
    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }
    
    public StatusEnum getStatus() {
        return status;
    }
    
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    
    /**
     * Method tambahan 1: Format waktu appointment
     */
    public String formatWaktu() {
        if (waktuAppointment == null) {
            return "-";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return waktuAppointment.format(formatter);
    }
    
    /**
     * Method tambahan 2: Dapatkan info appointment lengkap
     */
    public String getInfoAppointment() {
        return "ID Appointment: " + idAppointment + "\n" +
               "Pasien: " + pasien.getNama() + " (" + pasien.getId() + ")\n" +
               "Dokter: " + dokter.getNama() + " - " + dokter.getSpesialisasi() + "\n" +
               "Waktu: " + formatWaktu() + "\n" +
               "Keluhan: " + keluhan + "\n" +
               "Status: " + status.getDisplayName();
    }
    
    /**
     * Method tambahan 3: Update status appointment
     */
    public void updateStatus(StatusEnum newStatus) {
        this.status = newStatus;
    }
    
    /**
     * Method tambahan 4: Hitung biaya konsultasi
     * Menggunakan Wrapper Class
     */
    public Double hitungBiaya() {
        return dokter.getTarifKonsultasi();
    }
    
    /**
     * Method untuk format data ke file
     */
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return idAppointment + "|" + 
               pasien.getId() + "|" + 
               dokter.getId() + "|" + 
               waktuAppointment.format(formatter) + "|" + 
               keluhan + "|" + 
               status.getDisplayName();
    }
}
