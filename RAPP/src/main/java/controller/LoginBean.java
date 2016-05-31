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
public class LoginBean {
    private Long personId;
    private String loginName;
    private String loginPassword;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    
    public String login(){
        //String redirect = "faces/invoice.xhtml?personId=1";
        String redirect = "invoice?personId=" + this.personId + "&faces-redirect=true";
        return redirect;
    }
}
