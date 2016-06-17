/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.rest.resources;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Jesse
 */
public class TestResource extends BaseResource {
    public TestResource() {
        super();
    }
    
    public byte[] exposeEncrypt(Serializable object)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {
        return super.encrypt(object);
    }
    
    protected <T> T exposeDecrypt(byte[] encrypted, Object object) 
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException, 
                   InvalidKeyException, 
                   IllegalBlockSizeException, 
                   BadPaddingException {
        return super.decrypt(encrypted, object);
    }
}
