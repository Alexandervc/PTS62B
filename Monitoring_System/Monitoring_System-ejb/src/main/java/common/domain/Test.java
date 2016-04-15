/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * @author Edwin.
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
    private Timestamp date;
    
    @Column(name = "DBRESULT")
    private Boolean result;
    
    @Column(name="SYSTEM_ID")
    private Long systemID;

    /**
     * Default empty constructor for JPA.
     */
    public Test() {
        // Empty constructor for Sonarqube.
    }

    /**
     * A constructor used to construct a Test object.
     * @param testType The type of text.
     * @param date The date the test was executed.
     * @param result The result of the test.
     */
    public Test(TestType testType, Timestamp date, Boolean result) {
        this.testType = testType;
        this.date = new Timestamp(date.getTime());
        this.result = result;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }   

    public TestType getTestType() {
        return this.testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public Timestamp getDate() {
        return (Timestamp) this.date.clone();
    }

    public void setDate(Timestamp date) {
        this.date = (Timestamp) date.clone();
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Long getSystemID() {
        return this.systemID;
    }

    public void setSystemID(Long systemID) {
        this.systemID = systemID;
    }
    

    
    
}
