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
import util.CmdHelper;

/**
 * Provides the server status functionality using asadmin.
 * @author jesblo
 */
public class ServerStatusManager {
    
    public static final String PASSWORD_FILE = "C:\\Proftaak\\asadmin.txt";
    
    /**
     * TODO:
     * 
     * http://bbissett.blogspot.nl/2012/01/asadmin-with-remote-glassfish.html
     * add C:/glassfish4/glassfish/bin to PATH
     * 
     * create password file:
     * AS_ADMIN_PASSWORD=admin
     * AS_ADMIN_ADMINPASSWORD=admin
     * AS_ADMIN_USERPASSWORD=admin
     * AS_ADMIN_MASTERPASSWORD=admin
     */

    /**
     * Retrieves the status of all the deployed applications on the system.
     * @param system The system on which to retrieve the status from.
     * @return A map of the application names and their status.
     * @throws IOException
     * @throws InterruptedException 
     */
    public Map<String, ServerStatus> retrieveApplicationStatus(common.domain.System system) 
           throws IOException, InterruptedException {
       
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
     * @throws IOException 
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
            "list-applications"
        };
        
        // Execute the list-applications command.
        List<String> asadminListApplicationsOutput = CmdHelper.execute(command);
        List<String> results = new ArrayList<>();

        // Iterate throught the output and get the status of the application.
        for (String result : asadminListApplicationsOutput) {
            // Filter on web applications.
            if (result.contains("<web>")) {
                // Match on first word. This retreives the application name.
                Pattern pattern = Pattern.compile("^[\\w]+");
                Matcher matcher = pattern.matcher(result);

                if (matcher.find())
                {
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
     * @throws IOException 
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
            "show-component-status"
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
            if (result.contains("Status of ")) {
                // If the status is enabled, the application is online.
                if (result.contains("enabled")) {
                    return ServerStatus.ONLINE;
                } 
            }
        }
        
        // If a status of "enabled" is not found, the application is offline.
        return ServerStatus.OFFLINE;
    }
}