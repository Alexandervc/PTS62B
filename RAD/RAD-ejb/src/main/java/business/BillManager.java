package business;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import dao.BillDao;
import dao.RateDao;
import domain.Bill;
import domain.ForeignCountryRide;
import domain.Person;
import domain.Rate;
import dto.BillRoadUsage;
import java.util.logging.Logger;

/**
 * Manager for BillDao.
 *
 * @author Melanie.
 */
@Stateless
public class BillManager {

    private static final Logger LOG = Logger.
            getLogger(BillManager.class.getName());
    
    @Inject
    private BillDao billDao;

    @Inject
    private RateDao rateDAO;

    @Inject
    private ForeignCountryManager foreignCountryManager;

    /**
     * Find all bills in Database from person.
     *
     * @param person Type Person.
     */
    public void findBills(Person person) {
        this.billDao.findAllForUser(person);
    }
    
    /**
     * find bill in database with cartrackerid, month and year.
     * @param cartrackerId Long.
     * @param month integer.
     * @param year integer.
     * @return Bill, if no bill found return null;
     */
    public Bill findBillWithCartracker(String cartrackerId, int month, int year){
        return this.billDao.findBillWithCartracker(cartrackerId, month, year);
    }

    /**
     * Generate bill.
     *
     * @param person Type Person.
     * @param roadUsages List of IRoadUsage.
     * @param cartrackerId.
     * @param month.
     * @param year.
     * @return new Bill Type Bill.
     */
    public Bill generateBill(Person person, List<BillRoadUsage> roadUsages,
            String cartrackerId, int month, int year)
            throws EntityNotFoundException {
        double totalPrice = 0.0;
        double ruPrice = 0.0;

        for (BillRoadUsage ru : roadUsages) {
            // If the BillRoadUsage contains a ForeignCountryRideId, the BillRoadUsage's
            // origin is not from this country. The price should be retrieved 
            // from the RAD database. If the price could not be found, an 
            // exception is thrown.
            // If the BillRoadUsage does not contain a ForeignCountryRideId, the 
            // BillRoadUsage's origin is from this country. Calculate the cost by 
            // the distance multiplied by the cost of the RoadType's rate.
            if (ru.getForeignCountryRideId() != null) {
                ForeignCountryRide foreignCountryRide;
                foreignCountryRide = this.foreignCountryManager.
                        findRideByForeignCountryRideId(
                                ru.getForeignCountryRideId());

                // If the foreignCountryRide is null, the query did not return
                // exactly one result. This means that the price was not stored 
                // or multiple entries with the same foreignCountryRideId exist.
                if (foreignCountryRide != null) {
                    ruPrice = foreignCountryRide.getTotalPrice();
                } else {
                    throw new EntityNotFoundException("Cost of the "
                            + "ForeignCountryRide was not found in the RAD "
                            + "database.");
                }
            } else {
                Rate rate = this.rateDAO.find(ru.getRoadType());
                ru.setRate(rate.getPrice());
                ruPrice = ru.getKm() * rate.getPrice();
            }
            
            ru.setPrice(ruPrice);
            totalPrice += ruPrice;
        }

        // Check if bill exicts in the database.
        Bill bill = this.findBillWithCartracker(cartrackerId, month, year);
        
        if(bill == null){
            // Round totalPrice
            totalPrice = Math.round(totalPrice * 100) / 100;
            
            // If the bill was not found, create a new bill entry.
            bill = new Bill(person,
                            roadUsages,
                            totalPrice,
                            cartrackerId,
                            month,
                            year);
            
            this.billDao.create(bill);
        } else {
            // If the bill already exists in the database, update the RoadUsages
            // and the Total price.
            bill.setRoadUsages(roadUsages);
            bill.setTotalPrice(totalPrice);
            
            this.billDao.edit(bill);
        }
        
        return bill;
    }
    
    /**
     * Calculate the price for the given roadUsages.
     *
     * @param roadUsages The roadUsages to calculate the price for.
     * @return The price.
     */
    public Double calculatePrice(List<BillRoadUsage> roadUsages) {
        double totalPrice = 0;

        for (BillRoadUsage ru : roadUsages) {
            totalPrice += this.calculatePrice(ru);
        }

        return totalPrice;
    }

    /**
     * Calculate the price for the given roadUsage.
     *
     * @param roadUsage The roadUsage to calculate the price for.
     * @return The price.
     */
    private Double calculatePrice(BillRoadUsage roadUsage) {
        Rate rate = this.rateDAO.find(roadUsage.getRoadType());
        return roadUsage.getKm() * rate.getPrice();
    }

    /**
     * Setter BillDao.
     *
     * @param billDao object.
     */
    public void setBillDao(BillDao billDao) {
        this.billDao = billDao;
    }

    /**
     * Setter RateDao.
     *
     * @param rateDAO object.
     */
    public void setRateDAO(RateDao rateDAO) {
        this.rateDAO = rateDAO;
    }

    /**
     * Setter ForeignCountryManager.
     *
     * @param foreignCountryManager object.
     */
    public void setForeignCountryManager(
            ForeignCountryManager foreignCountryManager) {
        this.foreignCountryManager = foreignCountryManager;
    }

}
