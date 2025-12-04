/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.GameCell.*;
import static strategygame.StrategyGame.*;

/**
 *
 * @author artyom
 */
public class ActionTest {
    private enum DestResType {
        OK,
        NO_MOVE,
        NO_ACT,
        NO_BUILD
    }
    
    private enum BuildResType {
        OK,
        CANT_BUILD
    }

    public DestResType checkAct_dest(GameCell cell, ActionType action) {
        DestResType result = DestResType.OK;
        switch (action) {
            case MOVE:
                if (cell.getTerrainType() != TerrainType.PLATEAU) {
                    result = DestResType.NO_MOVE;
                }
                
                if (cell.whatInCell() != null) {
                    result = DestResType.NO_MOVE;
                }
                break;
            case INTERACT:
                if (cell.whatInCell() == null) {
                    result = DestResType.NO_ACT;
                }
                break;
            case BUILD:
                if (cell.whatInCell() != null &&
                        cell.whatInCell() != CellFillType.BUILDING) {
                    result = DestResType.NO_BUILD;
                }
                break;
        }
        
        return result;
    }
    
    public BuildResType checkBuildingCell(GameCell cell,
                                    BuildingType buiType) {
        BuildResType result = BuildResType.OK;
        
        if (cell.getBuilding() != null &&
            cell.getBuilding().getBuildingType() != buiType) {
            result = BuildResType.CANT_BUILD;
        }
        
        if (cell.getBuilding() == null &&
                (
                    (cell.getTerrainType() != TerrainType.PLATEAU &&
                        buiType == BuildingType.HOUSE)
                    ||
                    (cell.getTerrainType() != TerrainType.WATER &&
                        buiType == BuildingType.BRIDGE)
                )
            ) {
            result = BuildResType.CANT_BUILD;
        }
        return result;
    }
}
