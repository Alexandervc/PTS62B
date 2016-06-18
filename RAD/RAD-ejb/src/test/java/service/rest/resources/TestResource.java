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
    
    public String exposeEncrypt(String plain) {
        return super.encrypt(plain);
    }
    
    protected String exposeDecrypt(String encrypted) {
        return super.decrypt(encrypted);
    }
    
    protected String exposeToJson(Object object) {
        return super.toJson(object);
    }
}
