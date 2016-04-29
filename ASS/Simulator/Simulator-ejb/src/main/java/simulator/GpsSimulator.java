package simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Leg;
import model.Point;
import model.PositionInfo;
import service.GpsdService;
import support.NavUtils;

/**
 * GpsSimulator Class.
 * @author Melanie
 */
public class GpsSimulator implements Runnable {
    private long id;

    private GpsdService gpsdService;

    private static final boolean useGpsd = false;

    private final AtomicBoolean shouldCancel = new AtomicBoolean();

    private Double speedInMps; // In meters/sec
    private Boolean shouldMove;

    private static final Integer reportInterval = 
            500; // millisecs at which to send position reports
    private PositionInfo currentPosition;
    private List<Leg> legs;

    /**
     * Runnable method for simulator.
     */
    @Override
    public void run() {
        try {
            if (this.shouldCancel.get()) {
                this.destroy();
                return;
            }
            
            while (!Thread.interrupted()) {
                long startTime = new Date().getTime();
                
                if (this.currentPosition != null) {
                    if (this.shouldMove) {
                        this.moveVehicle();
                        this.currentPosition.setSpeed(this.speedInMps);
                    } else {
                        this.currentPosition.setSpeed(0.0);
                    }

                    if (GpsSimulator.useGpsd) {
                        this.gpsdService.updatePosition(this.currentPosition);
                    }
                }

                // wait till next position report is due.
                this.sleep(startTime);
            } // loop endlessly
        } catch (InterruptedException iex) {
            Logger.getLogger(GpsSimulator.class.getName())
                    .log(Level.SEVERE, null, iex);
            this.destroy();
            return;
        }

        this.destroy();
    }
    
    /**
     * On thread interrupt. Send null position to all consumers to indicate that
     * sim has closed.
     */
    public void destroy() {
        this.currentPosition = null;
    }

    /**
     * Sleep till next position report is due.
     *
     * @param startTime.
     * @throws InterruptedException .
     */
    private void sleep(long startTime) throws InterruptedException {
        long endTime = new Date().getTime();
        long elapsedTime = endTime - startTime;
        
        long sleepTime = 0;
        
        if (GpsSimulator.reportInterval - elapsedTime > 0) {
            sleepTime = GpsSimulator.reportInterval - elapsedTime;
        }

        Thread.sleep(sleepTime);
    }

    /**
     * Set new position of vehicle based on current position and vehicle speed.
     */
    public void moveVehicle() {
        Double distance = this.speedInMps * GpsSimulator.reportInterval / 1000.0;
        Double distanceFromStart = this.currentPosition.getDistanceFromStart() 
                + distance;
        Double excess = 0.0; // amount by which next postion will exceed end
        // point of present leg

        for (int i = this.currentPosition.getLeg().getId();
                i < this.legs.size(); i++) {
            Leg currentLeg = this.legs.get(i);
            
            if (distanceFromStart > currentLeg.getLength()) {
                excess = currentLeg.getLength() - distanceFromStart;
            }

            if (Double.doubleToRawLongBits(excess) == 0) {
                // this means new position falls within current leg
                this.currentPosition.setDistanceFromStart(distanceFromStart);
                this.currentPosition.setLeg(currentLeg);
                Point newPosition = NavUtils.getPosition(
                        currentLeg.getStartPosition(), 
                        distanceFromStart,
                        currentLeg.getHeading());
                this.currentPosition.setPosition(newPosition);
                return;
            }
            distanceFromStart = excess;
        }

        // if we've reached here it means vehicle has moved beyond end of path.
        // so go back to start of path.
        this.setStartPosition();
    }

    /**
     * Position vehicle at start of path.
     */
    public void setStartPosition() {
        this.currentPosition = new PositionInfo();
        Leg leg = this.legs.get(0);
        this.currentPosition.setLeg(leg);
        this.currentPosition.setPosition(leg.getStartPosition());
        this.currentPosition.setDistanceFromStart(0.0);
    }

    public Double getSpeedInMps() {
        return this.speedInMps;
    }

    public void setSpeedInMps(Double speed) {
        this.speedInMps = speed;
    }

    public void setSpeedInKph(Double speed) {
        this.speedInMps = speed / 3.6;
    }

    public Double getSpeedInKph() {
        return this.speedInMps * 3.6;
    }

    public Boolean getShouldMove() {
        return this.shouldMove;
    }

    public void setShouldMove(Boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    public synchronized void cancel() {
        this.shouldCancel.set(true);
    }

    public void setGpsdService(GpsdService gpsdService) {
        this.gpsdService = gpsdService;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = new ArrayList<>(legs);
    }

    public PositionInfo getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(PositionInfo currentPosition) {
        this.currentPosition = currentPosition;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
