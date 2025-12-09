/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import static strategygame.StrategyGame.*;
import strategygame.Cell.*;

/**
 *
 * @author artyom
 */
public class TestAction {
    public static enum ErrorType {
        COORDS, // Coordinates are out of field
        ACTION_PROHIB,
        OK
    }
    
    private Field field;
    private Coord src;
    private DirType dir;
    private ActionType act;

    public void setField(Field field) {
        this.field = field;
    }

    public void setSrc(Coord src) {
        this.src = src;
    }

    public void setDir(DirType dir) {
        this.dir = dir;
    }

    public Field getField() {
        return field;
    }

    public Coord getSrc() {
        return src;
    }

    public DirType getDir() {
        return dir;
    }

    public ActionType getAct() {
        return act;
    }

    public void setAct(ActionType act) {
        this.act = act;
    }
    
    public TestAction(Field field, Coord src, DirType dir,
                        ActionType act) {
        setField(field);
        setSrc(src);
        setDir(dir);
        setAct(act);
    }
    
    public ErrorType testActDir() {
        Coord dest;
        Cell destCell;
        
        dest = calcDest();
        
        if (dest.getX() < 0 || dest.getX() >= FLD_WIDTH
                || dest.getY() < 0 || dest.getY() >= FLD_HEIGHT) {
            return ErrorType.COORDS;
        }
        
        destCell = destCell(dest);
        
        switch (this.act) {
            case ACT:
                return testAct(destCell);
            case MOVE:
                return testMove(destCell);
            case BUILD:
                return testBuild(destCell);
            default:
                return ErrorType.ACTION_PROHIB;
        }
    }
    
    public Cell destCell(Coord coord) {
        return field.findCell(coord);
    }
        
    public ErrorType testAct(Cell cell) {
        
        return ErrorType.ACTION_PROHIB;
    }
            
    public ErrorType testMove(Cell cell) {
        if (cell.getUnit() == null &&
                cell.getResource() == null &&
                (cell.getBuilding() == null ||
                cell.getBuilding().getType() == Building.BuildingType.BRIDGE)) {
            return ErrorType.OK;
        }
        else {
            return ErrorType.ACTION_PROHIB;
        }
    }
    
    public ErrorType testBuild(Cell cell) {
        return ErrorType.ACTION_PROHIB;
    }
    
    public ErrorType testBuild(Cell cell, Building.BuildingType building) {
        
    }
    
    public Coord calcDest() {
        int x = 0, y = 0;
        Coord dest = new Coord();
        
        switch (dir) {
            case UP:
                x = 0;
                y = 1;
                break;
            case DOWN:
                x = 0;
                y = -1;
                break;
            case LEFT:
                x = -1;
                y = 0;
                break;
            case RIGHT:
                x = 1;
                y = 0;
                break;
        }
        
        dest.setX(src.getX() + x);
        dest.setY(src.getY() + y);
        
        return dest;
    }
}
