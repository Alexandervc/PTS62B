/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Class which provides Windows Command Line functionality.
 * @author jesblo
 */
@Stateless
public class CmdHelper {
    
    /**
     * Method which executes a command within the Windows command line.
     * @param command The command which is to be executed.
     * @return The output lines.
     * @throws IOException 
     */
    public static List<String> execute(String[] command) throws IOException {
        return CmdHelper.execute(command, new String[0]);
    }

    /**
     * Method which executes a command within the Windows command line.
     * @param command The command which is to be executed.
     * @param additional Additional parameters which will be appended to the 
     * command.
     * @return The output lines.
     * @throws IOException 
     */
    public static List<String> execute(String[] command, String[] additional) 
            throws IOException {
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
        String[] commandTotal = ArrayHelper.concat(command, additional);

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
}