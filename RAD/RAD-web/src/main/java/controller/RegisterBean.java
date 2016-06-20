/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Address;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import service.PersonService;

/**
 * Controller class for register page.
 * @author Alexander
 */
@Named
@RequestScoped
public class RegisterBean {   
    @EJB
    private PersonService personService;
    
    private String username;
    private String password;
    
    private String firstName;
    private String lastName;
    private String initials;
    
    private String streetname;
    private String housenumber;
    private String zipcode;
    private String city;
    
    private String successMessage;
    private String errorMessage;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitials() {
        return this.initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getStreetname() {
        return this.streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getHousenumber() {
        return this.housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public boolean getHasSuccessMessage() {
        return this.successMessage != null && !this.successMessage.isEmpty();
    }
    
    public boolean getHasErrorMessage() {
        return this.errorMessage != null && !this.errorMessage.isEmpty();
    }
    
    private void reset() {
        this.username = "";
        this.password = "";
        
        this.firstName = "";
        this.lastName = "";
        this.initials = "";
        
        this.streetname = "";
        this.housenumber = "";
        this.zipcode = "";
        this.city = "";
    }
    
    /**
     * Register a new person.
     */
    public void register() {
        if(this.personService.findPersonByUsername(this.username) != null){
            this.errorMessage = "username " + this.username + 
                    " is already in use.";
            return;
        }
        
        Address address = new Address(this.streetname, this.housenumber, 
                this.zipcode, this.city);
        this.personService.addPerson(this.firstName, this.lastName, 
                this.initials, this.username, this.password, address);
        
        this.successMessage = this.username + " is successfully registered.";
        this.reset();
    }
}
