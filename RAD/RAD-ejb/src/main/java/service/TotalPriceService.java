/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.BillManager;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for totalPrice.
 * @author Alexander
 */
@Stateless
public class TotalPriceService {
    @Inject
    private BillManager billManager;
    
    /**
     * Get the price for the given roadUsages.
     *
     * @param roadUsages The roadUsages to get the price for.
     * @return The price.
     */
    public Double getTotalPrice(List<dto.BillRoadUsage> roadUsages) {
        return this.billManager.calculatePrice(roadUsages);
    }
}
