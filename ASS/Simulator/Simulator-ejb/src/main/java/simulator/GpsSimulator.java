package simulator;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import model.Leg;
import model.Point;
import model.PositionInfo;
import service.IGpsdService;
import support.NavUtils;

/**
 *
 * @author Melanie
 */
public class GpsSimulator implements Runnable {
    private long id;

    //private MessageChannel messageChannel;

    private IGpsdService gpsdService;

    private final boolean useGpsd = false;

    private final AtomicBoolean cancel = new AtomicBoolean();

    private Double speedInMps; // In meters/sec
    private Boolean shouldMove;

    private final Integer reportInterval = 500; // millisecs at which to send position reports
    private PositionInfo currentPosition = null;
    private List<Leg> legs;

    @Override
    public void run() {
        try {
            if (this.cancel.get()) {
                destroy();
                return;
            }
            
            while (!Thread.interrupted()) {
                long startTime = new Date().getTime();
                
                if (this.currentPosition != null) {
                    if (this.shouldMove) {
                        moveVehicle();
                        this.currentPosition.setSpeed(this.speedInMps);
                    } else {
                        this.currentPosition.setSpeed(0.0);
                    }

                    if (this.useGpsd) {
                        this.gpsdService.updatePosition(this.currentPosition);
                    }

                    //messageChannel.send(MessageBuilder.withPayload(currentPosition).build());
                }

                // wait till next position report is due
                sleep(startTime);
            } // loop endlessly
        } catch (InterruptedException ie) {
            destroy();
            return;
        }

        destroy();
    }
    
    /**
     * On thread interrupt. Send null position to all consumers to indicate that
     * sim has closed.
     */
    void destroy() {
        this.currentPosition = null;
    }

    /**
     * Sleep till next position report is due.
     *
     * @param startTime
     * @throws InterruptedException
     */
    private void sleep(long startTime) throws InterruptedException {
        long endTime = new Date().getTime();
        long elapsedTime = endTime - startTime;
        long sleepTime = this.reportInterval - elapsedTime > 0 ? 
                this.reportInterval - elapsedTime : 0;
        Thread.sleep(sleepTime);
    }

    /**
     * Set new position of vehicle based on current position and vehicle speed.
     */
    void moveVehicle() {
        Double distance = this.speedInMps * this.reportInterval / 1000.0;
        Double distanceFromStart = this.currentPosition.getDistanceFromStart() 
                + distance;
        Double excess = 0.0; // amount by which next postion will exceed end
        // point of present leg

        for (int i = this.currentPosition.getLeg().getId();
                i < this.legs.size(); i++) {
            Leg currentLeg = this.legs.get(i);
            excess = distanceFromStart > currentLeg.getLength() ? distanceFromStart - currentLeg.getLength() : 0.0;

            if (Double.doubleToRawLongBits(excess) == 0) {
                // this means new position falls within current leg
                this.currentPosition.setDistanceFromStart(distanceFromStart);
                this.currentPosition.setLeg(currentLeg);
                Point newPosition = NavUtils.getPosition(currentLeg.getStartPosition(), distanceFromStart,
                        currentLeg.getHeading());
                this.currentPosition.setPosition(newPosition);
                return;
            }
            distanceFromStart = excess;
        }

        // if we've reached here it means vehicle has moved beyond end of path
        // so go back to start of path
        setStartPosition();
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

    /**
     * @return the speed
     */
    public Double getSpeedInMps() {
        return this.speedInMps;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeedInMps(Double speed) {
        this.speedInMps = speed;
    }

    public void setSpeedInKph(Double speed) {
        this.speedInMps = speed / 3.6;
    }

    public Double getSpeedInKph() {
        return this.speedInMps * 3.6;
    }

    /**
     * @return the shouldMove
     */
    public Boolean getShouldMove() {
        return this.shouldMove;
    }

    /**
     * @param shouldMove the shouldMove to set
     */
    public void setShouldMove(Boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    //public void setMessageChannel(MessageChannel sendPosition) {
    //    this.messageChannel = sendPosition;
    //}

    public synchronized void cancel() {
        this.cancel.set(true);
    }

    public void setGpsdService(IGpsdService gpsdService) {
        this.gpsdService = gpsdService;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
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
