/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategygame;

import strategygame.StrategyGame.*;
/**
 *
 * @author artyom
 */
public class Map {
    public Field field;
    public int width;
    public int height;
    
    public Map() {
        field = new Field();
    }

    public Field River() {
        int x = 0, y = 0;
        
        if (StrategyGame.FLD_WIDTH == 15 && StrategyGame.FLD_HEIGHT == 15) {            
            for (y = 0; y < StrategyGame.FLD_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.FLD_WIDTH; x++) {
                    if (
                            (y < 5 && x == 4) ||
                            (y >= 5 && y < 10 && x == 3) ||
                            (y >= 10 && y < 15 && x == 2)
                            ) {
                        field.cells[x][y].terrainType = TerrainType.WATER;
                    }
                    else {
                        field.cells[x][y].terrainType = TerrainType.PLATEAU;
                        
                    }
                }                
            }
        }
        return field;
    }
    
    public Field Plateau() {
        int x = 0, y = 0;
        
        if (StrategyGame.FLD_WIDTH == 15 && StrategyGame.FLD_HEIGHT == 15) {
            for (y = 0; y < StrategyGame.FLD_HEIGHT; y++) {
                for (x = 0; x < StrategyGame.FLD_WIDTH; x++) {
                    field.cells[x][y].terrainType = TerrainType.PLATEAU;    
                }
            }
        }
        
        field.cells[0][2].resource
                = field.new Resource(ResourceType.GOLD, 25);
        
        field.cells[2][1].resource
                = field.new Resource(ResourceType.LUMBER, 100);
        
        field.cells[6][5].resource
                = field.new Resource(ResourceType.STONE, 100);
        return field;
    }
}