/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.RoadUsage;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.rest.clients.TotalPriceClient;

/**
 * The service for totalPrice.
 * @author Alexander
 */
@Stateless
public class TotalPriceService {
    @Inject
    private TotalPriceClient totalPriceClient;
    
    /**
     * Get the total price for the given roadUsages.
     * @param roadUsages The given roadUsages to get the price for.
     * @return The calculated total price.
     */
    public Double getTotalPrice(List<RoadUsage> roadUsages) {
        return this.totalPriceClient.getTotalPrice(roadUsages);
    }
}
