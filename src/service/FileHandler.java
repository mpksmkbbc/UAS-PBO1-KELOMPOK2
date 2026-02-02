/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class FileHandler {
     private static final String DATA_DIR = "data/";
    private static final String PASIEN_FILE = DATA_DIR + "pasien.txt";
    private static final String DOKTER_FILE = DATA_DIR + "dokter.txt";
    private static final String APPOINTMENT_FILE = DATA_DIR + "appointment.txt";
    
    /**
     * Constructor - membuat folder data jika belum ada
     */
    public FileHandler() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Simpan data pasien ke file
     */
    public void savePasien(List<Pasien> listPasien) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASIEN_FILE))) {
            for (Pasien pasien : listPasien) {
                writer.write(pasien.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving pasien: " + e.getMessage());
        }
    }
    
    /**
     * Baca data pasien dari file
     */
    public List<Pasien> loadPasien() {
        List<Pasien> listPasien = new ArrayList<>();
        File file = new File(PASIEN_FILE);
        
        if (!file.exists()) {
            return listPasien; // Return empty list
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pasien pasien = Pasien.fromFileFormat(line);
                if (pasien != null) {
                    listPasien.add(pasien);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading pasien: " + e.getMessage());
        }
        
        return listPasien;
    }
    
    /**
     * Simpan data dokter ke file
     */
    public void saveDokter(List<Dokter> listDokter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOKTER_FILE))) {
            for (Dokter dokter : listDokter) {
                writer.write(dokter.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving dokter: " + e.getMessage());
        }
    }
    
    /**
     * Baca data dokter dari file
     */
    public List<Dokter> loadDokter() {
        List<Dokter> listDokter = new ArrayList<>();
        File file = new File(DOKTER_FILE);
        
        if (!file.exists()) {
            return listDokter; // Return empty list
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Dokter dokter = Dokter.fromFileFormat(line);
                if (dokter != null) {
                    listDokter.add(dokter);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading dokter: " + e.getMessage());
        }
        
        return listDokter;
    }
    
    /**
     * Simpan data appointment ke file
     */
    public void saveAppointment(List<Appointment> listAppointment, 
                               List<Pasien> listPasien, List<Dokter> listDokter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (Appointment apt : listAppointment) {
                writer.write(apt.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving appointment: " + e.getMessage());
        }
    }
    
    /**
     * Baca data appointment dari file
     */
    public List<Appointment> loadAppointment(List<Pasien> listPasien, List<Dokter> listDokter) {
        List<Appointment> listAppointment = new ArrayList<>();
        File file = new File(APPOINTMENT_FILE);
        
        if (!file.exists()) {
            return listAppointment; // Return empty list
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    // Cari pasien dan dokter berdasarkan ID
                    Pasien pasien = findPasienById(parts[1], listPasien);
                    Dokter dokter = findDokterById(parts[2], listDokter);
                    
                    if (pasien != null && dokter != null) {
                        LocalDateTime waktu = LocalDateTime.parse(parts[3], formatter);
                        StatusEnum status = StatusEnum.fromString(parts[5]);
                        
                        Appointment apt = new Appointment(parts[0], pasien, dokter, 
                                                          waktu, parts[4], status);
                        listAppointment.add(apt);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading appointment: " + e.getMessage());
        }
        
        return listAppointment;
    }
    
    /**
     * Helper method: Cari pasien berdasarkan ID
     */
    private Pasien findPasienById(String id, List<Pasien> listPasien) {
        for (Pasien p : listPasien) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Helper method: Cari dokter berdasarkan ID
     */
    private Dokter findDokterById(String id, List<Dokter> listDokter) {
        for (Dokter d : listDokter) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }
}
