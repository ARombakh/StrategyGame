/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.FieldObject.*;

/**
 *
 * @author artyom
 */
public class Building extends FieldObject{
    public static enum BuildingType {
        BRIDGE,
        HOUSE
    }
    
    private BuildingType type;

    public BuildingType getType() {
        return type;
    }
    
    public void setType(BuildingType type) {
        this.type = type;
    }

    public Building(int life, BuildingType type) {
        super(life);
        
        this.setType(type);
    }
}
