/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import domain.CarPosition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.jms.SendMissingCarpositions;

/**
 *
 * @author Linda
 */
public class TimerManager {

    private final static Long TIME = new Long(60 * 1000);
    
    private final Timer timer;
    private final SenderTask task;
    private final CarPositionManager manager;
          
    
    public TimerManager(SendMissingCarpositions sender, String cartrackerId,
            Long serialnumber, Integer rideId, CarPositionDao dao,
            CarPositionManager manager) {
        this.manager = manager;
        this.timer = new Timer();
        this.task = new SenderTask(cartrackerId, serialnumber, 
            this, sender, rideId, dao);
    }
    
    public void startTimer(){
        this.timer.schedule(this.task, TIME);
    }
    
    public void stopTimer(List<CarPosition> positions, String cartrackerId){
        this.timer.cancel();
        this.timer.purge();
        this.manager.processRideForForeignCountry(positions, cartrackerId);
    }

}
