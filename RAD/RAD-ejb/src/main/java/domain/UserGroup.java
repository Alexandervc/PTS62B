/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a security group.
 * @author Alexander
 */
@Entity
public class UserGroup implements Serializable {
    @Id
    private String groupName;
    
    /**
     * Empty constructor for JPA.
     * @deprecated JPA.
     */
    @Deprecated
    public UserGroup() {
        // Empty constructor for JPA.
    }

    /**
     * Represents a security group.
     * @param groupName The name of the group.
     */
    public UserGroup(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof UserGroup)){
            return false;
        }
        UserGroup g = (UserGroup) o;
        return this.getGroupName().equals(g.getGroupName());
    }
}
