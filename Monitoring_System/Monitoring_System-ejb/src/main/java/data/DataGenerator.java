package data;

import common.domain.Test;
import common.domain.TestType;
import static common.domain.Test_.date;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Remote;
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
        common.domain.System sys = new common.domain.System("VS","VS","192.168.24.74",8080);
        systemDao.create(sys);
        Test t1 = new Test(TestType.STATUS, new Timestamp(System.currentTimeMillis()),true );
        Test t2 = new Test(TestType.FUNCTIONAL, new Timestamp(System.currentTimeMillis()),true );
        Test t3 = new Test(TestType.ENDPOINTS, new Timestamp(System.currentTimeMillis()),true );
        sys.addTest(t1);
        sys.addTest(t2);
        sys.addTest(t3);
        testDao.create(t1);
        testDao.create(t2);
        testDao.create(t3);
        systemDao.edit(sys);  
    }
}
