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
    
    private ActionData data;

    public void setData(ActionData data) {
        this.data = data;
    }

    public ActionData getData() {
        return data;
    }
    
    public TestAction(ActionData data) {
        setData(data);
    }
    
    public ErrorType testDir(Coord dest) {        
        dest = calcDest();
        
        if (dest.getX() < 0 || dest.getX() >= FLD_WIDTH
                || dest.getY() < 0 || dest.getY() >= FLD_HEIGHT) {
            return ErrorType.COORDS;
        }
        else {
            return ErrorType.OK;
        }        
    }
    
    public ErrorType testActDir() {
        Coord dest = getData().getDest();
        Cell destCell;
        
        if (testDir(dest) == ErrorType.COORDS) {
            return ErrorType.COORDS;
        }
        
        getData().setDestCell(getDestCell(dest));
        
        switch (getData().getAct()) {
            case ACT:
                return testAct();
            case MOVE:
                return testMove();
            case BUILD:
                return testBuild();
            default:
                return ErrorType.ACTION_PROHIB;
        }
    }
    
    public Cell getDestCell(Coord coord) {
        return data.getField().findCell(coord);
    }
        
    public ErrorType testAct() {
        
        return ErrorType.ACTION_PROHIB;
    }
            
    public ErrorType testMove() {
        Cell cell = getData().getDestCell();
        
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
    
    public ErrorType testBuild() {
        return ErrorType.ACTION_PROHIB;
    }
    
    public ErrorType testBuild(Cell cell, Building.BuildingType building) {
        return ErrorType.ACTION_PROHIB;
    }
    
    public Coord calcDest() {
        int x = 0, y = 0;
        Coord dest = new Coord();
        
        switch (data.getDir()) {
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
        
        dest.setX(data.getSource().getX() + x);
        dest.setY(data.getSource().getY() + y);
        
        return dest;
    }
}
