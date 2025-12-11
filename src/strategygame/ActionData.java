/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import static strategygame.StrategyGame.*;
import static strategygame.Building.*;

/**
 *
 * @author artyom
 */
public class ActionData {
    private Field field;
    
    private ActionType act;
    private DirType dir;
    
    private Coord source;
    private Coord dest;
    
    private BuildingType building;

    public Field getField() {
        return field;
    }

    public ActionType getAct() {
        return act;
    }

    public DirType getDir() {
        return dir;
    }

    public Coord getSource() {
        return source;
    }

    public Coord getDest() {
        return dest;
    }

    public BuildingType getBuilding() {
        return building;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setAct(ActionType act) {
        this.act = act;
    }

    public void setDir(DirType dir) {
        this.dir = dir;
    }

    public void setSource(Coord source) {
        this.source = source;
    }

    public void setDest(Coord dest) {
        this.dest = dest;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
    }
}
