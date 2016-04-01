/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asadminserverstatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jesblo
 */
public class AsadminServerStatus {
    
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
     * The main method.
     * @param args The command line arguments.
     * @throws IOException
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "192.168.24.70";
        String passwordfile = "C:\\School\\Proftaak\\asadmin.txt";

        // Define the asadmin command list-applications.
        String[] asadminListApplications =
        {
            "asadmin",
            "-H " + host,
            "-u admin",
            "--passwordfile " + passwordfile,
            "-p 4848",
            "-s",
            "list-applications"
        };
        
        // Define the asadmin command show-component-status.
        String[] asadminShowComponentStatus =
        {
            "asadmin",
            "-H " + host,
            "-u admin",
            "--passwordfile " + passwordfile,
            "-p 4848",
            "-s",
            "show-component-status"
        };
        
        List<String> asadminListApplicationsOutput = 
                AsadminServerStatus.execute(asadminListApplications);
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
        
        // TODO: Remove debug code.
        System.out.println(results.size() + " deployed application(s).");
        
        for (String application : results) {
            // Execute the show-component-status command.
            List<String> asadminShowComponentStatusOutput = 
                    AsadminServerStatus.execute(asadminShowComponentStatus, 
                            new String[]{ application });
            
            // Iterate throught the output and get the status of the 
            // application.
            for (String result : asadminShowComponentStatusOutput) {                
                if (result.contains("Status of ")) {
                    // TODO: Remove debug code.
                    System.out.println(result.replace("Status of ", ""));
                } 
            }
        }
    }
    
    /**
     * Method which executes a command within the Windows command line.
     * @param command The command which is to be executed.
     * @return The output lines.
     * @throws IOException 
     */
    public static List<String> execute(String[] command) throws IOException {
        return AsadminServerStatus.execute(command, new String[0]);
    }
    
    /**
     * Method which executes a command within the Windows command line.
     * @param command The command which is to be executed.
     * @param additional Additional parameters which will be appended to the 
     * command.
     * @return The output lines.
     * @throws IOException 
     */
    public static List<String> execute(String[] command, String[] additional) throws IOException {
        // Create the result list.
        List<String> output = new ArrayList<>();

        // Define the cmd command.
        String[] cmd =
        {
            "cmd"
        };
        
        // Execute the cmd command.
        Process p = Runtime.getRuntime().exec(cmd);
        
        // Append the additional parameters to the command.
        String[] commandTotal = concat(command, additional);
        
        // Create the buffered input stream reader.
        BufferedReader stdInput = new BufferedReader(
                new InputStreamReader(p.getInputStream()));

        // Execute the command.
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println(String.join(" ", commandTotal));
        stdin.close();
                
        // Print the result lines.
        String s = null;
        while ((s = stdInput.readLine()) != null) {            
            output.add(s);
        }
        
        return output;
    }
    
    /**
     * Combines two arrays.
     * @param <T> The type.
     * @param first The first array.
     * @param second The second array.
     * @return The combined array.
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
}
