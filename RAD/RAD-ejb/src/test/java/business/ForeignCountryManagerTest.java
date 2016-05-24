/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.ForeignCountryRideDao;
import domain.ForeignCountryRide;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.argThat;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the ForeignCountryRide.
 *
 * @author Jesse
 */
@RunWith(MockitoJUnitRunner.class)
public class ForeignCountryManagerTest {

    private ForeignCountryManager foreignCountryManager;

    @Mock
    private ForeignCountryRideDao foreignCountryRideDao;
    
    private static final Long FOREIGN_COUNTRY_RIDE_ID = 29L;
    private static final Double TOTAL_PRICE = 13.37;

    /**
     * Initializes the test class before testing.
     */
    @Before
    public void setUp() {
        this.foreignCountryManager = new ForeignCountryManager();

        // Set the mocked ForeignCountryRideDao.
        this.foreignCountryManager.setForeignCountryRideDao(
                this.foreignCountryRideDao);
    }

    /**
     * Tests the createForeignCountryRide method.
     */
    @Test
    public void createForeignCountryRideTest() {
        ForeignCountryRide foreignCountryRide = 
                new ForeignCountryRide(FOREIGN_COUNTRY_RIDE_ID, TOTAL_PRICE);

        this.foreignCountryManager.createForeignCountryRide(
                FOREIGN_COUNTRY_RIDE_ID, TOTAL_PRICE);
        verify(this.foreignCountryManager.getForeignCountryRideDao())
                .create(argThat(
                        new IsSameForeignCountryRide(foreignCountryRide)));
    }

    /**
     * Matcher for ForeignCountryRide.
     */
    private class IsSameForeignCountryRide
            extends ArgumentMatcher<ForeignCountryRide> {

        private final ForeignCountryRide foreignCountryRide;

        /**
         * Instantiates the IsSameForeignCountryRide class.
         * @param foreignCountryRide 
         */
        public IsSameForeignCountryRide(ForeignCountryRide foreignCountryRide) {
            this.foreignCountryRide = foreignCountryRide;
        }

        @Override
        public boolean matches(Object argument) {
            ForeignCountryRide other = (ForeignCountryRide) argument;
            return this.foreignCountryRide.getForeignCountryRideId()
                    .equals(other.getForeignCountryRideId())
                    && this.foreignCountryRide.getTotalPrice()
                            .equals(other.getTotalPrice());
        }
    }
}
