/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Amr
 */
public class Validation {

    public static boolean nameValidation(String name) {
        if (name.matches("[A-Za-z\\s]+")) {
            return true;
        }
        return false;

    }

    public static boolean eMailValidation(String email) {

        if (email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return true;
        }

        return false;
    }
    
     public static boolean passwordValidation(String email) {

        if (email.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,}$")) {
            return true;
        }

        return false;
    }
    
    
}
