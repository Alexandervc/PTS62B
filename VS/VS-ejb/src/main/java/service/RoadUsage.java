/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.RoadType;
import java.io.Serializable;

/**
 *
 * @author Alexander
 */
public class RoadUsage implements Serializable{
    private String name;
    private RoadType type;
    private Double km;
}
