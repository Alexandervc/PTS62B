package domain;

/**
 * Storage for the state of a system.
 * @author Edwin.
 */
public class SystemState {    
    private String systemName;
        
    private String functional;
    private String endpoints;
    private String status;
    
    private final String passed = "passed";
    private final String failed = "failed";

    /**
     * Creates a system state without results and just a systemname.
     * @param systemName the system name of the code.
     */
    public SystemState(String systemName) {
        this.systemName = systemName; 
    }

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getFunctional() {
        return this.functional;
    }

    /**
     * Stores and formats the string in the SystemState Object.
     * @param functional The result of the test.
     */
    public void setFunctional(String functional) {
        this.functional = this.formatResult(functional);
    }

    public String getEndpoints() {
        return this.endpoints;
    }

    /**
     * Stores and formats the string in the SystemState Object.
     * @param endpoints The result of the test.
     */
    public void setEndpoints(String endpoints) {
        this.endpoints = this.formatResult(endpoints);
    }

    public String getStatus() {
        return this.status;
    }

    /**
     * Stores and formats the string in the SystemState Object.
     * @param status The result of the test.
     */
    public void setStatus(String status) {
        this.status = this.formatResult(status);
    }
    
    /**
     * Formats a java boolean(true/false) into a "passed/failed" result
     * that can be displayed properly.
     * @param result.
     * @return The resulting string.
     */
    public String formatResult(String result) {
        if("true".equals(result)) {
            return passed;
        } else{
            return failed;
        }
    }
}
