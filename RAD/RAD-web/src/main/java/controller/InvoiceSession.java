package controller;

import domain.Person;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Melanie
 */
@Named
@SessionScoped
public class InvoiceSession implements Serializable {
    private Long personId;
    private Person person;
    
    /**
     * Get full person name.
     * 
     * @return String person name.
     */
    public String getPersonName() {
        return this.person.getFirstName() + " " +
                this.person.getInitials() + " " + 
                this.person.getLastName();
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
