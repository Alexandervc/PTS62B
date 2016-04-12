package data;

import common.domain.Test;
import common.domain.TestType;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
/**
 * Generates initial data for testing usages.
 */
public class DataGenerator {
    
    @Inject
    private SystemDao systemDao;
    
    @Inject 
    private TestDao testDao;
    
    @PostConstruct
    /**
     * Generates data when the application is started.
     */
    public void onStartup() {
        common.domain.System sys = 
                new common.domain.System("VS","VS","192.168.24.74",8080);
        common.domain.System sys2 =
                new common.domain.System("RAD","RAD","192.168.24.75",8080);
        common.domain.System sys3 =
                new common.domain.System("ASS","ASS","192.168.24.76",8080);

        this.systemDao.create(sys);
        this.systemDao.create(sys2);
        this.systemDao.create(sys3);

        Test t1 = new Test(TestType.STATUS,
                new Timestamp(System.currentTimeMillis()),false );
        Test t2 = new Test(TestType.FUNCTIONAL,
                new Timestamp(System.currentTimeMillis()),true );
        Test t3 = new Test(TestType.ENDPOINTS,
                new Timestamp(System.currentTimeMillis()),true );
        Test t4 = new Test(TestType.STATUS,
                new Timestamp(System.currentTimeMillis()),false );
        Test t5 = new Test(TestType.FUNCTIONAL,
                new Timestamp(System.currentTimeMillis()),false );
        Test t6 = new Test(TestType.ENDPOINTS,
                new Timestamp(System.currentTimeMillis()),false );
        Test t7 = new Test(TestType.STATUS,
                new Timestamp(System.currentTimeMillis()),true );
        Test t8 = new Test(TestType.FUNCTIONAL,
                new Timestamp(System.currentTimeMillis()),true );
        Test t9 = new Test(TestType.ENDPOINTS,
                new Timestamp(System.currentTimeMillis()),true );
        sys.addTest(t1);
        sys.addTest(t2);
        sys.addTest(t3);
        sys2.addTest(t4);
        sys2.addTest(t5);
        sys2.addTest(t6);
        sys3.addTest(t7);
        sys3.addTest(t8);
        sys3.addTest(t9);
        this.testDao.create(t1);
        this.testDao.create(t2);
        this.testDao.create(t3);
        this.testDao.create(t4);
        this.testDao.create(t5);
        this.testDao.create(t6);
        this.testDao.create(t7);
        this.testDao.create(t8);
        this.testDao.create(t9);
        this.systemDao.edit(sys);  
        this.systemDao.edit(sys2); 
        this.systemDao.edit(sys3); 
    }
}
