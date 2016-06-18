/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Jesse
 */
public class BaseResource {
    private static final Logger LOGGER 
            = Logger.getLogger(BaseResource.class.getName());
    
    private static final String RAD_KEY_FILE  = "rad.key";
    private static final String RAPP_KEY_FILE = "rapp.key";
    
    protected final Gson gson;
    
    private Key radKey;
    private Key rappKey;
    
    public BaseResource() {
        this.gson = new Gson();
        this.readRadKey();
        this.readRappKey();
    }
    
    private void readRadKey() {
        String filePath = String.format("C:/Proftaak/certificates/%s",
                                        RAD_KEY_FILE);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            
            String key = sb.toString();
            this.radKey = new SecretKeySpec(key.getBytes(), "AES");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    private void readRappKey() {
        String filePath = String.format("C:/Proftaak/certificates/%s",
                                        RAPP_KEY_FILE);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            
            String key = sb.toString();
            this.rappKey = new SecretKeySpec(key.getBytes(), "AES");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    protected String encrypt(String plain) {
        try {
            // Encrypt the json string.
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.radKey);
            byte[] encrypted = cipher.doFinal(plain.getBytes());
            
            // Create a string of the encrypted bytes.
            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }
            
            return sb.toString();
            
        } catch (InvalidKeyException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    protected String decrypt(String encrypted) {
        
        try {
            // Convert encrypted string to bytes in order to decrypt the message.
            byte[] bb = new byte[encrypted.length()];
            for (int i = 0; i < encrypted.length(); i++) {
                bb[i] = (byte) encrypted.charAt(i);
            }
            
            Cipher cipher = Cipher.getInstance("AES");
            // TODO: rappKey instead of radKey
            cipher.init(Cipher.DECRYPT_MODE, this.radKey);
            return new String(cipher.doFinal(bb));
            
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    protected String toJson(Object object) {
        return this.gson.toJson(object);
    }
}