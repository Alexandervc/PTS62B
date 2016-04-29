/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import common.domain.ServerStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import util.CmdHelper;

/**
 * <p>Provides the server status functionality using asadmin.</p>
 * 
 * <p>This requires the C:/glassfish4/glassfish/bin 
 * (or C:/payara41/glassfish/bin) folder to be added to the PATH variable of
 * the system, as well as a password file on the PASSWORD_FILE location 
 * containing the following parameters:</p>
 * 
 * <p>AS_ADMIN_PASSWORD=admin
 * AS_ADMIN_ADMINPASSWORD=admin
 * AS_ADMIN_USERPASSWORD=admin
 * AS_ADMIN_MASTERPASSWORD=admin</p>
 * 
 * @author jesblo
 */
@Stateless
public class ServerStatusManager {
    
    /**
     * The location of the file which stores the passwords.
     */
    public static final String PASSWORD_FILE = "C:\\Proftaak\\asadmin.txt";
    
    /**
     * Retrieves the status of all the deployed applications on the system.
     * @param system The system on which to retrieve the status from.
     * @return A map of the application names and their status.
     * @throws IOException Thrown if the asadmin file was not found in 
     * C:/Proftaak.
     */
    public Map<String, ServerStatus> retrieveApplicationStatus(
            common.domain.System system) throws IOException {
       
        Map<String, ServerStatus> serverStatus = new HashMap();
       
        // Get the host IP address of the system.
        String host = system.getIp();

        // Retrieve the deployed applications on the server.
        List<String> applications = this.listApplications(host);

        // Iterate though all all applications.
        for (String application : applications) {
            
            // Retrieve the server status of the application.
            ServerStatus status = this.showComponentStatus(
                    host, 
                    application);
            
            // Add the server status to the return map.
            serverStatus.put(application, status);
            
        }
        
        return serverStatus;
    }
    
    /**
     * Executes the list-applications command using the CmdHelper.
     * @param host The host IP address of the system.
     * @return A list of application names.
     * @throws IOException Thrown if the asadmin file was not found in 
     * C:/Proftaak.
     */
    private List<String> listApplications(String host) throws IOException {
        // Defines the asadmin command list-applications.
        String[] command =
        {
            "asadmin",
            "-H " + host,
            "-u admin",
            "--passwordfile " + PASSWORD_FILE,
            "-p 4848",
            "-s",
            "list-applications",
        };
        
        // Execute the list-applications command.
        List<String> asadminListApplicationsOutput = CmdHelper.execute(command);
        List<String> results = new ArrayList<>();

        // Iterate throught the output and get the status of the application.
        for (String result : asadminListApplicationsOutput) {
            // Get the application from the output.
            if (result.contains("<")) {
                // Match on first word. This retreives the application name.
                Pattern pattern = Pattern.compile("([^\\s]+)");
                Matcher matcher = pattern.matcher(result);

                if (matcher.find()) {
                    // Add the application name to the result list.
                    results.add(matcher.group(0));
                }
            }
        }
        
        return results;
    }
    
    /**
     * Executes the show-component-status command using the CmdHelper.
     * @param host The host IP address of the system.
     * @param applicationName The name of the application.
     * @return The status of the application.
     * @throws IOException Thrown if the asadmin file was not found in 
     * C:/Proftaak.
     */
    private ServerStatus showComponentStatus(
            String host, 
            String applicationName) 
            throws IOException {
        
        // Defines the asadmin command show-component-status.
        String[] command =
        {
            "asadmin",
            "-H " + host,
            "-u admin",
            "--passwordfile " + PASSWORD_FILE,
            "-p 4848",
            "-s",
            "show-component-status",
        };
        
        // Execute the show-component-status command.
        List<String> asadminShowComponentStatusOutput = 
                CmdHelper.execute(command, new String[]{ applicationName });

        // Iterate throught the output and get the status of the 
        // application.
        for (String result : asadminShowComponentStatusOutput) {  
            // If the correct outpur line is reached, check the status.
            // An example of the output is:
            // asadmin> show-component-status TestApp
            // Status of TestApp is enabled
            // Command show-component-status executed successfully.
            if (result.contains("Status of ") && result.contains("enabled")) {
                // If the status is enabled, the application is online.
                return ServerStatus.ONLINE;
            }
        }
        
        // If a status of "enabled" is not found, the application is offline.
        return ServerStatus.OFFLINE;
    }
}