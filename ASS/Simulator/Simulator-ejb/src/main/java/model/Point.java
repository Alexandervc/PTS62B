package model;

/**
 * Point Class.
 * @author Melanie
 */
public class Point {
    private Double latitude;
    private Double longitude;
    private Double altitude;

    /**
     * Constructor for point representing position with coordinates.
     * 
     * @param latitude xcoordinate.
     * @param longitude ycoordinate.
     * @param altitude if road is going up/down.
     */
    public Point(Double latitude, Double longitude, Double altitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * Constructor for point representing position with coordinates.
     * 
     * @param latitude xcoordinate.
     * @param longitude ycoordinate.
     */
    public Point(Double latitude, Double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = null;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    /**
     * ToString method.
     * 
     * @return String.
     */
    @Override
    public String toString() {
        return "Point [lat/lang:" + this.latitude + "," + this.longitude 
                + ", altitude=" + this.altitude + "]";
    }
}
