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
public class ActionController {
    private Field field;
    
    private Player player;
    private Unit unit;
    private Coord source;
    
    private DirType dir;
    private ActionType action;
    private BuildingType building;
    
    private Coord dest;

    public void setDir(MoveType dir) {
        this.dir = dir;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setSource(Coord source) {
        this.source = source;
    }

    public void setDest(Coord dest) {
        this.dest = dest;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public void setBuilding(BuildingType building) {
        this.building = building;
    }

    public MoveType getDir() {
        return dir;
    }

    public Player getPlayer() {
        return player;
    }

    public Field getField() {
        return field;
    }

    public Unit getUnit() {
        return unit;
    }

    public Coord getSource() {
        return source;
    }

    public Coord getDest() {
        return dest;
    }

    public ActionType getAction() {
        return action;
    }

    public BuildingType getBuilding() {
        return building;
    }
    
    public void resetACfull() {
        this.setPlayer(null);
        this.setSource(null);
        this.setUnit(null);
        resetAC();
    }
    
    public void resetAC() {
        this.setDest(null);
        this.setAction(null);
        this.setBuilding(null);
    }
    
    public ActionController(Field field) {
        this.setField(field);
    }
    
    public void setACplayer(Player player) {
        this.setPlayer(player);
        this.setSource(field.findUnitCell(this.player));
        this.setUnit(field.findCell(this.getSource()).getUnit());
    }
    
    public Cell calcDest() {
        TestAction.ErrorType err = null;
        
        TestAction test = new TestAction(field, source, dir, action);        
        
        err = test.testActDir();
        
        switch (err) {
            case OK:
                return 
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public void askActionMove() {
        setAction(player.askAction());
        setDir(player.askMove());
        

    }
}
