package domain;

import common1.domain1.Test;

/**
 *
 * @author Edwin
 */
public class SystemState {    
    private String systemName;
    
    private String functional;
    private String endpoints;
    private String status;

    public SystemState(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getFunctional() {
        return functional;
    }

    public void setFunctional(String functional) {
        if(functional.equals("true")) {
            this.functional = "passed";
        } else{
            this.functional = "failed";
        }
    }

    public String getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String endpoints) {
        if(endpoints.equals("true")) {
            this.endpoints = "passed";
        } else{
            this.endpoints = "failed";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals("true")) {
            this.status = "passed";
        } else{
            this.status = "failed";
        }
    }   
}
