/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dto.PersonDto;
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
    private Long personId;
    private PersonDto person;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
    public void setPerson(PersonDto dto){
        this.person = dto;
    }
    
    public String restLinkCars(){
        return this.person.getLinkCarDto();
    }
    
    /**
     * Get full name of person.
     * @return String of name.
     */
    public String getPersonName(){
        return this.person.getFirstName() + " " + this.person.getInitials() +
                " " + this.person.getLastName();
    }
    
    /**
     * Get full Address of person.
     * @return String of address.
     */
    public String getPersonAddress(){
        return this.person.getAddress().getStreetname() + " " + 
                this.person.getAddress().getHousenumber() +
                ", " + this.person.getAddress().getZipcode() +" "+
                this.person.getAddress().getCity();
    }
}
