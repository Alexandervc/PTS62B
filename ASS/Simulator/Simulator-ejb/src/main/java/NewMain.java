import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ejb.Singleton;
import service.IPathService;

/**
 *
 * @author Melanie
 */
@Singleton
@Startup
public class NewMain {
    @Inject
    IPathService service;

    @PostConstruct
    public void main() {
        service.generateFile();
    }    
}
