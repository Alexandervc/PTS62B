/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import domain.Bill;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jesse
 */
public class BaseResourceTest {
    private TestResource testResource;
    
    @Before
    public void setUp() {
        this.testResource = new TestResource();
    }
    
    @Test
    public void encryptDecryptTest() 
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {
        Bill bill = new Bill();
        
        String json = this.testResource.gson.toJson(bill);
        String encrypted = this.testResource.exposeEncrypt(json);
        String decrypted = this.testResource.decrypt(encrypted);
        Bill decryptedBill = this.testResource.gson.fromJson(decrypted,
                                                             Bill.class);
        
        assertNotNull(decryptedBill);
    }
    
}
