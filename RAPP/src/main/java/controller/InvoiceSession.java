/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Linda
 */
@Named
@SessionScoped
public class InvoiceSession implements Serializable {
    private String personName;
    private Long personId;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String name) {
        this.personName = name;
    }
}
