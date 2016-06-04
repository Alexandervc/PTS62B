/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Managed bean for index page.
 * @author Linda
 */
@Named
@RequestScoped
public class InlogBean {
    private String inlogName;

    public String getInlogName() {
        return inlogName;
    }

    public void setInlogName(String inlogName) {
        this.inlogName = inlogName;
    }
    
}
