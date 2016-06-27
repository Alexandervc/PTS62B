/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.jms.SendPositionBean;

/**
 * Bean for searching missing serialnumbers.
 * @author Linda.
 */
@Stateless
public class SearchMissingPosition implements Serializable{

    private static final Logger LOGGER = Logger
            .getLogger(SearchMissingPosition.class.getName());
    
    private static final String PROJECT_ROOT
            = "C:\\Proftaak";

    @Inject
    private SendPositionBean sendPositionBean;

    private transient BufferedReader reader;

    private void setupStream(String cartracker, Long serialNumber)
            throws FileNotFoundException {
        
        String fileName = cartracker + "-" + serialNumber + ".json";
        this.reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(SearchMissingPosition.PROJECT_ROOT
                                        + "\\output\\" + fileName)
                        )
                )
        );
    }

    /**
     * Search Missing numbers for cartrackerid.
     * @param cartrackerId, String cartrackerid.
     * @param positions List serialnumbers.
     */
    public void searchPositions(String cartrackerId, List<Long> positions) {
        // for each positions search file
        for (Long l : positions) {
            try {
                //Setup stream for configId.                
                this.setupStream(cartrackerId, l);

                //Read config file.
                String jsonposition = this.reader.readLine();
                
                this.sendPositionBean.sendPosition(jsonposition,
                        cartrackerId, l);
                this.reader.close();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Carposition file not found with "
                        + "cartrackerId = "+ cartrackerId + " and position= "
                + l.toString(), ex);
            }
        }
    }

}
