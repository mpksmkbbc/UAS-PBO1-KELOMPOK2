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
public enum StatusEnum {
    MENUNGGU("Menunggu"),
    SEDANG_DIPERIKSA("Sedang Diperiksa"),
    SELESAI("Selesai"),
    DIBATALKAN("Dibatalkan");
    
    private final String displayName;
    
    // Constructor enum
    StatusEnum(String displayName) {
        this.displayName = displayName;
    }
    
    // Method untuk mendapatkan nama tampilan
    public String getDisplayName() {
        return displayName;
    }
    
    // Method untuk mendapatkan enum dari string
    public static StatusEnum fromString(String text) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.displayName.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return MENUNGGU; // default
    }
}
