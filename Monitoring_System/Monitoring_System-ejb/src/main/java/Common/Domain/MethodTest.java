/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.Domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table
public class MethodTest implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "DBDATE")
    private Timestamp date;
    
    @Column(name = "DBRESULT")
    private Boolean result;
    
    public MethodTest() {
    }

    public MethodTest(Timestamp date, Boolean result) {
        this.date = new Timestamp(date.getTime());
        this.result = result;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return new Timestamp(date.getTime());
    }

    public void setDate(Timestamp date) {
        this.date = new Timestamp(date.getTime());
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
    
    
    

}
