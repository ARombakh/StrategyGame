/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
import static strategygame.StrategyGame.*;
import strategygame.House.*;
import strategygame.Bridge.*;
import strategygame.GameCell.*;

/**
 *
 * @author artyom
 */
public class Action {    
    private Unit unit;
    private Direction dir;
    private GameCell src;
    private GameCell dest;
    private ActionType action;
    private BuildingType buildingType;

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public Unit getUnit() {
        return unit;
    }

    public Direction getDir() {
        return dir;
    }

    public GameCell getSrc() {
        return src;
    }

    public GameCell getDest() {
        return dest;
    }

    public ActionType getAction() {
        return action;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void setSrc(GameCell src) {
        this.src = src;
    }

    public void setDest(GameCell dest) {
        this.dest = dest;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public void calcDest() {
        int x = getSrc().getxCell(), y = getSrc().getyCell();

        switch (dir) {
            case UP -> {
                x += 0;
                y += -1;
            }
            case DOWN -> {
                x += 0;
                y += 1;
            }
            case LEFT -> {
                x += -1;
                y += 0;
            }
            case RIGHT -> {
                x += 1;
                y += 0;
            }
        }

        if (!(x >= 0 && x < StrategyGame.FLD_WIDTH) ||
                !(y >= 0 && y < StrategyGame.FLD_HEIGHT)) {
            this.setDest(null);
        }
        else this.setDest(getSrc().getField().cells[x][y]);
        }

    public boolean checkDest() {
        calcDest();
        if(getDest() == null) {
            System.out.println("Destination is out of field borders!");
            return false;
        }
        return true;
    }

    public boolean move() {
        boolean isSuccess;

        if(!checkDest()) return false;

        // if destination cell [is empty and has Plateau terrain type]
        // or [if it has bridge] then one can go on this cell
        if ((getDest().whatInCell() == null &&
                getDest().getTerrainType() == TerrainType.PLATEAU)
                ||
                (getDest().whatInCell() == CellFillType.BUILDING &&                
                getDest().getBuilding().getBuildingType() ==
                BuildingType.BRIDGE)) {
            getDest().setUnit(getSrc().getUnit());
            getSrc().setUnit(null);
            getUnit().setCell(getDest());
            isSuccess = true;
        }
        else {
            if (getDest().whatInCell() != null) {
                System.out.println("The destination cell is taken");
            }

            if (getDest().getTerrainType() != TerrainType.PLATEAU) {
                System.out.printf("Can't move to %s terrain\n",
                        getDest().getTerrainType());
            }

            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean action() {
        boolean isSuccess;

        if(!checkDest()) return false;

        switch (getDest().whatInCell()) {
            case RESOURCE, UNIT, BUILDING:
                getDest().actUpon(getSrc().getUnit());
                isSuccess = true;
                break;
            case null:
                System.out.println(
                    "Nothing to act upon in the target cell");
                isSuccess = false;
                break;
        }

        return isSuccess;
    }

    public boolean tryBuild() {
        boolean isSuccess = false;

        if(!checkDest()) return false;

        switch (build()) {
            case BUILDING_BUILT:
                System.out.println("Building is already built. " +
                        "Choose another action.");
                isSuccess = false;
                break;
            case CELL_OCCUPIED:
                System.out.println("The cell you want to build in " +
                        "is occupied. Choose another cell.");
                isSuccess = false;
                break;
            case WRONG_TERRAIN:
                System.out.println("The cell you want to build in " +
                        "doesn't have appropriate terrain. Choose " +
                        "another cell.");
                isSuccess = false;
                break;
            case OTHER_BUILDING:
                System.out.println("Another building is in the cell you want "
                        + "to build in.");
                isSuccess = false;
                break;
            case BUILDING_SUCCESSFUL:
                isSuccess = true;
                break;
            case null:
                isSuccess = false;
                break;
        }

        return isSuccess;
    }

    private BuildResultType build() {
        BuildResultType buildResult;

        if(!checkDest()) return null;

        switch (getDest().whatInCell()) {
            case null:
                Building building = new Building(LIFE, TerrainType.MOUNTAIN,
                                                buildingType, 'C');
                switch (buildingType) {
                    case HOUSE:
                        building = new House(0);
                        break;
                    case BRIDGE:
                        building = new Bridge(0);
                        break;
                }
                
                if(getDest().getTerrainType() != building.getTerrain())
                    return BuildResultType.WRONG_TERRAIN;

                getDest().setBuilding(building);
                // Specifically no break was inserted
            case BUILDING:
                buildResult = getDest().getBuilding().build(unit);
                break;
            case UNIT, RESOURCE:
                buildResult = BuildResultType.CELL_OCCUPIED;                    
                break;
        }

        return buildResult;
    }

    public boolean act() {
        boolean isSuccess = false;
        switch (action) {
            case BUILD -> isSuccess = tryBuild();
            case INTERACT -> isSuccess = action();
            case MOVE -> isSuccess = move();
            default -> {
                isSuccess = false;
                throw new AssertionError("Wrong type of action");
            }
        }

        return isSuccess;
    }
}
