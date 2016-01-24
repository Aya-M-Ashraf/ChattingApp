/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Amr
 */
public class Validation {

    public static boolean nameValidation(String name) {
   /*
        if (name.matches("([A-Z][a-z]*)([\\\\s\\\\\\'-][A-Z][a-z]*)*")) {
            return true;
        }
*/
        return true;

    }

    public static boolean eMailValidation(String email) {
/*
        if (email.matches("^(([^<>()[\\]\\\\.,;:\\s@\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
            return true;
        }
*/
        return true;
    }
    
     public static boolean passwordValidation(String email) {
/*
        if (email.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,}$")) {
            return true;
        }
*/
        return true;
    }
    
    
}
