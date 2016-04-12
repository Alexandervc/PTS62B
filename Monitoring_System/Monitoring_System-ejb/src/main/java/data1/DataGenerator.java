package data;

import common.Domain.Test;
import common.Domain.TestType;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class DataGenerator {
    
    @Inject
    SystemDao systemDao;
    
    @Inject 
    TestDao testDao;
    
    @PostConstruct
    public void onStartup() {
        System.out.println("GENERATING DATA");
        common.Domain.System sys = new common.Domain.System("VS","VS","192.168.24.74",8080);
        common.Domain.System sys2 = new common.Domain.System("RAD","RAD","192.168.24.75",8080);
        common.Domain.System sys3 = new common.Domain.System("ASS","ASS","192.168.24.76",8080);

        systemDao.create(sys);
        systemDao.create(sys2);
        systemDao.create(sys3);

        Test t1 = new Test(TestType.STATUS, new Timestamp(System.currentTimeMillis()),false );
        Test t2 = new Test(TestType.FUNCTIONAL, new Timestamp(System.currentTimeMillis()),true );
        Test t3 = new Test(TestType.ENDPOINTS, new Timestamp(System.currentTimeMillis()),true );
        Test t4 = new Test(TestType.STATUS, new Timestamp(System.currentTimeMillis()),false );
        Test t5 = new Test(TestType.FUNCTIONAL, new Timestamp(System.currentTimeMillis()),false );
        Test t6 = new Test(TestType.ENDPOINTS, new Timestamp(System.currentTimeMillis()),false );
        Test t7 = new Test(TestType.STATUS, new Timestamp(System.currentTimeMillis()),true );
        Test t8 = new Test(TestType.FUNCTIONAL, new Timestamp(System.currentTimeMillis()),false );
        Test t9 = new Test(TestType.ENDPOINTS, new Timestamp(System.currentTimeMillis()),true );
        sys.addTest(t1);
        sys.addTest(t2);
        sys.addTest(t3);
        sys2.addTest(t4);
        sys2.addTest(t5);
        sys2.addTest(t6);
        sys3.addTest(t7);
        sys3.addTest(t8);
        sys3.addTest(t9);
        testDao.create(t1);
        testDao.create(t2);
        testDao.create(t3);
        testDao.create(t4);
        testDao.create(t5);
        testDao.create(t6);
        testDao.create(t7);
        testDao.create(t8);
        testDao.create(t9);
        systemDao.edit(sys);  
        systemDao.edit(sys2); 
        systemDao.edit(sys3); 
    }
}
