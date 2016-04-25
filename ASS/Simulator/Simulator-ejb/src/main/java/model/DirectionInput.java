package model;

/**
 *
 * @author Melanie
 */
public class DirectionInput {
    private String from;
    private String to;
    
    @Deprecated
    public DirectionInput() {
    }

    public DirectionInput(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
