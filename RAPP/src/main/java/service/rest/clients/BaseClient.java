/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.clients;

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
 * Provides security functionality to the REST clients.
 * @author Jesse
 */
public class BaseClient {
    private static final Logger LOGGER 
            = Logger.getLogger(BaseClient.class.getName());
    
    private static final String RAD_KEY_FILE  = "rad.key";
    private static final String RAPP_KEY_FILE = "rapp.key";
    private static final String RAD_API_KEY_FILE = "radapi.key";
    
    protected final Gson gson;
    protected String radApiKey;
    
    private Key radKey;
    private Key rappKey;
    
    /**
     * Instantiates the BaseResource.
     */
    public BaseClient() {
        this.gson = new Gson();
        this.readRadApiKey();
        this.readRadKey();
        this.readRappKey();
    }
    
    /**
     * Reads the RAD api key from file.
     */
    private void readRadApiKey() {
        this.radApiKey = this.readStringFromFile(RAD_API_KEY_FILE);
    }
    
    /**
     * Reads the rad key from file and creates the radKey.
     */
    private void readRadKey() {
        this.radKey = new SecretKeySpec(
                this.readStringFromFile(RAD_KEY_FILE).getBytes(),
                "AES");
    }
    
    /**
     * Reads the rapp key from file and creates the rappKey.
     */
    private void readRappKey() {
        this.rappKey = new SecretKeySpec(
                this.readStringFromFile(RAPP_KEY_FILE).getBytes(),
                "AES");
    }
    
    /**
     * Encrypts the JSON message.
     * @param plain The plain text JSON message.
     * @return Encrypted string using the RAPP key.
     */
    protected String encrypt(String plain) {
        try {
            // Encrypt the json string.
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, this.rappKey);
            byte[] encrypted = cipher.doFinal(plain.getBytes());
            
            // Create a string of the encrypted bytes.
            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }
            
            return sb.toString();
        } catch (InvalidKeyException 
                 | NoSuchAlgorithmException 
                 | NoSuchPaddingException 
                 | IllegalBlockSizeException 
                 | BadPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
     * Decrypts the encrypted JSON message.
     * @param encrypted The encrypted JSON message.
     * @return Plain text JSON string using the RAD key.
     */
    protected String decrypt(String encrypted) {
        try {
            // Convert encrypted string to bytes in order to decrypt the
            // message.
            byte[] encryptedBytes = new byte[encrypted.length()];
            for (int i = 0; i < encrypted.length(); i++) {
                encryptedBytes[i] = (byte) encrypted.charAt(i);
            }
            
            // Decrypt the message.
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, this.radKey);
            return new String(cipher.doFinal(encryptedBytes));
        } catch (NoSuchAlgorithmException 
                 | NoSuchPaddingException 
                 | InvalidKeyException 
                 | IllegalBlockSizeException 
                 | BadPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * Reads a string value from a file.
     * @param path The absolute path of the file.
     * @return The string read from file. Returns null if something went wrong.
     */
    private String readStringFromFile(String file) {
        String path = String.format("C:/Proftaak/certificates/%s", file);
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            
            return sb.toString();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}