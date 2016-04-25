package model;

/**
 *
 * @author Melanie
 */
public class Leg {
    private Integer id;
    private Point startPosition;
    private Point endPosition;
    private Double length;
    private Double heading;

    /**
     * @return the startPosition
     */
    public Point getStartPosition() {
        return this.startPosition;
    }

    /**
     * @param startPosition the startPosition to set
     */
    public void setStartPosition(Point startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * @return the endPosition
     */
    public Point getEndPosition() {
        return this.endPosition;
    }

    /**
     * @param endPosition the endPosition to set
     */
    public void setEndPosition(Point endPosition) {
        this.endPosition = endPosition;
    }

    /**
     * @return the length
     */
    public Double getLength() {
        return this.length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * @return the heading
     */
    public Double getHeading() {
        return this.heading;
    }

    /**
     * @param heading the heading to set
     */
    public void setHeading(Double heading) {
        this.heading = heading;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
