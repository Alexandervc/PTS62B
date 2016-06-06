package service.rest.contracts;

/**
 * Data contract for the ForeignCountryRide class from RAD.
 * @author Jesse
 */
public class ForeignCountryRide {
    private Long foreignCountryRideId;
    private double totalPrice;

    public Long getForeignCountryRideId() {
        return this.foreignCountryRideId;
    }

    public void setForeignCountryRideId(Long foreignCountryRideId) {
        this.foreignCountryRideId = foreignCountryRideId;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}