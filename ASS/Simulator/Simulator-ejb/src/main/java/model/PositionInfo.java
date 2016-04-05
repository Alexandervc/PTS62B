package model;

/**
 *
 * @author Melanie
 */
public class PositionInfo {
    private Point position;
    //kml path is composed of a series of legs (line segments) 1 .. n.
    // this member denotes present leg (starting at leg 0)
    private Leg leg;
    //metres from start of leg
    private Double distanceFromStart;
    private Double speed;       // m/s

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * @return the distanceFromStart
     */
    public Double getDistanceFromStart() {
        return distanceFromStart;
    }

    /**
     * @param distanceFromStart the distanceFromStart to set
     */
    public void setDistanceFromStart(Double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    /**
     * @return the leg
     */
    public Leg getLeg() {
        return leg;
    }

    /**
     * @param leg the leg to set
     */
    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    /**
     * @return the speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "PositionInfo [position=" + position + ", leg=" + leg + 
                ", distanceFromStart=" + distanceFromStart
                + ", speed=" + speed + "]";
    }
}
