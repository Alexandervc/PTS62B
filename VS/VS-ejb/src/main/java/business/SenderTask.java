/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.CarPositionDao;
import domain.CarPosition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import service.jms.SendMissingCarpositions;

/**
 *
 * @author Linda
 */
public class SenderTask extends TimerTask {

    private final String cartrackerId;
    private final Long serialNumber;
    private final Integer rideId;
    private final TimerManager manager;
    private final SendMissingCarpositions sender;
    private final CarPositionDao dao;

    public SenderTask(String cartrackerId, Long serialNumber,
            TimerManager manager, SendMissingCarpositions sender,
            Integer rideId, CarPositionDao dao) {
        this.cartrackerId = cartrackerId;
        this.serialNumber = serialNumber;
        this.rideId = rideId;
        this.manager = manager;
        this.sender = sender;
        this.dao = dao;
    }

    @Override
    public void run() {
        // Get last serialnumber of last ride
        Long startIndex;
        Long lastRideIndex = null;
        if (this.rideId != 1) {
            startIndex = this.dao.getLastSerialNumberOfRide(rideId - 1,
                    this.cartrackerId);
            if (startIndex == null) {
                startIndex = this.dao.getFirstSerialNumberOfRide(rideId,
                        cartrackerId);
            }
        } else {
            startIndex = new Long(0);
        }
        if (startIndex != null) {
            startIndex++;
            // Check if there are serialnumber missing before this serialnumber
            List<CarPosition> positions = this.dao
                    .getPositionsOfRide(this.rideId, this.cartrackerId);
            List<Long> missingPositions = new ArrayList<>();
            Set<Long> set = new HashSet<>();
            for (CarPosition cp : positions) {
                set.add(cp.getSerialNumber());
                if (cp.getLastOfRide()) {
                    lastRideIndex = cp.getSerialNumber();
                }
            }
            if (lastRideIndex == null) {
                lastRideIndex = this.serialNumber;
            }

            while (startIndex < lastRideIndex) {
                if (!set.contains(startIndex)) {
                    missingPositions.add(startIndex);
                }
                startIndex++;
            }

            // If true send list of missing serialnumbers to ASS
            // If false do nothing
            if (!missingPositions.isEmpty()) {
                this.sender.sendMissingNumbers(cartrackerId, missingPositions);
            }
        }
        // end stop timer
        this.manager.stopTimer();
    }

}
