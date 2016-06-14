/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.Address;
import java.io.Serializable;

/**
 * Dto class person.
 * @author Linda.
 */
public class PersonDto implements Serializable{
    private Long id;
    private String firstName;
    private String lastName;
    private String initials;
    private Address address;
    private String linkCarDto;

    /**
     * Constructor for personDto.
     * @param id of person.
     * @param firstName of person.
     * @param lastName of person.
     * @param initials of person.
     * @param address of person.
     */
    public PersonDto(Long id, String firstName, String lastName, 
            String initials, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.initials = initials;
        this.address = address;
        this.linkCarDto = "/persons/" + this.id + "/cars";
    }
    
    /**
     * Empty constructor for converting to and from json.
     * @deprecated Only for converting.
     */
    @Deprecated
    public PersonDto() {
        // Empty constructor for converting to and from json
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLinkCarDto() {
        return this.linkCarDto;
    }

    public void setLinkCarDto(String linkCarDto) {
        this.linkCarDto = linkCarDto;
    }
    
}
