/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.CarPositionManager;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alexander
 */
@Stateless
public class CarPositionService {
    @Inject
    private CarPositionManager carPositionManager;

    public void saveCarPosition(Long cartrackerId, Date moment, 
            Double xCoordinate, Double yCoordinate, String roadName, 
            Double meter) {
        carPositionManager.saveCarPosition(cartrackerId, moment, xCoordinate, 
                yCoordinate, roadName, meter);
    }
    
}
