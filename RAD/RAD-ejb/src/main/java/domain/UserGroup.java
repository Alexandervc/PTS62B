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
    String groupName;
    
    public UserGroup() {
        
    }

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
