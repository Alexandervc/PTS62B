
package notused;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import domain.RoadType;

/**
 * Road class.
 * @author Linda
 */
public class Road {
    private Long id;
    private String name;
    private RoadType type;
    
    /**
     * Contructor.
     * @param id Long.
     * @param name String.
     * @param type type RoadType.
     */
    public Road(Long id, String name, RoadType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
