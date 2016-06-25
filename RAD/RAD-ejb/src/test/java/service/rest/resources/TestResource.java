/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

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
 * Exposes encryption and decryption functionality.
 * @author Jesse
 */
public class TestResource extends BaseResource {
    private static final Logger LOGGER 
            = Logger.getLogger(TestResource.class.getName());
    
    private static final String RAD_KEY_FILE  = "rad.key";
    
    private Key radKey;
    
    /**
     * Instantiates the TestResource class.
     */
    public TestResource() {
        super();
        this.readRadKey();
    }
    
    /**
     * Exposes the encrypt method of the BaseResource.
     * @param plain Plain text to encrypt.
     * @return Encrypted text.
     */
    public String exposeEncrypt(String plain) {
        return super.encrypt(plain);
    }
    
    /**
     * Reads the RAD key from file.
     */
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
    
    /**
     * Overrides the decrypt method from the BaseResource class. This allows for
     * the decryption with the RAD key.
     * @param encrypted The encrypted String.
     * @return The decrypted String.
     */
    @Override
    protected String decrypt(String encrypted) {
        
        try {
            // Convert encrypted string to bytes in order to decrypt the
            // message.
            byte[] bb = new byte[encrypted.length()];
            for (int i = 0; i < encrypted.length(); i++) {
                bb[i] = (byte) encrypted.charAt(i);
            }
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, this.radKey);
            return new String(cipher.doFinal(bb));
            
        } catch (NoSuchAlgorithmException 
                 | NoSuchPaddingException 
                 | InvalidKeyException 
                 | IllegalBlockSizeException 
                 | BadPaddingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
