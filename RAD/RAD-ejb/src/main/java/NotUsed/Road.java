package NotUsed;

import Domain.RoadType;

/**
 *
 * @author Linda
 */
public class Road {
    private Long id;
    private String name;
    private RoadType type;
    
    public Road(Long id, String name, RoadType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
