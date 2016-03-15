/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.Domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Edwin
 */
@Entity
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TEST_TYPE")
    @Enumerated(EnumType.STRING)
    private TestType testType;
    
    @Column(name = "DATE")
    private Date date;
    
    @Column(name = "RESULT")
    private Boolean result;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   
}
