/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author Jesse
 */
public class BaseResource {
    private static final Logger LOGGER 
            = Logger.getLogger(BaseResource.class.getName());
    
    private static final String SYSTEM_NAME = "rad.cer";
    private static final String PRIVATE_KEY_FILENAME = "rad.pkcs8";
    
    protected  X509Certificate certificate;
    private PublicKey publicKey ;
    private PrivateKey privateKey;

    public BaseResource() {
        this.readPublicKey();
        this.readPrivateKey();
    }
    
    private void readPublicKey() {
        String filePath = String.format("C:/Proftaak/certificates/%s", SYSTEM_NAME);
        try (InputStream stream = new FileInputStream(filePath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            this.certificate = (X509Certificate) cf.generateCertificate(stream);
            this.publicKey = this.certificate.getPublicKey();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    private void readPrivateKey() {
        String filePath = String.format("C:/Proftaak/certificates/%s",
                                        this.PRIVATE_KEY_FILENAME);
        File keyFile = new File(filePath);
        
        // Read the file.
        try (FileInputStream fis = new FileInputStream(keyFile)) {
            byte[] keyBytes = new byte[(int) keyFile.length()];
            // Read the file bytes.
            try (DataInputStream dis = new DataInputStream(fis)) {
                dis.readFully(keyBytes);
            }
            
            // Generate the private key from the keyBytes.
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = keyFactory.generatePrivate(keySpec);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    //Class<? extends Serializable>
    protected byte[] encrypt(Serializable object) 
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {
        // Serialize object to be encrypted
        byte[] plain = SerializationUtils.serialize(object);
        
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);  
        return cipher.doFinal(plain);
    }

    protected <T> T decrypt(byte[] encrypted, Object object) 
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException, 
                   InvalidKeyException, 
                   IllegalBlockSizeException, 
                   BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");   
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);  
        byte[] decrypted = cipher.doFinal(encrypted);
        
        // Deserialize decrypted byte array.
        Object deserialized = SerializationUtils.deserialize(decrypted);
        Class<T> decryptedObject = (Class<T>) deserialized;
        
        return decryptedObject.cast(object);
    }
    
}
