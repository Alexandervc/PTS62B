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
import java.util.SortedSet;
import java.util.TimerTask;
import java.util.TreeSet;
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
    private Long lastRideIndex;

    public SenderTask(String cartrackerId, Long serialNumber,
            TimerManager manager, SendMissingCarpositions sender,
            Integer rideId, CarPositionDao dao) {
        this.cartrackerId = cartrackerId;
        this.serialNumber = serialNumber;
        this.rideId = rideId;
        this.manager = manager;
        this.sender = sender;
        this.dao = dao;
        this.lastRideIndex = null;
    }

    @Override
    public void run() {
        // Get last serialnumber of last ride
        List<CarPosition> positions;
        SortedSet<Long> set = new TreeSet<>();

        CarPosition cpLast = null;
        CarPosition cpFirst = null;

        // Check if there are serialnumber missing before this serialnumber
        positions = this.dao
                .getPositionsOfRide(this.rideId, this.cartrackerId);

        // Find first and last position.
        for (CarPosition cp : positions) {
            if (cp.getFirstOfRide()) {
                cpFirst = cp;
            } else if (cp.getLastOfRide()) {
                cpLast = cp;
                this.lastRideIndex = cp.getSerialNumber();
            }
            // Add cartrackerId to Set List
            set.add(cp.getSerialNumber());
        }

        // If first and last position are found.
        if (cpFirst != null && cpLast != null) {
            // Check if list-Size is complete.
            int completeSize = (cpLast.getSerialNumber()
                    .intValue() + 1) - cpFirst.getSerialNumber().intValue();
            Boolean complete = completeSize == positions.size();

            // If complete stop timer and send carPosition list for check
            // foreign country ride.
            if (complete) {
                // end timer
                this.manager.stopTimer(positions, this.cartrackerId);
            } else {
                // Search for missing positions.
                // And stop timer
                this.sendMissingPositionList(set, cpFirst);
                this.manager.stopTimer(null, this.cartrackerId);
            }
        } else {
            // Search for missing positions.
            // And stop timer
            this.sendMissingPositionList(set, cpFirst);
            this.manager.stopTimer(null, this.cartrackerId);
        }

    }

    private void sendMissingPositionList(Set<Long> postions,
            CarPosition first) {
        List<Long> missingPositions = new ArrayList<>();
        Long startIndex;
        // If first is null, extraIndex is first serialnumber of ride - 1
        // In case first of the ride isn't arrived jet.
        Long extraIndex;
        
        // If rideId != 1, search for last serialNumber
        if (this.rideId != 1) {
            // Search last serialnumber of rideid -1.
            startIndex = this.dao.getLastSerialNumberOfRide(rideId - 1,
                    this.cartrackerId);
            // If null, first searchnumber of rideId
            if (startIndex == null) {
                startIndex = this.dao.getFirstSerialNumberOfRide(rideId,
                        cartrackerId);
                if(first == null){
                    extraIndex = startIndex-1;
                    missingPositions.add(extraIndex);
                }
            }
        } else {
            // else rideId = 1, startIndex = 0.
            startIndex = new Long(0);
        }
        
        // If startIndex is not null.
        if (startIndex != null) {
            startIndex++;
            if (lastRideIndex == null) {
                lastRideIndex = this.serialNumber;
            }

            while (startIndex < lastRideIndex) {
                if (!postions.contains(startIndex)) {
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
    }

}
