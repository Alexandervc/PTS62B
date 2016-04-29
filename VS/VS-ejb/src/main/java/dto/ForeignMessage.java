/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import domain.CarPosition;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for sending messages to foreign countries.
 * @author Alexander
 */
public class ForeignMessage {
    private String cartrackerId;
    private Double totalPrice;
    private List<ForeignPosition> positions;
    
    /**
     * Helper class for sending messages to foreign countries.
     * @param cartrackerId The cartrackerId to send the message for.
     * @param totalPrice The totalPrice of the message.
     * @param carpositions The carpositions of the message.
     */
    public ForeignMessage(String cartrackerId, Double totalPrice, 
            List<CarPosition> carpositions) {
        this.cartrackerId = cartrackerId;
        this.totalPrice = totalPrice;
        
        this.positions = new ArrayList<>();
        
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        for(CarPosition cp : carpositions) {
            this.positions.add(new ForeignPosition(cp.getxCoordinate(),
                    cp.getyCoordinate(),
                    dateFormat.format(cp.getMoment())));
        }
    }

    public String getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(String cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ForeignPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<ForeignPosition> positions) {
        this.positions = positions;
    }    
}
