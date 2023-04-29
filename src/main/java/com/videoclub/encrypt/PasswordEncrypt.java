package com.videoclub.encrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JTextField;

public class PasswordEncrypt {

    public static String encryptPassword(String passwordField) {
        String encryptedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordField.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }
    

    }
