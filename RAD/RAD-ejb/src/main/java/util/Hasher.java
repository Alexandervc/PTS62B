/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class for hashing strings.
 * @author Alexander
 */
public class Hasher {
    /**
     * Hash the given string.
     * @param toHash The string to hash.
     * @return The hashed string.
     */
    public static String hash(String toHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(toHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
