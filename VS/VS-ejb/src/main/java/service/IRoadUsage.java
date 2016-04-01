/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.RoadType;

/**
 *
 * @author Alexander
 */
public interface IRoadUsage {
    String getRoadName();
    RoadType getRoadType();
    Double getKm();
}
