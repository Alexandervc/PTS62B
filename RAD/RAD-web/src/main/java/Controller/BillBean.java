/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.IRoadUsage;
import service.RadService;

/**
 *
 * @author Alexander
 */
@Named
@RequestScoped
public class BillBean {
    @EJB
    private RadService service;
    
    private List<IRoadUsage> roadUsages;
    
    public List<IRoadUsage> getRoadUsages() {
        return service.generateRoadUsages(1L, new Date(), new Date());
    }
}
