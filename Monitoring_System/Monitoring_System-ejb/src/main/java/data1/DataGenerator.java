package data1;

import common.domain.Test;
import common.domain.TestType;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Generates initial data for testing usages.
 *
 * @author Edwin.
 */
@Singleton
@Startup
public class DataGenerator {

    @Inject
    private SystemDao systemDao;

    @Inject
    private TestDao testDao;

    /**
     * Generates data when the application is started.
     */
    @PostConstruct
    public void onStartup() {
        common.domain.System sys
                = new common.domain.System("VS", "VS", "192.168.24.72", 8080);
        common.domain.System sys2
                = new common.domain.System("RAD", "RAD", "192.168.24.74", 8080);
        common.domain.System sys3
                = new common.domain.System("ASS", "ASS", "192.168.24.77", 8080);
        if (this.systemDao.getSystems().isEmpty()) {
            this.systemDao.create(sys);
            this.systemDao.create(sys2);
            this.systemDao.create(sys3);
        }

        int min = 60000;
        int hour = min * 60;
        Long time_test1 = System.currentTimeMillis() - (1 * hour);
        Long time_test2 = System.currentTimeMillis() - (20 * min);
        Long time_test3 = System.currentTimeMillis() - (50 * min);

        Test t1 = new Test(TestType.STATUS,
                new Timestamp(time_test1), false);
        Test t2 = new Test(TestType.FUNCTIONAL,
                new Timestamp(time_test1), true);
        Test t3 = new Test(TestType.ENDPOINTS,
                new Timestamp(time_test1), true);

        Test t1_mid = new Test(TestType.STATUS,
                new Timestamp(time_test3), true);
        Test t1_end = new Test(TestType.STATUS,
                new Timestamp(time_test2), true);
        Test t2_end = new Test(TestType.FUNCTIONAL,
                new Timestamp(time_test2), false);
        Test t3_end = new Test(TestType.ENDPOINTS,
                new Timestamp(time_test2), false);

        Test t4 = new Test(TestType.STATUS,
                new Timestamp(time_test1), false);
        Test t5 = new Test(TestType.FUNCTIONAL,
                new Timestamp(time_test1), false);
        Test t6 = new Test(TestType.ENDPOINTS,
                new Timestamp(time_test1), false);

        Test t4_end = new Test(TestType.STATUS,
                new Timestamp(time_test2), true);
        Test t5_end = new Test(TestType.FUNCTIONAL,
                new Timestamp(time_test2), true);
        Test t6_end = new Test(TestType.ENDPOINTS,
                new Timestamp(time_test2), true);

        Test t7 = new Test(TestType.STATUS,
                new Timestamp(time_test1), true);
        Test t8 = new Test(TestType.FUNCTIONAL,
                new Timestamp(time_test1), true);
        Test t9 = new Test(TestType.ENDPOINTS,
                new Timestamp(time_test1), true);

        Test t7_end = new Test(TestType.STATUS,
                new Timestamp(time_test2), false);
        Test t8_end = new Test(TestType.FUNCTIONAL,
                new Timestamp(time_test2), false);
        Test t9_end = new Test(TestType.ENDPOINTS,
                new Timestamp(time_test2), false);

        sys.addTest(t1);
        sys.addTest(t2);
        sys.addTest(t3);
        sys.addTest(t1_mid);
        sys.addTest(t1_end);
        sys.addTest(t2_end);
        sys.addTest(t3_end);

        sys2.addTest(t4);
        sys2.addTest(t5);
        sys2.addTest(t6);
        sys2.addTest(t4_end);
        sys2.addTest(t5_end);
        sys2.addTest(t6_end);

        sys3.addTest(t7);
        sys3.addTest(t8);
        sys3.addTest(t9);
        sys3.addTest(t7_end);
        sys3.addTest(t8_end);
        sys3.addTest(t9_end);

        this.testDao.create(t1);
        this.testDao.create(t2);
        this.testDao.create(t3);
        this.testDao.create(t1_mid);
        this.testDao.create(t1_end);
        this.testDao.create(t2_end);
        this.testDao.create(t3_end);

        this.testDao.create(t4);
        this.testDao.create(t5);
        this.testDao.create(t6);
        this.testDao.create(t4_end);
        this.testDao.create(t5_end);
        this.testDao.create(t6_end);

        this.testDao.create(t7);
        this.testDao.create(t8);
        this.testDao.create(t9);
        this.testDao.create(t7_end);
        this.testDao.create(t8_end);
        this.testDao.create(t9_end);

        this.systemDao.edit(sys);
        this.systemDao.edit(sys2);
        this.systemDao.edit(sys3);
    }
}
