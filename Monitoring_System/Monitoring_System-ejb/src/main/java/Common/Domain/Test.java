/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Edwin
 */
@Entity
@NamedQueries({
@NamedQuery(query = "select t from Test t where t.systemID = :systemId AND"
        + " t.testType = :type ORDER BY t.date DESC", name = "get latest"
                + " test for system with type")
})
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TEST_TYPE")
    @Enumerated(EnumType.STRING)
    private TestType testType;
    
    @Column(name = "DBDATE")
    private Date date;
    
    @Column(name = "DBRESULT")
    private Boolean result;
    
    @Column(name="SYSTEM_ID")
    private Long systemID;
    
    public Long getId() {
        return this.id;
    }

    /**
     * Default empty constructor for JPA.
     */
    public Test() {
    }

    public Test(TestType testType, Date date, Boolean result) {
        this.testType = testType;
        this.date = new Date(date.getTime());
        this.result = result;
    }

    public void setId(Long id) {
        this.id = id;
    }   
}
