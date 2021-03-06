/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import business.RateManager;
import domain.Rate;
import domain.RoadType;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Service for rate.
 * @author Alexander
 */
@Stateless
public class RateService {
    @Inject
    private RateManager rateManager;
    
    /**
     * Add Rate to database.
     *
     * @param rate double.
     * @param type RoadType.
     */
    public void addRate(double rate, RoadType type) {
        this.rateManager.createRate(rate, type);
    }
}
