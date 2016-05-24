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
import dto.RoadUsage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
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
     * Generate bill.
     *
     * @param person Type Person.
     * @param roadUsages List of IRoadUsage.
     * @param cartrackerId.
     * @param month.
     * @param year.
     * @return new Bill Type Bill.
     */
    public Bill generateBill(Person person, List<RoadUsage> roadUsages,
            String cartrackerId, int month, int year)
            throws EntityNotFoundException {
        double totalPrice = 0.0; //this.calculatePrice(roadUsages);

        for (RoadUsage ru : roadUsages) {
            // If the RoadUsage contains a ForeignCountryRideId, the RoadUsage's
            // origin is not from this country. The price should be retrieved 
            // from the RAD database. If the price could not be found, an 
            // exception is thrown.
            // If the RoadUsage does not contain a ForeignCountryRideId, the 
            // RoadUsage's origin is from this country. Calculate the cost by 
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
                    totalPrice += foreignCountryRide.getTotalPrice();
                } else {
                    throw new EntityNotFoundException("Cost of the "
                            + "ForeignCountryRide was not found in the RAD "
                            + "database.");
                }
            } else {
                Rate rate = this.rateDAO.find(ru.getRoadType());
                double price = ru.getKm() * rate.getPrice();
                totalPrice += price;
            }
        }
        Bill temp = new Bill(person, roadUsages, totalPrice, cartrackerId,
                month, year);

        try {
            // Calculate Date today
            Calendar calToday = Calendar.getInstance();
            Date today = calToday.getTime();
            Date lastDayOfMonth = new Date();

            // Calculate last Day of the Month of Bill
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, 5);
            int day = cal.getActualMaximum(Calendar.DATE);
            // Set a new Date of last Day of the Month.
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String dateInString = day + "-" + month + "-" + year + " 23:59:59";
            lastDayOfMonth = sdf.parse(dateInString);

            // If Date today after the last Day of the Month
            // Save in Database
            if(today.after(lastDayOfMonth)){
                this.billDao.create(temp);
            }
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        return temp;
    }

    /**
     * Calculate the price for the given roadUsages.
     *
     * @param roadUsages The roadUsages to calculate the price for.
     * @return The price.
     */
    public Double calculatePrice(List<RoadUsage> roadUsages) {
        double totalPrice = 0;

        for (RoadUsage ru : roadUsages) {
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
    private Double calculatePrice(RoadUsage roadUsage) {
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
