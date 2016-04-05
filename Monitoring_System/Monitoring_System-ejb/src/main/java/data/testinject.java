/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author Edwin
 */
@Stateless
public class testinject {
    @PostConstruct
    public void init() {
        System.out.println("TEST INJECTED");
    }
}
